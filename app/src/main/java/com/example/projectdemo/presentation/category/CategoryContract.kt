package com.example.projectdemo.presentation.category

import com.example.projectdemo.base.MviContract
import com.example.projectdemo.domain.model.Category

interface CategoryContract : MviContract<CategoryContract.State, CategoryContract.Intent, CategoryContract.Effect> {
    
    // State
    data class State(
        val isLoading: Boolean = false,
        val categories: List<Category> = emptyList(),
        val error: String? = null
    ) : MviContract.State

    // Intent
    sealed class Intent : MviContract.Intent {
        object LoadCategories : Intent()
        object RefreshCategories : Intent()
        data class CategoryClicked(val category: Category) : Intent()
    }

    // Effect
    sealed class Effect : MviContract.Effect {
        data class ShowError(val message: String) : Effect()
        data class NavigateToCategoryDetail(val category: Category) : Effect()
    }
}
