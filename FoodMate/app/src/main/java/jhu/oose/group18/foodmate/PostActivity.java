package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
=======
>>>>>>> origin/thomas-liao

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {
    @BindView(R.id.btn_post) Button _postButton;
<<<<<<< HEAD
    @BindView(R.id.restaurant) EditText _restaurant;
    @BindView(R.id.dateTime) EditText _dateTime;
    @BindView(R.id.waitingTime) EditText _waitingTime;
    @BindView(R.id.maxGuest) EditText _maxGuest;


=======
>>>>>>> origin/thomas-liao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

<<<<<<< HEAD
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
                _postButton.setEnabled(false);
                postSuccess();
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
        String waitingTime = _waitingTime.getText().toString();


        if (resturant.isEmpty()) {
            _restaurant.setError("Enter valid Restaurant Name");
            valid = false;
        } else {
            _restaurant.setError(null);
        }

        return valid;
    }
=======
        _postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageBoxActivity.class);
                startActivity(intent);
            }
        });
    }
>>>>>>> origin/thomas-liao
}
