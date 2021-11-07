package com.trigonated.albhedtranslator.model.objects

import androidx.annotation.StringRes
import com.trigonated.albhedtranslator.misc.toRomanNumeral
import com.trigonated.albhedtranslator.model.data.AlBhedAlphabetData

/**
 * An Al Bhed primer found in the game.
 */
data class AlBhedPrimer(
    /** The number of the volume. */
    val volume: Int,
    /** The alphabet entry this volume teaches. */
    val alphabetEntry: AlBhedAlphabetEntry,
    /** The string describing the location of the primer. */
    @StringRes
    val locationResId: Int,
    /** Whether this primer is marked as "obtained" or not. */
    val isObtained: Boolean = false
) {
    /** The volume number as a roman numeral. */
    val volumeAsRomanNumeral: String get() = volume.toRomanNumeral()

    /** The Al Bhed character this primer translates. */
    val alBhedChar: Char get() = alphabetEntry.alBhed

    /** The translation of [alBhedChar]. */
    val englishChar: Char get() = alphabetEntry.english

    constructor(
        volume: Int,
        alBhedLetter: Char,
        @StringRes
        locationResId: Int,
        isObtained: Boolean = false
    ) : this(
        volume = volume,
        alphabetEntry = AlBhedAlphabetData.entries.first { it.alBhed == alBhedLetter.uppercaseChar() },
        locationResId = locationResId,
        isObtained = isObtained
    )

    constructor(primer: AlBhedPrimer, isObtained: Boolean) : this(
        volume = primer.volume,
        alphabetEntry = primer.alphabetEntry,
        locationResId = primer.locationResId,
        isObtained = isObtained
    )
}