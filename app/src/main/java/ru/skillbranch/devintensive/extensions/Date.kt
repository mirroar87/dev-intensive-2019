package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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
        diff < -26* HOUR -> "через ${TimeUnits.DAY.plural(diff/ DAY)}"
        diff < -22* HOUR -> "через день"
        diff < -75* MINUTE -> "через ${TimeUnits.HOUR.plural(diff/ HOUR)}"
        diff < -45* MINUTE -> "через час"
        diff < -75* SECOND -> "через ${TimeUnits.MINUTE.plural(diff/ MINUTE)}"
        diff < -45* SECOND -> "через минуту"
        diff < -1* SECOND -> "через несколько секунд"
        diff > -1* SECOND && diff <= 1* SECOND -> "только что"
        diff <= 45* SECOND -> "несколько секунд назад"
        diff <= 75* SECOND -> "минуту назад"
        diff <= 45* MINUTE -> "${TimeUnits.MINUTE.plural(diff/ MINUTE)} назад"
        diff <= 75* MINUTE -> "час назад"
        diff <= 22* HOUR -> "${TimeUnits.HOUR.plural(diff/ HOUR)} назад"
        diff <= 26* HOUR -> "день назад"
        diff <= 360* DAY -> "${TimeUnits.DAY.plural(diff/ DAY)} назад"
        else -> "более года назад"
    }
}

enum class TimeUnits{
    SECOND {
        override fun plural(long: Long): String {
            val diff = abs(long)
            return when {
                diff%100 in 11..19 -> "$diff секунд"
                diff%10 in 2..4 -> "$diff секунды"
                diff%10 == 1L -> "$diff секунда"
                else -> "$diff секунд"
            }
        }
    },
    MINUTE {
        override fun plural(long: Long): String {
            val diff = abs(long)
            return when {
                diff%100 in 11..19 -> "$diff минут"
                diff%10 in 2..4 -> "$diff минуты"
                diff%10 == 1L -> "$diff минуту"
                else -> "$diff минут"
            }
        }
    },
    HOUR {
        override fun plural(long: Long): String {
            val diff = abs(long)
            return when {
                diff%100 in 11..19 -> "$diff часов"
                diff%10 in 2..4 -> "$diff часа"
                diff%10 == 1L -> "$diff час"
                else -> "$diff часов"
            }
        }
    },
    DAY {
        override fun plural(long: Long): String {
            val diff = abs(long)
            return when {
                diff%100 in 11..19 -> "$diff дней"
                diff%10 in 2..4 -> "$diff дня"
                diff%10 == 1L -> "$diff день"
                else -> "$diff дней"
            }
        }
    };
    abstract fun plural(long: Long): String
}