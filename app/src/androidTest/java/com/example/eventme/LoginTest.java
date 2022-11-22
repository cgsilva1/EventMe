package com.example.eventme;




import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;


import com.example.eventme.ui.login.LoginActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityTestRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void welcomePageTest() {
       // ViewInteraction imageView = onView( Matchers.allOf(ViewMatchers.withId(R.id.imageView), withContentDescription("logo"), withParent(allOf(withId(R.id.container), withParent(withId(android.R.id.content)))), isDisplayed()));

        imageView.check(matches(isDisplayed()));

        ViewInteraction button = onView( allOf(withId(R.id.login), withText("Sign in"), withParent(allOf(withId(R.id.container), withParent(withId(android.R.id.content)))), isDisplayed()));

        button.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView( allOf(withId(R.id.username), childAtPosition(R.id.container), withParent(allOf(withId(R.id.container), withParent(withId(android.R.id.content)))), isDisplayed()));

        button2.check(matches(isDisplayed()));


    }
}
