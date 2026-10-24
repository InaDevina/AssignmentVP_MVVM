package com.example.assignmentvp_mvvm.soal2.model

data class AnswerResult(
    val question: QuizQuestion,
    val selectedAnswer: String,
    val isCorrect: Boolean
)