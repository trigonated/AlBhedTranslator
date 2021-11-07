package com.trigonated.albhedtranslator.ui.alphabet

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.ui.MainActivity
import com.trigonated.albhedtranslator.ui.testutils.hasViewWithTextAtPosition
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class AlphabetFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun before() {
        // Navigate to the Alphabet fragment
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.drawer_nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_alphabet))
    }

    /**
     * Test if the items are presented in the correct order when sorting by al bhed.
     */
    @Test
    fun itemsInAlToEnOrder() {
        onView(withId(R.id.list))
            .check(hasViewWithTextAtPosition(0, "A ➞ E"))
    }

    /**
     * Test if the items are presented in the correct order when sorting by al english.
     */
    @Test
    fun itemsInEnToAlOrder() {
        Thread.sleep(500)
        onView(withId(R.id.action_sort)).perform(click())
        onView(withId(R.id.list))
            .check(hasViewWithTextAtPosition(0, "A ➞ Y"))
    }
}