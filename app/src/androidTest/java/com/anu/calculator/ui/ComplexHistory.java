package com.anu.calculator.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.anu.calculator.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ComplexHistory {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fffffff() {
        ViewInteraction tabView = onView(
                allOf(withContentDescription("Func"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("abc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                3),
                        isDisplayed()));
        tabView2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_a), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.equals), withText("="),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction tabView3 = onView(
                allOf(withContentDescription("Main"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                0),
                        isDisplayed()));
        tabView3.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.dgt_7), withText("7"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.evaluate), withText("Solve"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction tabView4 = onView(
                allOf(withContentDescription("abc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                3),
                        isDisplayed()));
        tabView4.perform(click());

        ViewInteraction tabView5 = onView(
                allOf(withContentDescription("Main"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                0),
                        isDisplayed()));
        tabView5.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.all_clear), withText("AC"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction tabView6 = onView(
                allOf(withContentDescription("abc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                3),
                        isDisplayed()));
        tabView6.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btn_b), withText("b"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.equals), withText("="),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                4),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btn_a), withText("a"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction tabView7 = onView(
                allOf(withContentDescription("Main"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                0),
                        isDisplayed()));
        tabView7.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.evaluate), withText("Solve"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                3),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction tabView8 = onView(
                allOf(withContentDescription("History"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.operations_tabs),
                                        0),
                                2),
                        isDisplayed()));
        tabView8.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.history_text),
                        childAtPosition(
                                withParent(withId(R.id.container)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("\na=7=7\nb=a=7")));
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
