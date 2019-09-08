package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class BaseViewModel: ViewModel() {
    private val repository : PreferencesRepository = PreferencesRepository
}