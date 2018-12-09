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


//@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

//    @Rule
//    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    private String username = "John";
    private String password = "John";
    private String wrong_password = "John11";

    private Timer timer = new Timer();


    @Test
    public void testUserLoginSuccess()
    {
        // input existing username 'John'
        onView(withId(R.id.input_username)).perform(typeText(username));
        // input correct password 'John'
        onView(withId(R.id.input_password)).perform(typeText(password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // perform button click to login
        onView(withId(R.id.btn_login)).perform(click());
        // check that after successfully login the next activity RoleSelectActivity is invoked
//        intended(hasComponent(RoleSelectActivity.class.getName()));
//        timer.schedule( new TimerTask(){
//            public void run() {
//                System.out.println("Wait for loading");
//            }
//        }, 2000);
//        // check that after successfully login the next activity RoleSelectActivity is invoked by viewmatch
//        onView(withId(R.id.btn_host)).check(matches(isDisplayed()));
    }

    @Test
    public void testUserLoginFailure()
    {
        // input existing username 'John'
        onView(withId(R.id.input_username)).perform(typeText(username));
        // input wrong password 'John11'
        onView(withId(R.id.input_password)).perform(typeText(wrong_password));
        // close the soft keyboard
        Espresso.closeSoftKeyboard();
        // perform button click
        onView(withId(R.id.btn_login)).perform(click());
        // checking the text in the textview
        onView(withText("Login failed")).
                inRoot(withDecorView(
                        not(is(loginActivityActivityTestRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }


    @Test
    public void testUserSignUP()
    {
        // click on the signup link
        onView(withText("No account yet? Create one")).perform(click());

        onView(withId(R.id.btn_signup)).check(matches(isDisplayed()));
    }


}
