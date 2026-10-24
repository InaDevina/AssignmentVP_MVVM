package com.example.assignmentvp_mvvm.soal1.data

import com.example.assignmentvp_mvvm.soal1.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object ExpenseRepository {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private var nextId = 1

    fun addExpense(name: String, amount: Long){
        val expense = Expense(nextId, name, amount)
        _expenses.update {it + expense}
        nextId++
    }

    fun deleteExpense(id: Int){
        _expenses.update {list -> list.filter{it.id != id}}
    }

    fun updateExpense(id: Int, newAmount: Long){
        _expenses.update { list ->
            list.map {
                if (it.id == id) {
                    it.copy(amount = newAmount)
                } else {
                    it
                }
            }
        }
    }
}