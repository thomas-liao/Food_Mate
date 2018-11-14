package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageBoxActivity extends AppCompatActivity {
    String[] nameArray = {"message", "message"};

    String[] infoArray = {
            "this is a test message",
            "this is a test message",
            "this is a test message",

    };

    Integer[] imageArray = {R.drawable.ava1,
            R.drawable.ava2,
            R.drawable.ava3};

    ListView listView;
    JSONArray jsonArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);

        ListAdapter whatever = new ListAdapter(this, R.layout.message_row, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.message_list);
        listView.setAdapter(whatever);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        MyApplication application=(MyApplication)getApplication();
        String url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts/" + application.createdPostId + "/guests";
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
                                System.out.println(jsonObj);
                                nameArray[i] = jsonObj.getString("fullName");
                            }
                            //System.out.println(response.toString());
                            //JSONArray a = new JSONArray();
                            //response.toJSONArray(a);
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
        queue.add(getRequest);

    }


}
