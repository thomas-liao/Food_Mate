package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendationActivity extends AppCompatActivity {

    String[] nameArray = {"recommendation","recommendation" };

    String[] infoArray = {
            "this is a test message",
            "this is a test message",
            "this is a test message",

    };

    Integer[] imageArray = {R.drawable.restaurant_logo,
            R.drawable.restaurant_logo,
            R.drawable.restaurant_logo,
            R.drawable.restaurant_logo,
            R.drawable.restaurant_logo,
            R.drawable.restaurant_logo,
            R.drawable.restaurant_logo};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        ListAdapter whatever = new ListAdapter(this, R.layout.recommendation_row, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.recommendation_list);
        listView.setAdapter(whatever);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
//                String message = nameArray[position];
//                intent.putExtra("restaurantSelected", message);
//                startActivity(intent);
//            }
//        });

    }


}
