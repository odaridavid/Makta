package com.android.blackoder.makta;

import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.collect.Range.all;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created By blackcoder
 * On 22/01/19
 **/
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBottomNavigationNavigatesCorrectly() {
//        Check text on activity launch
        onView(withId(R.id.message_text_view)).check(matches(withText(R.string.title_borrowed_books_view)));

//        Navigate to Lent books view and check text view matches lent text
        onView(withId(R.id.navigation_lent)).perform(click());
        onView(withId(R.id.message_text_view)).check(matches(withText(R.string.title_lent_books_view)));

//        Navigate to Search view and check text view matches book search text
        onView(withId(R.id.navigation_search)).perform(click());
        onView(withId(R.id.message_text_view)).check(matches(withText(R.string.title_book_search_view)));

//        Navigate to User View and  check text view matches profile text
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.message_text_view)).check(matches(withText(R.string.title_user_profile_view)));
    }
}