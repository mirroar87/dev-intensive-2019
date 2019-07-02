package ru.skillbranch.devintensive.utils

import android.annotation.SuppressLint


object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{
        val parts : List<String>? = fullName?.trim()?.replace("[\\s]{2,}".toRegex(), " ")?.split(" ")

        val firstName = if(parts?.getOrNull(0).isNullOrBlank()) null else parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    private val transliterationChars = mapOf("а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d", "е" to "e", "ё" to "e",
        "ж" to "zh", "з" to "z", "и" to "i", "й" to "i", "к" to "k", "л" to "l", "м" to "m", "н" to "n", "о" to "o",
        "п" to "p", "р" to "r", "с" to "s", "т" to "t", "у" to "u", "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch",
        "ш" to "sh", "щ" to "sh'", "ъ" to "", "ы" to "i", "ь" to "", "э" to "e", "ю" to "yu", "я" to "ya"
    )

    @SuppressLint("DefaultLocale")
    fun transliteration(payload: String, divider:String = " "): String {
        var translit = payload
        for ((k, v) in transliterationChars) {
            translit = translit.replace(k, v)
            translit = translit.replace(k.toUpperCase(), v.toUpperCase())
        }
        if (divider != " ") {
            translit = translit.replace(" ", divider)
        }
        return translit
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val initialsFirstName: Char? = firstName?.trim()?.firstOrNull()?.toUpperCase()
        val initialsLastName: Char? = lastName?.trim()?.firstOrNull()?.toUpperCase()

        return when {
            initialsFirstName == null && initialsLastName == null -> null
            initialsFirstName != null && initialsLastName == null -> initialsFirstName.toString()
            initialsFirstName == null && initialsLastName != null -> initialsLastName.toString()
            else -> initialsFirstName.toString() + initialsLastName.toString()
        }
    }
}