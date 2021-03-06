package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {
    @BindView(R.id.btn_post) Button _postButton;
    @BindView(R.id.restaurant) EditText _restaurant;
    @BindView(R.id.dateTime) EditText _dateTime;
    @BindView(R.id.post_description) EditText _postDescription;
    @BindView(R.id.maxGuest) EditText _maxGuest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

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

        String restaurantSelected = getIntent().getStringExtra("restaurantSelected");
        _restaurant.setText(restaurantSelected);
        _restaurant.setFocusable(false);
        _restaurant.setEnabled(false);

        _postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    postFailed();
                    return;
                }
                final MyApplication application=(MyApplication)getApplication();
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String URL = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts/" + application.restaurantId;
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("numOfGuest", _maxGuest.getText().toString());
                    jsonBody.put("startDate", _dateTime.getText().toString());
                    jsonBody.put("description", _postDescription.getText().toString());

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
                            if (response == null) {
                                postFailed();
//                                return;
                            } else {
                                application.createdPostId = Integer.parseInt(response);
                                _postButton.setEnabled(false);
                                postSuccess();
                            }
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


//                if (!validate()) {
//                    postFailed();
//                    return;
//                }
//                _postButton.setEnabled(false);
//                postSuccess();
            }
        });
    }

    private void postSuccess() {
        Toast.makeText(getBaseContext(), "Post finished", Toast.LENGTH_LONG).show();
        _postButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(),MessageBoxActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void postFailed() {
        Toast.makeText(getBaseContext(), "Post failed", Toast.LENGTH_LONG).show();
        _postButton.setEnabled(true);
    }



    private boolean validate() {
        boolean valid = true;

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObject;
        try{
            String dateTime = _dateTime.getText().toString();
            dateObject = formatter.parse(dateTime);
//            String date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
//            String time = new SimpleDateFormat("h:mmaa").format(dateObject);
//        Toast.makeText(getBaseContext(), date + time, Toast.LENGTH_LONG).show();
        }catch (java.text.ParseException e){
            _dateTime.setError("follow the format dd/MM/yyyy");
            valid = false;
            e.printStackTrace();
        }
        String resturant = _restaurant.getText().toString();
        String maxGuest = _maxGuest.getText().toString();
        String waitingTime = _postDescription.getText().toString();


        if (resturant.isEmpty()) {
            _restaurant.setError("Enter valid Message Name");
            valid = false;
        } else {
            _restaurant.setError(null);
        }

        return valid;
    }
}
