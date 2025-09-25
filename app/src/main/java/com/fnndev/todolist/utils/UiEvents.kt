package com.fnndev.todolist.utils

sealed class UiEvents {
    data class Navigate(val route: String) : UiEvents()
    object PopBackStack : UiEvents()
}