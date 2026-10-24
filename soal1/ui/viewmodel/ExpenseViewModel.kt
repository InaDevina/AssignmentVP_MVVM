package com.example.assignmentvp_mvvm.soal1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentvp_mvvm.soal1.data.ExpenseRepository
import com.example.assignmentvp_mvvm.soal1.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpenseUiState(
    val name: String = "",
    val amount: String = "",
    val amountError: Boolean = false,
    val expenses: List<Expense> = emptyList(),
    val totalExpenses: Long = 0
)

class ExpenseViewModel : ViewModel(){
    private val repository = ExpenseRepository
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            repository.expenses.collect { list ->
                _uiState.update {
                    it.copy(
                        expenses = list,
                        totalExpenses = list.sumOf { expense -> expense.amount }
                    )
                }
            }
        }
    }

    fun onNameChange(value:String){
        _uiState.update {it.copy(name = value)}
    }

    fun onAmountChange(value: String){
        _uiState.update{
            it.copy(amount = value, amountError = false)
        }
    }

    fun addExpense(){
        val state = _uiState.value
        val amount = parseAmount(state.amount)

        if (state.name.isBlank() || amount == null || amount <= 0) {
            _uiState.update {it.copy(amountError = true)}
            return
        }

        repository.addExpense(state.name, amount)

        _uiState.update {
            it.copy(
                name = "",
                amount = "",
                amountError = false
            )
        }
    }

    fun deleteExpense(id: Int) {
        repository.deleteExpense(id)
    }

    fun updateExpense(id: Int, newAmount: String): Boolean {
        val amount = parseAmount(newAmount)

        if (amount == null || amount <= 0) {
            return false
        }
        repository.updateExpense(id, amount)
        return true
    }
}

private fun parseAmount(value: String): Long? {
    val normalized = value.trim()
        .replace(".", "")
        .replace(",", "")
        .replace(" ", "")
    return normalized.toLongOrNull()
}