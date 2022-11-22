package com.example.eventme;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.eventme.ui.register.Register;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class DuplicateEmailRegisterTest {

    @Rule
    public ActivityScenarioRule<Register> mActivityTestRule = new ActivityScenarioRule<>(Register.class);

    @Test
    public void DuplicateEmailRegisterTest() {

        //testing button
        ViewInteraction materialButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.registerBtn), withText("Register"),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //testing name
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameReg),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("test test"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText1 = onView(
                allOf(withId(R.id.nameReg),
                        isDisplayed()));
        appCompatEditText1.perform(pressImeActionButton());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //testing username
        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.emailReg),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("trev@usc.edu"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailReg),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //testing dob
        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.dobReg),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("00/00/0000"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dobReg),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //testing password
        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.passwordReg),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("1234567"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.passwordReg),
                        isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //testing confirm password
        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.confirmPasswordReg),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("1234567"), closeSoftKeyboard());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.confirmPasswordReg),
                        isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //submit sign up
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.registerBtn), withText("Register"),
                        isDisplayed()));
        materialButton2.perform(click());
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
