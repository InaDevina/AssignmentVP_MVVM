package com.example.assignmentvp_mvvm.soal2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.assignmentvp_mvvm.soal2.data.QuestionRepository
import com.example.assignmentvp_mvvm.soal2.model.AnswerResult
import com.example.assignmentvp_mvvm.soal2.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class QuizPage {
    START,
    QUIZ,
    RESULT
}

data class QuizUiState(
    val page: QuizPage = QuizPage.START,
    val questionCount: String = "",
    val inputError: Boolean = false,
    val maxQuestions: Int = 5,
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String = "",
    val answers: List<AnswerResult> = emptyList()
) {
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentQuestionIndex)
    val score: Int
        get() = answers.count { it.isCorrect }
}

class QuizViewModel : ViewModel() {
    private val repository = QuestionRepository
    private val _uiState = MutableStateFlow(
        QuizUiState(maxQuestions = repository.getQuestionCount())
    )

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onQuestionCountChange(value: String) {
        val count = value.toIntOrNull()
        _uiState.update {
            it.copy(
                questionCount = value,
                inputError = count != null && count > repository.getQuestionCount()
            )
        }
    }

    fun startQuiz() {
        val count = _uiState.value.questionCount.toIntOrNull()

        if (count == null || count < 1 || count > repository.getQuestionCount()) {
            _uiState.update {
                it.copy(inputError = true)
            }
            return
        }

        val randomQuestions = repository.getRandomQuestions(count)

        _uiState.update {
            it.copy(
                page = QuizPage.QUIZ,
                questions = randomQuestions,
                currentQuestionIndex = 0,
                selectedAnswer = "",
                answers = emptyList(),
                inputError = false
            )
        }
    }

    fun selectAnswer(answer: String) {
        _uiState.update {
            it.copy(selectedAnswer = answer)
        }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return

        if (state.selectedAnswer.isEmpty()) {
            return
        }

        val result = AnswerResult(
            question = question,
            selectedAnswer = state.selectedAnswer,
            isCorrect = state.selectedAnswer == question.correctAnswer
        )

        val updatedAnswers = state.answers + result

        if (state.currentQuestionIndex == state.questions.lastIndex) {
            _uiState.update {
                it.copy(
                    answers = updatedAnswers,
                    page = QuizPage.RESULT
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    answers = updatedAnswers,
                    currentQuestionIndex = state.currentQuestionIndex + 1,
                    selectedAnswer = ""
                )
            }
        }
    }

    fun restartQuiz() {
        _uiState.update {
            QuizUiState(
                maxQuestions = repository.getQuestionCount(),
                questionCount = it.questionCount
            )
        }
    }
}