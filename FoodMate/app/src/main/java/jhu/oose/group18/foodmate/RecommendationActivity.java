package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import butterknife.ButterKnife;

public class RecommendationActivity extends AppCompatActivity {
    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Message> messageList;
    private RecyclerView.Adapter adapter;

    MyApplication application;
    private String url;


    private void joinPost() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        application = (MyApplication) getApplication();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/guest/" + application.joinedPostId;
        System.out.println(url);
        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        if (response.equals("-1")) {
                            onJoinOwnPostFailed();
                            application.joinedPostId = 0;
                        } else if (response.equals("-2")) {
                            onJoinPostFullFailed();
                            application.joinedPostId = 0;
                        } else if (response.equals("-3")) {
                            onJoinJoinedPostFailed();
                            application.joinedPostId = 0;
                        } else {
                            onJoinSuccess();
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

    private void onJoinOwnPostFailed() {
        Toast.makeText(getBaseContext(), "Cannot join Own Posts", Toast.LENGTH_LONG).show();
    }

    private void onJoinPostFullFailed() {
        Toast.makeText(getBaseContext(), "This post is full", Toast.LENGTH_LONG).show();
    }

    private void onJoinJoinedPostFailed() {
        Toast.makeText(getBaseContext(), "You've already joined", Toast.LENGTH_LONG).show();
    }

    private void onJoinSuccess() {
        Toast.makeText(getBaseContext(), "Join success!", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        mList = findViewById(R.id.recommendation_list);

        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Intent intent = new Intent(RecommendationActivity.this, PostActivity.class);
                String message = messageList.get(position).getName();
                MyApplication application = (MyApplication) getApplication();
                try {
                    application.joinedPostId = jsonArr.getJSONObject(position).getInt("id");
                    joinPost();
                } catch (Exception e) {
                    System.out.println(e);
                }
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
}
