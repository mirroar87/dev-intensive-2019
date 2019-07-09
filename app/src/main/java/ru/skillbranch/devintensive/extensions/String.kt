package ru.skillbranch.devintensive.extensions

fun String.truncate(int: Int=16):String {
    return if (this.trim().length > int) {
        "${this.substring(0, int).trim()}..."
    } else {
        this.trim()
    }
}

fun String.stripHtml():String {
    return this.replace("<.*?>".toRegex(), "").replace("&.*?;".toRegex(), "").replace("\\s{2,}".toRegex(), " ")
}