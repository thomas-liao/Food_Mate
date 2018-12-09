package jhu.oose.group18.foodmate;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;



public class RecommendationActivityTest {

    @Rule
    public ActivityTestRule<RecommendationActivity> RecommendationActivityTestRule =
            new ActivityTestRule<>(RecommendationActivity.class);

    @Test
    public void testRecommendation() {


        onView(withId(R.id.no_post)).check(matches(withText(R.string.waiting_for_new_posts)));

        onView(withId(R.id.SwipeRefreshLayout)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        onView(withId(R.id.no_post)).check(isCompletelyAbove(withId(R.id.bottom_navigation)));

        // perform button click to go to new event
        onView(withId(R.id.action_new_event)).perform(click());

    }
}
