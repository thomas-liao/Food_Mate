package jhu.oose.group18.foodmate;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.Timer;
import java.util.TimerTask;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.*;


public class PostActivityTest {

    @Rule
    public ActivityTestRule<PostActivity> PostActivityActivityTestRule =
            new ActivityTestRule<>(PostActivity.class);


    @Test
    public void testPostScreen() {
        String datetime = "12/10/2018";
        onView(withId(R.id.dateTime)).perform(typeText(datetime));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        String maxguest = "4";
        onView(withId(R.id.maxGuest)).perform(typeText(maxguest));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        String description = "Italian food";
        onView(withId(R.id.post_description)).perform(typeText(description));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.link_switchrole)).check(matches(isDisplayed()));

        // perform button click to post
        onView(withId(R.id.btn_post)).perform(click());


//        onView(ViewMatchers.withId(R.id.restaurant_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
