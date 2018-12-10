package jhu.oose.group18.foodmate;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class DetailedGuestResponseActivityTest {

    @Rule
    public ActivityTestRule<DetailedGuestResponseActivity> DetailedGuestResponseActivityTestRule =
            new ActivityTestRule<>(DetailedGuestResponseActivity.class);

    @Test
    public void testDetailedGuestResponse() {


        onView(withId(R.id.joined_guest)).check(matches(isDisplayed()));

        onView(withId(R.id.post_time)).check(matches(isDisplayed()));

        onView(withId(R.id.guestList)).check(matches(isDisplayed()));

        onView(withId(R.id.join_btn)).check(matches(isDisplayed()));

        onView(withId(R.id.return_btn)).check(matches(isDisplayed()));
        // perform button click to go to new event
        onView(withId(R.id.action_new_event)).perform(click());


    }
}
