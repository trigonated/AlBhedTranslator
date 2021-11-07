package com.trigonated.albhedtranslator.model

/**
 * The application's preferences.
 */
interface AppPreferences {
    /**
     * The set of volume numbers of the obtained primers.
     *
     * Override the getter and setter to implement this functionality.
     */
    var obtainedPrimers: Set<Int>
}