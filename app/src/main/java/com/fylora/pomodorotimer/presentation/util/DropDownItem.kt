package com.fylora.pomodorotimer.presentation.util

sealed interface DropDownItem {
    data object Expand: DropDownItem
    data object Delete: DropDownItem

    companion object {
        val items = listOf(
            Expand, Delete
        )
    }
}