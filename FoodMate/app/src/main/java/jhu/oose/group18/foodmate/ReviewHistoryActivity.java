package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

public class ReviewHistoryActivity extends AppCompatActivity{
        private RecyclerView mList;
        JSONArray jsonArr;

        private LinearLayoutManager linearLayoutManager;
        private DividerItemDecoration dividerItemDecoration;
        private List<Message> messageList;
        private RecyclerView.Adapter adapter;

        MyApplication application;

        private String url;
        private String extraValue;
        private Intent intent;


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/guest/posts";
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

    @NonNull
    private Message getMessage(JSONObject jsonObj) throws JSONException {
        Message message = new Message();
        message.setName(jsonObj.getString("restaurantName"));
        String description = jsonObj.getString("startDate");
        if (description == null) {
            description = "Host is lazy";
        }
        message.setCategory(description);
        message.setPic(R.drawable.restaurant_logo);
        return message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_history);

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
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

        String historyType = getIntent().getStringExtra("HistoryType");
        application = (MyApplication) getApplication();

        if (historyType.equals("PostHistory")){
            url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts";
            extraValue = "";
            intent = new Intent(getApplicationContext(), DetailedHostPostActivity.class);
        } else if (historyType.equals("GuestHistory")){
            url = "https://food-mate.herokuapp.com/user/" + application.userId + "/guest/posts";
            extraValue = "ReviewHistoryActivity";
            intent = new Intent(getApplicationContext(), DetailedGuestResponseActivity.class);
        }

        mList = findViewById(R.id.review_list);

        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                try {
                    application.reviewPostId = jsonArr.getJSONObject((position)).getInt("id");
                    application.reviewPostHost = jsonArr.getJSONObject((position)).getString("hostName");
                    application.reviewPostRes = jsonArr.getJSONObject((position)).getString("restaurantName");
                    application.reviewPostStartDate = jsonArr.getJSONObject((position)).getString("startDate");
                    application.reviewResId = jsonArr.getJSONObject((position)).getInt("restaurantId");
                    //application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
                } catch (Exception e) {
                    System.out.println(e);
                }
                intent.putExtra("FROM_ACTIVITY", extraValue);
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
}

