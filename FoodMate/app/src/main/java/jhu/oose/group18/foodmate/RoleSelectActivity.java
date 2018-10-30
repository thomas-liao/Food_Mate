package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        _hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListRestaurantActivity.class);
                startActivity(intent);
            }
        });

        _guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecommendationActivity.class);
                startActivity(intent);
            }
        });
    }
}
