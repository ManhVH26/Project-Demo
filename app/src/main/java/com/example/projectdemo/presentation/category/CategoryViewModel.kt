package com.example.projectdemo.presentation.category

import androidx.lifecycle.viewModelScope
import com.example.projectdemo.base.BaseViewModel
import com.example.projectdemo.domain.model.Category
import com.example.projectdemo.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<CategoryContract.State, CategoryContract.Intent, CategoryContract.Effect>(
    CategoryContract.State()
) {
    
    override fun processIntent(intent: CategoryContract.Intent) {
        when (intent) {
            is CategoryContract.Intent.LoadCategories -> {
                loadCategories()
            }
            is CategoryContract.Intent.RefreshCategories -> {
                refreshCategories()
            }
            is CategoryContract.Intent.CategoryClicked -> {
                onCategoryClicked(intent.category)
            }
        }
    }
    
    private fun loadCategories() {
        setState { copy(isLoading = true, error = null) }
        
        // Real API call
        viewModelScope.launch {
            getCategoriesUseCase().fold(
                onSuccess = { categories ->
                    setState {
                        copy(
                            isLoading = false,
                            categories = categories,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    setState {
                        copy(
                            isLoading = false,
                            categories = emptyList(),
                            error = exception.message ?: "Unknown error"
                        )
                    }
                    setEffect {
                        CategoryContract.Effect.ShowError(exception.message ?: "Failed to load categories")
                    }
                }
            )
        }
    }
    
    private fun refreshCategories() {
        setState { copy(isLoading = true, error = null) }
        
        // Real API call for refresh
        viewModelScope.launch {
            getCategoriesUseCase().fold(
                onSuccess = { categories ->
                    setState {
                        copy(
                            isLoading = false,
                            categories = categories,
                            error = null
                        )
                    }
                },
                onFailure = { exception ->
                    setState {
                        copy(
                            isLoading = false,
                            categories = emptyList(),
                            error = exception.message ?: "Unknown error"
                        )
                    }
                    setEffect {
                        CategoryContract.Effect.ShowError(exception.message ?: "Failed to refresh categories")
                    }
                }
            )
        }
    }
    
    private fun onCategoryClicked(category: Category) {
        setEffect {
            CategoryContract.Effect.NavigateToCategoryDetail(category)
        }
    }
    
}
