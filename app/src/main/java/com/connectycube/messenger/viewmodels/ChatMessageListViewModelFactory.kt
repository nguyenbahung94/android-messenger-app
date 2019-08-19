package com.connectycube.messenger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.connectycube.chat.model.ConnectycubeChatDialog
import com.connectycube.messenger.data.ChatMessageRepository

class ChatMessageListViewModelFactory(
    private val repository: ChatMessageRepository,
    private val chat: ConnectycubeChatDialog
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = ChatMessageListViewModel(repository, chat) as T
}