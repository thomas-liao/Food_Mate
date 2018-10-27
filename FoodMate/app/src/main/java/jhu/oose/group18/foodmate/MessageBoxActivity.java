package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageBoxActivity extends AppCompatActivity {
//    @BindView(R.id.switch2) Button _switch2;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);
        ButterKnife.bind(this);

//        _switch2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RoleSelectActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
