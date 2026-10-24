package com.example.assignmentvp_mvvm.soal2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.assignmentvp_mvvm.soal2.ui.viewmodel.QuizUiState

@Composable
fun StartScreen(
    uiState: QuizUiState,
    onQuestionCountChange: (String) -> Unit,
    onStartQuiz: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mini Quiz",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "How many questions do you want? (1 - 8)",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.questionCount,
            onValueChange = onQuestionCountChange,
            label = { Text("Number of Questions") },
            placeholder = { Text("Enter 1-${uiState.maxQuestions}") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            isError = uiState.inputError,
            supportingText = {
                if (uiState.inputError) {
                    val count = uiState.questionCount.toIntOrNull()

                    if (count != null && count > uiState.maxQuestions) {
                        Text("Maximum is ${uiState.maxQuestions} questions")
                    } else {
                        Text("Please enter a number between 1 and ${uiState.maxQuestions}")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF92929C),
                focusedBorderColor = Color(0xFF4D628F)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        val count = uiState.questionCount.toIntOrNull()

        Button(
            onClick = onStartQuiz,
            enabled = count != null && count in 1..uiState.maxQuestions,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
    }
}