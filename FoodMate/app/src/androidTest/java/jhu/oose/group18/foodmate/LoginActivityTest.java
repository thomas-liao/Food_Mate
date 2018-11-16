package jhu.oose.group18.foodmate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String username = "Amy";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUserLogin()
    {
        // input username 'Amy'
        Espresso.onView(withId(R.id.input_username)).perform(typeText(username));
        // input password 'Amy'
        Espresso.onView(withId(R.id.input_password)).perform(typeText(username));
//        Espresso.closeSoftKeyboard();
        // perform button click
        Espresso.onView(withId(R.id.btn_login)).perform(click());
        // checking the text in the textview
//        Espresso.onView(withId(R.id.tvChangedText)).check(matches(withText(mName)));
    }

    @After
    public void tearDown() throws Exception {
    }
}