package jhu.oose.group18.foodmate;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class DetailedHostPostActivityTest {
    @Rule
    public ActivityTestRule<DetailedHostPostActivity> DetailedHostPostActivityTestRule =
            new ActivityTestRule<>(DetailedHostPostActivity.class);

    @Test
    public void testDetailedHostPost() {



        onView(withId(R.id.reservation_time)).check(matches(isDisplayed()));

        onView(withId(R.id.guestList)).check(matches(isDisplayed()));

        onView(withId(R.id.joined_guest)).check(matches(isDisplayed()));

//        onView(withId(R.id.reservation_guest_list)).check(matches(isDisplayed()));

        onView(withId(R.id.return_btn)).check(matches(isDisplayed()));
        // perform button click to go to new event
        onView(withId(R.id.action_new_event)).perform(click());


    }
}
