package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {
    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Message> messageList;
    private RecyclerView.Adapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    MyApplication application;
    private String url;


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        application = (MyApplication) getApplication();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/guest/posts";
        System.out.println(url);
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        try {
                            jsonArr = new JSONArray(response);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                System.out.println(jsonObj);
                                Message message = new Message();
                                message.setName(jsonObj.getString("restaurantName"));
                                message.setCategory(jsonObj.getString("hostName"));
                                message.setPic(R.drawable.restaurant_logo);
                                messageList.add(message);
                            }
                            if (messageList.isEmpty()) {
                                findViewById(R.id.recommendation_list).setVisibility(View.GONE);
                                findViewById(R.id.no_post).setVisibility(View.VISIBLE);
                                progressDialog.cancel();
                                //            Toast.makeText(getBaseContext(),"Waiting for guests to join",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });

        getRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(getRequest);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Intent intent;
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_new_event:
                                intent = new Intent(getApplicationContext(), RoleSelectActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_post_history:
                                intent = new Intent(getApplicationContext(), ReviewHostHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_guest_history:
                                intent = new Intent(getApplicationContext(), ReviewGuestHistoryActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);

        mList = findViewById(R.id.recommendation_list);

        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Intent intent = new Intent(RecommendationActivity.this, DetailedHostPostActivity.class);
                String message = messageList.get(position).getName();
                MyApplication application = (MyApplication) getApplication();
                try {
                    application.reviewPostId = jsonArr.getJSONObject((position)).getInt("id");
                    application.reviewPostHost = jsonArr.getJSONObject((position)).getString("hostName");
                    application.reviewPostRes = jsonArr.getJSONObject((position)).getString("restaurantName");
                    application.reviewPostStartDate = jsonArr.getJSONObject((position)).getString("startDate");
                    //application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
                } catch (Exception e) {
                    System.out.println(e);
                }
                Intent intent = new Intent(RecommendationActivity.this, DetailedGuestResponseActivity.class);
                startActivity(intent);
//                try {
//                    application.reviewPostId = jsonArr.getJSONObject(position).getInt("id");
//                    application.reviewPostStartDate = jsonArr.getJSONObject(position).getString("startDate");
//                    application.reviewPostRes = jsonArr.getJSONObject(position).getString("restaurantName");
//                    application.reviewPostHost = jsonArr.getJSONObject(position).getString("hostName");
//                    //application.joinedPostId = jsonArr.getJSONObject(position).getInt("id");
//                    //joinPost();
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//                intent.putExtra("postSelected", message);
//                startActivity(intent);
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            swipeRefreshLayout.setRefreshing(true);
            getData();
            swipeRefreshLayout.setRefreshing(false);
        }
    };
}


