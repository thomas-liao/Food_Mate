package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.ListView;
=======
>>>>>>> origin/thomas-liao

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageBoxActivity extends AppCompatActivity {
<<<<<<< HEAD
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);

        ListAdapter whatever = new ListAdapter(this, R.layout.message_row, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.message_list);
        listView.setAdapter(whatever);
=======
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
>>>>>>> origin/thomas-liao
    }
}
