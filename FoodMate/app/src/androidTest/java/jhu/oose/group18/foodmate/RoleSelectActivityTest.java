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




public class RoleSelectActivityTest {
    @Rule
    public ActivityTestRule<RoleSelectActivity> RoleSelectActivityTestRule =
            new ActivityTestRule<>(RoleSelectActivity.class);

    @Test
    public void testRoleSelect() {


        onView(withId(R.id.textView)).check(matches(withText(R.string.choose_your_role)));

        onView(withId(R.id.btn_host)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_guest)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_host)).check(isCompletelyAbove(withId(R.id.btn_guest)));

        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_guest)).check(isCompletelyAbove(withId(R.id.bottom_navigation)));

        // perform button click to return to login
        onView(withId(R.id.action_log_out)).perform(click());


    }
}
