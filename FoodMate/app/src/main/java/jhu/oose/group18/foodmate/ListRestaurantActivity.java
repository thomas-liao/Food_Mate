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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRestaurantActivity extends AppCompatActivity{
//    MyRecyclerViewAdapter adapter;
//    ArrayList<String> nameArray;
//    JSONArray jsonArr;

    private RecyclerView mList;
    JSONArray jsonArr;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Restaurant> restaurantList;
    private RecyclerView.Adapter adapter;

    MyApplication application;
    private String url;



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
                                Restaurant restaurant = new Restaurant();
                                restaurant.setName(jsonObj.getString("name"));
                                restaurant.setCategory(jsonObj.getString("category"));
//                                restaurant.setYear(jsonObj.getInt("releaseYear"));
                                restaurantList.add(restaurant);
//                                nameArray[i] = jsonObj.getString("name");
//                                infoArray[i] = jsonObj.getString("category");
                            }
                            //System.out.println(response.toString());
                            //JSONArray a = new JSONArray();
                            //response.toJSONArray(a);
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
        setContentView(R.layout.activity_list_restaurant);

        mList = findViewById(R.id.restaurant_list);

        restaurantList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), restaurantList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();
    }

//    public void onItemClick(View view, int position) {
//        Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
//        String message = restaurantList.get(position).getName();
//        MyApplication application=(MyApplication)getApplication();
//        try {
//            application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
//        } catch (Exception e) {System.out.println(e);}
//        intent.putExtra("restaurantSelected", message);
//        startActivity(intent);
//    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
//        String message = nameArray.get(position);
//        MyApplication application = (MyApplication)getApplication();
//        try {
//            application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
//        } catch (Exception e) {System.out.println(e);}
//        intent.putExtra("restaurantSelected", message);
//        startActivity(intent);
//    }
}
//            @Override



//
//
//    String[] nameArray = {
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//            "restaurant",
//    };
//
//    String[] infoArray = {
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//            "info",
//    };
//
//    Integer[] imageArray = {R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo,
//            R.drawable.restaurant_logo};
//
//    ListView listView;
//
//    JSONArray jsonArr;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_restaurant);
//
//        ListAdapter whatever = new ListAdapter(this, R.layout.restaurant_row, nameArray, infoArray, imageArray);
//        listView = (ListView) findViewById(R.id.restaurant_list);
//        listView.setAdapter(whatever);
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        MyApplication application=(MyApplication)getApplication();
//        String url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/restaurants";
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
//                                nameArray[i] = jsonObj.getString("name");
//                                infoArray[i] = jsonObj.getString("category");
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
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
//                String message = nameArray[position];
//                MyApplication application=(MyApplication)getApplication();
//                try {
//                    application.restaurantId = jsonArr.getJSONObject(position).getInt("id");
//                } catch (Exception e) {System.out.println(e);}
//                intent.putExtra("restaurantSelected", message);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//
//}

