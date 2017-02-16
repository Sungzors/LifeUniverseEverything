package com.example.sungwon.lifeuniverseeverything;

import android.app.Application;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.example.sungwon.lifeuniverseeverything.Activity.EverythingListActivity;
import com.example.sungwon.lifeuniverseeverything.Utility.SQLHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

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
    public void testAdd() throws Exception {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.addName))
                .perform(clearText(), typeText("Testing"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.tagAdd))
                .perform(clearText(), typeText("just, testing, stuff"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.revAdd))
                .perform(clearText(), typeText("Type stuff here, too, man"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.ratingBar))
                .perform(click());

        onView(withId(R.id.addButton))
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.list_view))
                .atPosition(SQLHelper.getInstance(getContext()).getLastIDET())
                .onChildView(withId(R.id.everythingName))
                .check(matches(withText(startsWith("Testing"))));
    }

}
