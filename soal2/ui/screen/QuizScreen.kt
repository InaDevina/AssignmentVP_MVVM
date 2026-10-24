package com.example.assignmentvp_mvvm.soal2.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmentvp_mvvm.soal2.ui.viewmodel.QuizUiState

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit
) {
    val question = uiState.currentQuestion ?: return
    val isLastQuestion =
        uiState.currentQuestionIndex == uiState.questions.lastIndex
    val isAnswerSelected = uiState.selectedAnswer.isNotEmpty()
    val backgroundColor = Color(0xFFFAF8FF)
    val optionColor = Color(0xFFDFE5FA)
    val selectedColor = Color(0xFF4D628F)
    val textColor = Color(0xFF393943)
    val disabledColor = Color(0xFFE6E5EA)
    val disabledTextColor = Color(0xFFA7A6AE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Question ${uiState.currentQuestionIndex + 1}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = question.question,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            question.options.forEachIndexed { index, option ->

                val isSelected = uiState.selectedAnswer == option

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) selectedColor
                            else optionColor
                        )
                        .clickable(
                            role = Role.RadioButton
                        ) {
                            onAnswerSelected(option)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isSelected) {
                            Color.White
                        } else {
                            textColor
                        },
                        textAlign = TextAlign.Center
                    )
                }
                if (index != question.options.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isAnswerSelected) selectedColor
                        else disabledColor
                    )
                    .clickable(
                        enabled = isAnswerSelected,
                        role = Role.Button
                    ) {
                        onNextQuestion()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isLastQuestion) "Finish" else "Next",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isAnswerSelected) {
                        Color.White
                    } else {
                        disabledTextColor
                    }
                )
            }
        }
    }
}