package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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

public class ListRestaurantActivity extends AppCompatActivity {
    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Message> messageList;
    private MyRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    MyApplication application;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurant);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);


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
                                intent = new Intent(getApplicationContext(), ReviewHistoryActivity.class);
                                intent.putExtra("HistoryType", "PostHistory");
                                startActivity(intent);
                                break;
                            case R.id.action_guest_history:
                                intent = new Intent(getApplicationContext(), ReviewHistoryActivity.class);
                                intent.putExtra("HistoryType", "GuestHistory");
                                startActivity(intent);
                                break;
                            case R.id.action_log_out:
                                DialogFragment dialog = new LogOutDialogFragment();
                                dialog.show(getSupportFragmentManager(), "dialog");
                                break;
                        }
                        return true;
                    }
                });


        messageList = new ArrayList<>();
        mList = findViewById(R.id.restaurant_list);

        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
                String message = messageList.get(position).getName();
                MyApplication application = (MyApplication) getApplication();
                try {
                    application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
                } catch (Exception e) {
                    System.out.println(e);
                }
                intent.putExtra("restaurantSelected", message);
                startActivity(intent);
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
            for(int i = 0; i < adapter.getItemCount(); i++) {
                adapter.deleteItem(i);
            }
            getData();
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        application = (MyApplication) getApplication();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/restaurants";
        System.out.println(url);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        try {
                            jsonArr = new JSONArray(response);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                System.out.println(jsonObj);
                                Message message = getMessage(jsonObj);
                                messageList.add(message);
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

    private Message getMessage(JSONObject jsonObj) throws JSONException {
        Message message = new Message();
        message.setName(jsonObj.getString("name"));
        message.setCategory(jsonObj.getString("category"));
        message.setPic(R.drawable.restaurant);
        return message;
    }


}