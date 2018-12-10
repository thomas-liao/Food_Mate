package jhu.oose.group18.foodmate;


import android.content.Intent;
import android.os.Bundle;

public class ReviewHostHistoryActivity extends ReviewHistoryActivity{
    public ReviewHostHistoryActivity() {
        super();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/host/posts";
        extraValue = "";
        intent = new Intent(getApplicationContext(), DetailedHostPostActivity.class);
    }
}