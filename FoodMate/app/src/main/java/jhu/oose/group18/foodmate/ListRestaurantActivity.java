package jhu.oose.group18.foodmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListRestaurantActivity extends AppCompatActivity {

    String[] nameArray = {"restaurant","restaurant" };

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
        setContentView(R.layout.activity_list_restaurant);

        ListAdapter whatever = new ListAdapter(this, R.layout.restaurant_row, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.restaurant_list);
        listView.setAdapter(whatever);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListRestaurantActivity.this, PostActivity.class);
                String message = nameArray[position];
                intent.putExtra("restaurantSelected", message);
                startActivity(intent);
            }
        });

    }


}
