package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    Intent intent;
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
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
                                intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });

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
