package com.trigonated.albhedtranslator.model

import com.trigonated.albhedtranslator.model.objects.AlBhedString
import com.trigonated.albhedtranslator.testutils.TestAppPreferencesImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RepositoryTest {
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = Repository(TestAppPreferencesImpl.withVowelsObtained())
    }

    /**
     * Test various translations from al bhed to english.
     */
    @Test
    fun translateToEnglish() {
        // Test alphabet in custom mode
        var str = "YyPpLlTtAaVvKkRrEeZzGgMmSsHhUuBbXxNnCcDdIiJjFfQqOoWw"
        var alBhedParts = mutableListOf<Boolean>().apply { repeat(str.length) { add(true) } }
        var result: String = repository.translateToEnglish(
            AlBhedString(str, alBhedParts),
            Repository.TranslationMode.Custom
        )
        assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz", result)

        // Test phrase with letters, digits, spaces and symbols in custom mode
        str = "Rammu eh ahkmecr! 1234"
        alBhedParts = mutableListOf<Boolean>().apply { repeat(str.length) { add(true) } }
        result = repository.translateToEnglish(
            AlBhedString(str, alBhedParts),
            Repository.TranslationMode.Custom
        )
        assertEquals("Hello in english! 1234", result)

        // Test phrase with mixed english and Al Bhed in custom mode
        str = "YmPrat English EngPrat"
        alBhedParts = mutableListOf<Boolean>().apply {
            repeat("YmPrat".length) { add(true) }
            add(false)
            repeat("English".length) { add(false) }
            add(false)
            repeat("Eng".length) { add(false) }
            repeat("Prat".length) { add(true) }
        }
        result = repository.translateToEnglish(
            AlBhedString(str, alBhedParts),
            Repository.TranslationMode.Custom
        )
        assertEquals("AlBhed English EngBhed", result)

        // Test automatic mode
        str = "AEIOU aeiou BCD bcd"
        alBhedParts = mutableListOf<Boolean>().apply { repeat(str.length) { add(true) } }
        result = repository.translateToEnglish(
            AlBhedString(str, alBhedParts),
            Repository.TranslationMode.FromObtainedPrimers
        )
        assertEquals("AEIOU aeiou PST pst", result)
    }

    /**
     * Test various translations from english to al bhed.
     */
    @Test
    fun translateToAlBhed() {
        // Test alphabet
        var result: String =
            repository.translateToAlBhed(AlBhedString("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz"))
        assertEquals("YyPpLlTtAaVvKkRrEeZzGgMmSsHhUuBbXxNnCcDdIiJjFfQqOoWw", result)

        // Test phrase with letters, digits, spaces and symbols
        result = repository.translateToAlBhed(AlBhedString("Hello in english! 1234"))
        assertEquals("Rammu eh ahkmecr! 1234", result)
    }
}