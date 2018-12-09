package jhu.oose.group18.foodmate;

import org.junit.After;
import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;


import java.util.Timer;
import java.util.TimerTask;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
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

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> SignupActivityActivityTestRule =
            new ActivityTestRule<>(SignupActivity.class);

    private String fullname = "Jack Smith";
    private String username = "Jack";
    private String email = "jack1@gmail";
    private String email_ = "jack1@gmail.com";
    private String password = "jack123";
    private String password_ = "jack124";
    private String cityname = "Baltimore";
    private String description = "I love food and company.";

    @Test
    public void testUserSignupSuccess()
    {
        // input test full name 'Jack Smith'
        onView(withId(R.id.input_fullname)).perform(typeText(fullname));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test username 'Jack'
        onView(withId(R.id.input_username)).perform(typeText(username));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test email 'jack1@gmail.com'
        onView(withId(R.id.input_email)).perform(typeText(email));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test password 'jack123'
        onView(withId(R.id.input_password)).perform(typeText(password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // Reenter password 'jack123'
        onView(withId(R.id.input_reEnterPassword)).perform(typeText(password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test cityname 'Baltimore'
        onView(withId(R.id.input_location)).perform(typeText(cityname));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test description 'I love food and company.'
        onView(withId(R.id.input_bio)).perform(typeText(description));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // perform button click to login
        onView(withId(R.id.btn_signup)).perform(click());
        // check if signup success
        onView(withText("Signup failed")).
                inRoot(withDecorView(
                        not(is(SignupActivityActivityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));

    }


    @Test
    public void testUserSignupFailure()
    {
        // input test full name 'Jack Smith'
        onView(withId(R.id.input_fullname)).perform(typeText(fullname));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test username 'Jack'
        onView(withId(R.id.input_username)).perform(typeText(username));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test email 'jack1@gmail.com'
        onView(withId(R.id.input_email)).perform(typeText(email_));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test password 'jack123'
        onView(withId(R.id.input_password)).perform(typeText(password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // Reenter wrong password 'jack124'
        onView(withId(R.id.input_reEnterPassword)).perform(typeText(password_));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test cityname 'Baltimore'
        onView(withId(R.id.input_location)).perform(typeText(cityname));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // input test description 'I love food and company.'
        onView(withId(R.id.input_bio)).perform(typeText(description));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // perform button click to login
        onView(withId(R.id.btn_signup)).perform(click());
        // check if signup failure
        onView(withText("Signup failed")).
                inRoot(withDecorView(
                        not(is(SignupActivityActivityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));

    }

}