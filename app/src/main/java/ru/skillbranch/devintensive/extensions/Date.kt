package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND):Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date:Date = Date()): String {

    var diff = Date().time - this.time
    return when {
        diff < -360* DAY -> "более чем через год"
        diff < -26* HOUR -> "через ${dayPlur(diff/ DAY)}"
        diff < -22* HOUR -> "через день"
        diff < -75* MINUTE -> "через ${hourPlur(diff/ HOUR)}"
        diff < -45* MINUTE -> "через час"
        diff < -75* SECOND -> "через ${minutePlur(diff/ MINUTE)}"
        diff < -45* SECOND -> "через минуту"
        diff < -1* SECOND -> "через несколько секунд"
        diff > -1* SECOND && diff <= 1* SECOND -> "только что"
        diff <= 45* SECOND -> "несколько секунд назад"
        diff <= 75* SECOND -> "минуту назад"
        diff <= 45* MINUTE -> "${minutePlur(diff/ MINUTE)} назад"
        diff <= 75* MINUTE -> "час назад"
        diff <= 22* HOUR -> "${hourPlur(diff/ HOUR)} назад"
        diff <= 26* HOUR -> "день назад"
        diff <= 360* DAY -> "${dayPlur(diff/ DAY)} назад"
        else -> "более года назад"
    }
}

fun minutePlur(dif: Long):String{
    val diff = Math.abs(dif)
    return when {
        diff%100 in 11..19 -> "$diff минут"
        diff%10 in 2..4 -> "$diff минуты"
        diff%10 == 1L -> "$diff минуту"
        else -> "$diff минут"
    }
}

fun hourPlur(dif: Long):String{
    val diff = Math.abs(dif)
    return when {
        diff%100 in 11..19 -> "$diff часов"
        diff%10 in 2..4 -> "$diff часа"
        diff%10 == 1L -> "$diff час"
        else -> "$diff часов"
    }
}

fun dayPlur(dif: Long):String{
    val diff = Math.abs(dif)
    return when {
        diff%100 in 11..19 -> "$diff дней"
        diff%10 in 2..4 -> "$diff дня"
        diff%10 == 1L -> "$diff день"
        else -> "$diff дней"
    }
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}