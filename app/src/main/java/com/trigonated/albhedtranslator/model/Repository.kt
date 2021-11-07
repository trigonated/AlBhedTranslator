package com.trigonated.albhedtranslator.model

import com.trigonated.albhedtranslator.misc.withCase
import com.trigonated.albhedtranslator.model.data.AlBhedAlphabetData
import com.trigonated.albhedtranslator.model.data.AlBhedPrimersData
import com.trigonated.albhedtranslator.model.objects.AlBhedAlphabetEntry
import com.trigonated.albhedtranslator.model.objects.AlBhedPrimer
import com.trigonated.albhedtranslator.model.objects.AlBhedString
import com.trigonated.albhedtranslator.model.objects.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The data repository.
 */
@Singleton
class Repository @Inject constructor(
    /** The app's preferences. */
    val appPreferences: AppPreferences
) {
    /** @see alphabetEntries */
    private val _alphabetEntries = MutableStateFlow<List<AlBhedAlphabetEntry>>(listOf())

    /** The list of alphabet entries. */
    val alphabetEntries: StateFlow<List<AlBhedAlphabetEntry>> get() = _alphabetEntries

    /** @see primers */
    private val _primers = MutableStateFlow<List<AlBhedPrimer>>(listOf())

    /** The list of primers. */
    val primers: StateFlow<List<AlBhedPrimer>> get() = _primers

    init {
        // Load the data
        loadAlphabetEntries()
        loadPrimers()
    }

    /**
     * Load the data related to the alphabet entries.
     */
    private fun loadAlphabetEntries() {
        _alphabetEntries.value = AlBhedAlphabetData.entries.toList()
    }

    /**
     * Load the data related to the primers.
     */
    private fun loadPrimers() {
        val basePrimersData: List<AlBhedPrimer> = AlBhedPrimersData.entries
        val obtainedPrimers: Set<Int> = appPreferences.obtainedPrimers
        // Combine the base primers data with the "obtained" flags from the preferences.
        _primers.value = basePrimersData.map {
            AlBhedPrimer(it, obtainedPrimers.contains(it.volume))
        }
    }

    /**
     * Gets whether the primer that translates the al bhed character [alBhedChar] is marked as
     * obtained or not.
     *
     * @param alBhedChar The al bhed character to check
     */
    fun isPrimerObtained(alBhedChar: Char): Boolean {
        return _primers.value.find { it.alBhedChar == alBhedChar.uppercaseChar() }?.isObtained
            ?: false
    }

    /**
     * Marks a [primer] as obtained (or not).
     *
     * @param primer The primer to change
     * @param isObtained Whether to mark the primer as obtained or not.
     */
    fun setPrimerObtained(primer: AlBhedPrimer, isObtained: Boolean) {
        val newPrimersValue: List<AlBhedPrimer> = _primers.value.map {
            if (it == primer) {
                AlBhedPrimer(it, isObtained)
            } else {
                it
            }
        }
        // Update the app preferences
        appPreferences.obtainedPrimers =
            newPrimersValue.mapNotNull { if (it.isObtained) it.volume else null }.toSet()
        // Update the flow
        _primers.value = newPrimersValue
    }

    /**
     * Translate [str] to english. There are two translation modes:
     *
     * [TranslationMode.FromObtainedPrimers]: auto-detect al bhed characters based on the obbtained
     * primers.
     *
     * [TranslationMode.Custom]: translate the characters marked as al bhed.
     *
     * @param str The string to translate
     * @param translationMode The translation mode
     */
    fun translateToEnglish(str: AlBhedString, translationMode: TranslationMode): String {
        val result = StringBuilder()

        var charLanguage: Language
        str.string.forEachIndexed { i, c ->
            // Detect the char's language
            charLanguage = when (translationMode) {
                TranslationMode.FromObtainedPrimers -> if (isPrimerObtained(c)) Language.English else Language.AlBhed
                TranslationMode.Custom -> if (str.isCharAlBhed(i)) Language.AlBhed else Language.English
            }
            // Translate the char (or not)
            result.append(
                when (charLanguage) {
                    Language.AlBhed -> translateChar(c, charLanguage)
                    Language.English -> c
                }
            )
        }
        return result.toString()
    }

    /**
     * Translate [str] to al bhed.
     *
     * @param str The string to translate
     */
    fun translateToAlBhed(str: AlBhedString): String {
        val result = StringBuilder()
        str.string.forEach { c ->
            result.append(translateChar(c, Language.English))
        }
        return result.toString()
    }

    /**
     * Translate a [char] into it's opposite language.
     *
     * @param char The char to translate.
     * @param charLanguage The language [char] is currently in.
     */
    private fun translateChar(char: Char, charLanguage: Language): Char {
        return when (charLanguage) {
            Language.AlBhed -> {
                alphabetEntries.value.firstOrNull { it.alBhed == char.uppercaseChar() }?.english?.withCase(
                    char
                )
            }
            Language.English -> {
                alphabetEntries.value.firstOrNull { it.english == char.uppercaseChar() }?.alBhed?.withCase(
                    char
                )
            }
        } ?: char
    }

    enum class TranslationMode {
        FromObtainedPrimers,
        Custom
    }
}