package com.example.sungwon.lifeuniverseeverything;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by SungWon on 9/9/2016.
 */
@RunWith(AndroidJUnit4.class)
public class EverythingListTest extends ApplicationTestCase<Application> {
    public EverythingListTest() {
        super(Application.class);
    }

    @Rule
    public ActivityTestRule<EverythingListActivity> mActivityRule = new ActivityTestRule<EverythingListActivity>(EverythingListActivity.class);

    @Test
    public void testclickable() throws Exception {
        onView(withId(R.id.StartButton))
                .check(matches(isClickable()));
    }
}
