package com.example.eventme;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.eventme.ui.login.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class InvalidLoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void InvalidLoginTest() {
        // ViewInteraction imageView = onView( Matchers.allOf(ViewMatchers.withId(R.id.imageView), withContentDescription("logo"), withParent(allOf(withId(R.id.container), withParent(withId(android.R.id.content)))), isDisplayed()));
        // imageView.check(matches(isDisplayed()));

        //testing button
        ViewInteraction button = onView( allOf(withId(R.id.login), withText("Sign in"), withParent(allOf(withId(R.id.container), withParent(withId(android.R.id.content)))), isDisplayed()));
        button.check(matches(isDisplayed()));

        //testing username
        ViewInteraction appCompatEditText = onView( allOf(withId(R.id.username), childAtPosition( allOf(withId(R.id.container), childAtPosition( withId(android.R.id.content), 0)), 0), isDisplayed()));

        appCompatEditText.perform(replaceText("asbery@gmail.com"), closeSoftKeyboard());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //testing password
        ViewInteraction appCompatEditText2 = onView( allOf(withId(R.id.password), childAtPosition( allOf(withId(R.id.container), childAtPosition( withId(android.R.id.content), 0)), 1), isDisplayed()));
        appCompatEditText2.perform(replaceText("1114444"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton2 = onView( allOf(withId(R.id.login), withText("Sign in"), childAtPosition(allOf(withId(R.id.container), childAtPosition(withId(android.R.id.content), 0)), 2), isDisplayed()));
        materialButton2.perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button2 = onView(
                allOf(withId(R.id.login), withText("Sign in"), isDisplayed()));
        button2.check(matches(isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
