package jhu.oose.group18.foodmate;


import android.content.Intent;
import android.os.Bundle;

public class ReviewGuestHistoryActivity extends ReviewHistoryActivity{
    public ReviewGuestHistoryActivity() {
        super();
        url = "https://food-mate.herokuapp.com/user/" + application.userId + "/guest/posts";
        extraValue = "ReviewGuestHistoryActivity";
        intent = new Intent(getApplicationContext(), DetailedGuestResponseActivity.class);
    }
}