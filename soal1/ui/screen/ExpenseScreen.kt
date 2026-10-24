package com.example.assignmentvp_mvvm.soal1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmentvp_mvvm.soal1.model.Expense
import com.example.assignmentvp_mvvm.soal1.ui.viewmodel.ExpenseUiState
import com.example.assignmentvp_mvvm.soal1.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.util.Locale

private val cardColor = Color(0xFFDDE3FA)

private fun formatRupiah(amount: Long): String {
    val formatter = NumberFormat.getNumberInstance(
        Locale.forLanguageTag("id-ID")
    )
    return "Rp ${formatter.format(amount)}"
}

private fun parseAmountInput(value: String): Long? {
    val normalized = value.trim()
        .replace(".", "")
        .replace(",", "")
        .replace(" ", "")

    return normalized.toLongOrNull()
}

@Composable
fun ExpenseScreen(
    viewModel: ExpenseViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExpenseContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onAmountChange = viewModel::onAmountChange,
        onAddExpense = viewModel::addExpense,
        onDeleteExpense = viewModel::deleteExpense,
        onUpdateExpense = viewModel::updateExpense
    )
}

@Composable
fun ExpenseContent(
    uiState: ExpenseUiState,
    onNameChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onAddExpense: () -> Unit,
    onDeleteExpense: (Int) -> Unit,
    onUpdateExpense: (Int, String) -> Boolean
) {

    var editingExpenseId by remember {
        mutableStateOf<Int?>(null)
    }
    var editAmount by remember {
        mutableStateOf("")
    }
    var editError by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 40.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Text(
                text = "Expenses",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Total Expense : ${formatRupiah(uiState.totalExpenses)}",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                label = { Text("Expense Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.amount,
                    onValueChange = onAmountChange,
                    label = { Text("Amount of Expense") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    isError = uiState.amount == "0" || uiState.amountError,
                    supportingText = {
                        if (parseAmountInput(uiState.amount) == 0L) {
                            Text("Amount must be greater than 0")
                        } else if (uiState.amountError) {
                            Text("Please enter a valid expense name and amount")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                val amount = parseAmountInput(uiState.amount)
                val isFormValid = uiState.name.isNotBlank() &&
                        amount != null && amount > 0

                FilledTonalButton(
                    onClick = onAddExpense,
                    enabled = isFormValid,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Text("+", style = MaterialTheme.typography.headlineSmall)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            items = uiState.expenses,
            key = { it.id }
        ) { expense ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        cardColor,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Name : ${expense.name}",
                        fontWeight = FontWeight.Bold
                    )

                    if (editingExpenseId == expense.id) {

                        OutlinedTextField(
                            value = editAmount,
                            onValueChange = {
                                editAmount = it
                                editError = false
                            },
                            label = {
                                Text("New Amount")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            singleLine = true,
                            isError = parseAmountInput(editAmount) == 0L || editError,
                            supportingText = {
                                if (parseAmountInput(editAmount) == 0L) {
                                    Text("Amount must be greater than 0")
                                } else if (editError) {
                                    Text("Please enter a valid amount")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            TextButton(
                                onClick = {
                                    val success = onUpdateExpense(
                                        expense.id,
                                        editAmount
                                    )

                                    if (success) {
                                        editingExpenseId = null
                                        editAmount = ""
                                        editError = false
                                    } else {
                                        editError = true
                                    }
                                }
                            ) {
                                Text("Save")
                            }

                            TextButton(
                                onClick = {
                                    editingExpenseId = null
                                    editAmount = ""
                                    editError = false
                                }
                            ) {
                                Text("Cancel")
                            }
                        }

                    } else {
                        Text(
                            text = formatRupiah(expense.amount),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                IconButton(
                    onClick = {
                        editingExpenseId = expense.id
                        editAmount = expense.amount.toString()
                        editError = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Expense",
                        tint = Color(0xFFD19B00)
                    )
                }

                IconButton(
                    onClick = {
                        onDeleteExpense(expense.id)

                        if (editingExpenseId == expense.id) {
                            editingExpenseId = null
                            editAmount = ""
                            editError = false
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Expense",
                        tint = Color(0xFFB3261E)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExpenseScreenPreview() {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf(false) }

    var expenses by remember {
        mutableStateOf(
            listOf(
                Expense(1, "Nasi Pecel", 18000),
                Expense(2, "Kopi", 50000)
            )
        )
    }

    var nextId by remember { mutableStateOf(3) }

    MaterialTheme {
        ExpenseContent(
            uiState = ExpenseUiState(
                name = name,
                amount = amount,
                amountError = amountError,
                expenses = expenses,
                totalExpenses = expenses.sumOf { it.amount }
            ),

            onNameChange = {
                name = it
                amountError = false
            },

            onAmountChange = {
                amount = it
                amountError = false
            },

            onAddExpense = {
                val newAmount = parseAmountInput(amount)

                if (
                    name.isBlank() ||
                    newAmount == null ||
                    newAmount <= 0
                ) {
                    amountError = true
                } else {
                    expenses = expenses + Expense(
                        id = nextId,
                        name = name.trim(),
                        amount = newAmount
                    )

                    nextId++
                    name = ""
                    amount = ""
                    amountError = false
                }
            },

            onDeleteExpense = { id ->
                expenses = expenses.filter { it.id != id }
            },

            onUpdateExpense = { id, newAmountText ->
                val newAmount = parseAmountInput(newAmountText)

                if (newAmount == null || newAmount <= 0) {
                    false
                } else {
                    expenses = expenses.map { expense ->
                        if (expense.id == id) {
                            expense.copy(amount = newAmount)
                        } else {
                            expense
                        }
                    }
                    true
                }
            }
        )
    }
}