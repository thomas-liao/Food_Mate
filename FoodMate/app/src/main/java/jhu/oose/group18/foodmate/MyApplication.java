package jhu.oose.group18.foodmate;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyApplication extends Application {
    String globalVariable="My Global Variable";
    Integer userId;
    Integer restaurantId;
    Integer createdPostId;
    Integer joinedPostId;

    Integer reviewPostId;
    String reviewPostRes;
    String reviewPostHost;
    Integer reviewResId;
    String reviewPostStartDate;
}
