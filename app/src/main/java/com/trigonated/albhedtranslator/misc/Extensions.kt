package com.trigonated.albhedtranslator.misc

/**
 * Converts this character to the same case as the character [c].
 */
fun Char.withCase(c: Char): Char {
    return if (c.isUpperCase()) this.uppercaseChar() else this.lowercaseChar()
}

private val M = arrayOf("", "M", "MM", "MMM")
private val C = arrayOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
private val X = arrayOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
private val I = arrayOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")

/**
 * Converts this integer to it's roman numeral representation.
 */
fun Int.toRomanNumeral(): String {
    return M[this / 1000] + C[this % 1000 / 100] + X[this % 100 / 10] + I[this % 10]
}