package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
import butterknife.BindView;
import butterknife.ButterKnife;
=======
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

>>>>>>> origin/thomas-liao

public class RoleSelectActivity extends AppCompatActivity {

    @BindView(R.id.btn_host)
    Button _hostButton;

    @BindView(R.id.btn_guest)
    Button _guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);
        ButterKnife.bind(this);

<<<<<<< HEAD
        _hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListRestaurantActivity.class);
=======
//        _hostButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),PostActivity.class);
//                startActivity(intent);
//            }
//        });

        _guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
>>>>>>> origin/thomas-liao
                startActivity(intent);
            }
        });

<<<<<<< HEAD
        _guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecommendationActivity.class);
                startActivity(intent);
            }
        });
=======

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:8080/")
                .build();

        final HerokuService service = retrofit.create(HerokuService.class);

        _hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = service.hello();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> _,
                                           Response<ResponseBody> response) {
                        try {
                            System.out.println(response.body().string());
//                            textView.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
//                            textView.setText(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> _, Throwable t) {
                        t.printStackTrace();
                        System.out.println(t.getMessage());
//                        textView.setText(t.getMessage());
                    }
                });
            }
        });





>>>>>>> origin/thomas-liao
    }
}
