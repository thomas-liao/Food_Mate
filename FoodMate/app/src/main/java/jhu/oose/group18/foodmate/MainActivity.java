package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

<<<<<<< HEAD
=======

//
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Retrofit;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;





>>>>>>> origin/thomas-liao
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
<<<<<<< HEAD
    }
}
=======


        // TL oct 28
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://<yourapp>.herokuapp.com/")
                .build();

        final HerokuService service = retrofit.create(HerokuService.class);

    }
}
>>>>>>> origin/thomas-liao
