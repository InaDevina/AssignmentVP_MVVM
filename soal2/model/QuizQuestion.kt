package com.example.assignmentvp_mvvm.soal2.model

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)