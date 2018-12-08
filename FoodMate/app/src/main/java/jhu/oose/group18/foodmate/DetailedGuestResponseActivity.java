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
import android.widget.Button;
import android.widget.TextView;
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

import butterknife.BindView;

public class DetailedGuestResponseActivity extends AppCompatActivity {
    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Message> messageList;
    private RecyclerView.Adapter adapter;
    private TextView reservation_name;
    private TextView reservation_time;
    private TextView reservation_description;
    private Button _return;


    private SwipeRefreshLayout swipeRefreshLayout;

    MyApplication application;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_guest);

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

        messageList = new ArrayList<>();

        mList = findViewById(R.id.post_guest_list);
        reservation_name = findViewById(R.id.post_name);
        reservation_time = findViewById(R.id.post_time);
        reservation_description = findViewById(R.id.post_description);

        _return = findViewById(R.id.return_btn);
        _return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
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


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        application = (MyApplication) getApplication();
        reservation_name.setText(application.reviewPostRes);
        reservation_time.setText(application.reviewPostHost);
        reservation_description.setText((application.reviewPostStartDate));
        ///user/{id}/host/posts/{postId}/guests
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts/" + application.reviewPostId + "/guests" ;
        System.out.println(url);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        try {
                            jsonArr = new JSONArray(response);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                System.out.println(i);
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                System.out.println(jsonObj);
                                Message message = new Message();
                                message.setName(jsonObj.getString("userName"));
                                message.setCategory(jsonObj.getString("description"));
                                message.setPic(R.drawable.restaurant_logo);
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


}
