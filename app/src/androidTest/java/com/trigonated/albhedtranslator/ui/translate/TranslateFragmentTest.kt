package com.trigonated.albhedtranslator.ui.translate

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.trigonated.albhedtranslator.R
import com.trigonated.albhedtranslator.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TranslateFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    /**
     * Write a string as input without marking anything as albhed.
     *
     * In result, none of the text should be translated.
     */
    @Test
    fun translateFullEnglishtoEnglish() {
        val str = "Hello World!"
        // Write the input
        onView(withId(R.id.input_edittext))
            .perform(typeText(str), closeSoftKeyboard())
        // Check the translation
        onView(withId(R.id.translation_edittext))
            .check(matches(withText(str)))
    }

    /**
     * Write a string as input marking some parts as albhed manually (aka custom mode).
     *
     * In result, the parts marked as albhed of the text should be translated.
     */
    @Test
    fun translateMixedToEnglish() {
        val albhedInput = "Rammu"
        val englishInput = " World!"
        val expectedOutput = "Hello World!"

        val inputEditText = withId(R.id.input_edittext)
        val writingAlBhedButton = withId(R.id.writing_albhed_button)
        val translationEditText = withId(R.id.translation_edittext)

        // Write the input
        onView(writingAlBhedButton).perform(click()) // Mark as al bhed
        onView(inputEditText).perform(typeText(albhedInput), closeSoftKeyboard()) // Write al bhed
        onView(writingAlBhedButton).perform(click()) // Unmark as albhed
        onView(inputEditText).perform(typeText(englishInput), closeSoftKeyboard()) // Write english
        // Check the translation
        onView(translationEditText).check(matches(withText(expectedOutput)))
    }
}