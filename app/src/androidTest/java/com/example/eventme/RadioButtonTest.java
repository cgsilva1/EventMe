package com.example.eventme;


        import static androidx.test.espresso.Espresso.onView;
        import static androidx.test.espresso.action.ViewActions.click;
        import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
        import static androidx.test.espresso.assertion.ViewAssertions.matches;
        import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
        import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static androidx.test.espresso.matcher.ViewMatchers.withChild;
        import static androidx.test.espresso.matcher.ViewMatchers.withId;
        import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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

        import org.hamcrest.Description;
        import org.hamcrest.Matcher;
        import org.hamcrest.Matchers;
        import org.hamcrest.TypeSafeMatcher;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class RadioButtonTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void RadioButtonTest() {

        //open profile page
        ViewInteraction radioButton1 = onView(withId(R.id.alphRBtn));
        radioButton1.perform(click());
        radioButton1.check(matches(isChecked()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction radioButton2 = onView(withId(R.id.costRBtn));
        radioButton2.perform(click());
        radioButton2.check(matches(isChecked()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction radioButton3 = onView(withId(R.id.dateRBtn));
        radioButton3.perform(click());
        radioButton3.check(matches(isChecked()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction radioButton4 = onView(withId(R.id.distRBtn));
        radioButton4.perform(click());
        radioButton4.check(matches(isChecked()));
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