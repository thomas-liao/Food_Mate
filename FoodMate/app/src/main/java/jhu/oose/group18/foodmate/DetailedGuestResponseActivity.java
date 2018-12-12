package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
    private Button _join;
    private Button _review;
    private Button _close;
    private RatingBar _ratingBar;
    private PopupWindow popupWindow;
    private String previousActivity;
    private Float rating;

    private SwipeRefreshLayout swipeRefreshLayout;

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

    private void onJoinOwnPostFailed() {
        Toast.makeText(getBaseContext(), "Cannot join Own Posts", Toast.LENGTH_LONG).show();
    }

    private void onJoinPostFullFailed() {
        _join.setVisibility(View.GONE);
        Toast.makeText(getBaseContext(), "This post is full", Toast.LENGTH_LONG).show();
    }

    private void onJoinJoinedPostFailed() {
        Toast.makeText(getBaseContext(), "You've already joined", Toast.LENGTH_LONG).show();
    }

    private void onJoinSuccess() {
        _join.setVisibility(View.GONE);
//        _review.setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), "Join success!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_guest);
        _review = findViewById(R.id.review_btn);
        _join = findViewById(R.id.join_btn);
        _return = findViewById(R.id.return_btn);

        previousActivity = getIntent().getStringExtra("FROM_ACTIVITY");
        if (previousActivity.equals("ReviewHistoryActivity")){
            _review.setVisibility(View.VISIBLE);
            _join.setVisibility(View.GONE);
        }
        if (previousActivity.equals("RecommendationActivity")){
            _review.setVisibility(View.GONE);
            _join.setVisibility(View.VISIBLE);
        }

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
                        }
                        return true;
                    }
                });

        messageList = new ArrayList<>();

        mList = findViewById(R.id.post_guest_list);
        reservation_name = findViewById(R.id.post_name);
        reservation_time = findViewById(R.id.post_time);
        reservation_description = findViewById(R.id.post_description);

        _join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.joinedPostId = application.reviewPostId;
                joinPost();
            }
        });

        _return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final RelativeLayout relativeLayout;
        relativeLayout = findViewById(R.id.relativelayout);
        _review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) DetailedGuestResponseActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                _close = customView.findViewById(R.id.submit_btn);
                _ratingBar = customView.findViewById(R.id.ratingBar);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                _close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rating = _ratingBar.getRating();
                        final MyApplication application=(MyApplication)getApplication();
                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            String URL = "https://food-mate.herokuapp.com/user/";
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("userId", application.userId);
                            jsonBody.put("restaurantId", application.reviewResId);
                            jsonBody.put("score", rating.intValue());

//                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                    Date dateObject;
//                    try{
//                        String dateTime = _dateTime.getText().toString();
//                        dateObject = formatter.parse(dateTime);
//                        jsonBody.put("startDate", dateObject.toString());
//                        System.out.println(dateObject.toString());
//
//                    }catch (java.text.ParseException e){
//                        _dateTime.setError("follow the format dd/MM/yyyy");
//                        e.printStackTrace();
//                    }
                            final String requestBody = jsonBody.toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("error " + error.toString());
                                    Log.e("VOLLEY", error.toString());
                                }
                            }) {
                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";
                                }

                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    try {
                                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                                    } catch (UnsupportedEncodingException uee) {
                                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                        return null;
                                    }
                                }


                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    String responseString = "";
                                    if (response != null) {
                                        responseString = String.valueOf(response.statusCode);
                                        // can get more details such as response.headers
                                    }
                                    return super.parseNetworkResponse(response);
                                }
                            };
                            System.out.println("create the request");
                            requestQueue.add(stringRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        popupWindow.dismiss();
                    }
                });
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
        message.setName(jsonObj.getString("userName"));
        message.setCategory(jsonObj.getString("description"));
        message.setPic(R.drawable.restaurant_logo);
        return message;
    }


}
