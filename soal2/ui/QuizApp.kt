package com.example.assignmentvp_mvvm.soal2.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmentvp_mvvm.soal2.ui.screen.StartScreen
import com.example.assignmentvp_mvvm.soal2.ui.screen.QuizScreen
import com.example.assignmentvp_mvvm.soal2.ui.screen.ResultScreen
import com.example.assignmentvp_mvvm.soal2.ui.viewmodel.QuizPage
import com.example.assignmentvp_mvvm.soal2.ui.viewmodel.QuizViewModel

@Composable
fun QuizApp(
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.page) {
        QuizPage.START -> {
            StartScreen(
                uiState = uiState,
                onQuestionCountChange = viewModel::onQuestionCountChange,
                onStartQuiz = viewModel::startQuiz
            )
        }
        QuizPage.QUIZ -> {
            QuizScreen(
                uiState = uiState,
                onAnswerSelected = viewModel::selectAnswer,
                onNextQuestion = viewModel::nextQuestion
            )
        }
        QuizPage.RESULT -> {
            ResultScreen(
                uiState = uiState,
                onRestartQuiz = viewModel::restartQuiz
            )
        }
    }
}