package com.android.blackoder.makta;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created By blackcoder
 * On 22/01/19
 **/
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void unlockScreen() {
        final MainActivity activity = mActivityActivityTestRule.getActivity();
        Runnable wakeUpDevice = () -> activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void testBottomNavigationNavigatesCorrectly() {

//        Navigate to Lent books view and check text view matches lent text
        onView(withId(R.id.navigation_lent)).perform(click());

//        Navigate to Search view and check text view matches book search text
        onView(withId(R.id.navigation_search)).perform(click());

//        Navigate to User View and  check text view matches profile text
        onView(withId(R.id.navigation_profile)).perform(click());
    }
}