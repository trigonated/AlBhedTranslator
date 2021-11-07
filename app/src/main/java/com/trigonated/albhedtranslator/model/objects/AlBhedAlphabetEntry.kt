package com.trigonated.albhedtranslator.model.objects

/**
 * An entry in the alphabet.
 */
data class AlBhedAlphabetEntry(
    /** The Al Bhed character. **/
    val alBhed: Char,
    /** The english equivalent to [alBhed]. **/
    val english: Char
)