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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmentvp_mvvm.soal2.ui.viewmodel.QuizUiState

@Composable
fun ResultScreen(
    uiState: QuizUiState,
    onRestartQuiz: () -> Unit
) {
    val wrongAnswers = uiState.answers.filter { !it.isCorrect }

    val backgroundColor = Color(0xFFFAF8FF)
    val blueColor = Color(0xFF4D628F)
    val textColor = Color(0xFF393943)
    val redColor = Color(0xFFB3261E)
    val dividerColor = Color(0xFFD0CED8)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Quiz Finished!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your Score: ${uiState.score} / ${uiState.questions.size}",
            fontSize = 14.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )
        if (wrongAnswers.isEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Perfect! No wrong answers.",
                fontSize = 14.sp,
                color = blueColor,
                textAlign = TextAlign.Center
            )
        } else {
            Spacer(modifier = Modifier.height(24.dp))

            wrongAnswers.forEach { result ->
                val questionNumber =
                    uiState.questions.indexOfFirst {
                        it.question == result.question.question
                    } + 1

                Text(
                    text = "Q$questionNumber: ${result.question.question}",
                    fontSize = 14.sp,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(0.5.dp))

                Text(
                    text = "Your Answer: ${result.selectedAnswer}",
                    fontSize = 14.sp,
                    color = redColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(0.5.dp))

                Text(
                    text = "Correct Answer: ${result.question.correctAnswer}",
                    fontSize = 14.sp,
                    color = blueColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(0.5.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(dividerColor)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(CircleShape)
                .background(blueColor)
                .clickable {
                    onRestartQuiz()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Restart Quiz",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}