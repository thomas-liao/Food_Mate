package jhu.oose.group18.foodmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageBoxActivity extends AppCompatActivity {
    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Message> messageList;
    private RecyclerView.Adapter adapter;

    MyApplication application;
    private String url;


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        application = (MyApplication) getApplication();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts/" + application.createdPostId + "/guests";
        System.out.println(url);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        try {
                            jsonArr = new JSONArray(response);

                            for (int i = 0; i < jsonArr.length(); i++)
                            {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                Message message = new Message();
                                System.out.println(jsonObj);
                                message.setName(jsonObj.getString("fullName"));
                                messageList.add(message);
                            }
                        } catch (Exception e) {System.out.println(e);}
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
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
        setContentView(R.layout.activity_message_box);

        mList = findViewById(R.id.message_list);

        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), messageList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MessageBoxActivity.this, PostActivity.class);
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

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message_box);
//
//        ListAdapter whatever = new ListAdapter(this, R.layout.message_row, nameArray, infoArray, imageArray);
//        listView = (ListView) findViewById(R.id.message_list);
//        listView.setAdapter(whatever);
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        MyApplication application=(MyApplication)getApplication();
//        String url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts/" + application.createdPostId + "/guests";
//        System.out.println(url);
//        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // display response
//                        try {
//                            jsonArr = new JSONArray(response);
//
//                            for (int i = 0; i < jsonArr.length(); i++)
//                            {
//                                JSONObject jsonObj = jsonArr.getJSONObject(i);
//                                System.out.println(jsonObj);
//                                nameArray[i] = jsonObj.getString("fullName");
//                            }
//                            //System.out.println(response.toString());
//                            //JSONArray a = new JSONArray();
//                            //response.toJSONArray(a);
//                        } catch (Exception e) {System.out.println(e);}
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//
//        );
//        getRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        queue.add(getRequest);
//
//    }


}



