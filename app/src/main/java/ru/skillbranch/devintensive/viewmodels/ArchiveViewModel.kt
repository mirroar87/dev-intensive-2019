package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class ArchiveViewModel: BaseViewModel() {
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()) { chats ->
        return@map chats
            .filter { it.isArchived }
            .map { it.toChatItemFromArchive() }
            .sortedBy { it.id.toInt() }
    }

    fun getChatData(): LiveData<List<ChatItem>> {
        return chats
    }

    fun addToArchive(chatid: String) {
        val chat = chatRepository.find(chatid)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatid: String){
        val chat = chatRepository.find(chatid)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }
}