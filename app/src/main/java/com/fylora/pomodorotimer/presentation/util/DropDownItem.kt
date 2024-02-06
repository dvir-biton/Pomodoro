package com.fylora.pomodorotimer.presentation.util

sealed interface DropDownItem {
    data object Edit: DropDownItem
    data object Delete: DropDownItem

    companion object {
        val items = listOf(
            Edit, Delete
        )
    }
}