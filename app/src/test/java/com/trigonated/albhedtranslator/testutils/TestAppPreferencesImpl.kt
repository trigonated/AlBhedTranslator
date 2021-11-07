package com.trigonated.albhedtranslator.testutils

import com.trigonated.albhedtranslator.model.AppPreferences

class TestAppPreferencesImpl private constructor(vararg obtainedPrimers: Int) : AppPreferences {
    private var _obtainedPrimers: Set<Int>
    override var obtainedPrimers: Set<Int>
        get() = _obtainedPrimers
        set(value) {
            _obtainedPrimers = value
        }

    init {
        _obtainedPrimers = obtainedPrimers.toSet()
    }

    companion object {
        fun withNoneObtained(): TestAppPreferencesImpl = TestAppPreferencesImpl()

        fun withAllObtained(): TestAppPreferencesImpl = TestAppPreferencesImpl(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            0,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22,
            23,
            24,
            25,
            26
        )

        fun withVowelsObtained(): TestAppPreferencesImpl = TestAppPreferencesImpl(
            5,
            9,
            15,
            21,
            25
        )
    }
}