package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

abstract class BaseMessage (
    val id: String,
    val from: User,
    val chat: Chat,
    val isIncoming: Boolean,
    val date: Date = Date(),
    var isReaded: Boolean = false
){

    abstract fun formatMessage():String

    companion object AbstractFactory{
        var lastId = -1
        fun makeMessage(from: User, chat: Chat, date: Date = Date(), type:String="text", payload:Any?, isIncoming: Boolean = false, isReaded: Boolean = false):BaseMessage{
            lastId++

            return when(type){
                "image" -> ImageMessage("$lastId", from, chat, isIncoming, date, isReaded, payload as String)
                    else -> TextMessage("$lastId", from, chat, isIncoming, date, isReaded, payload as String)
            }
        }
    }
}