package jhu.oose.group18.foodmate;

import android.support.test.espresso.contrib.RecyclerViewActions;

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
    public ActivityTestRule<LoginActivity> PostActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String username = "John";
    private String password = "John";

    @Test
    public void testPostSuccess()
    {
        // input existing username 'John'
        onView(withId(R.id.input_username)).perform(typeText(username));
        // input correct password 'John'
        onView(withId(R.id.input_password)).perform(typeText(password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // perform button click to login
        onView(withId(R.id.btn_login)).perform(click());

        onView(withId(R.id.btn_host)).perform(click());

        onView(ViewMatchers.withId(R.id.restaurant_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}