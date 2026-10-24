package com.example.assignmentvp_mvvm.soal2.data

import com.example.assignmentvp_mvvm.soal2.model.QuizQuestion

object QuestionRepository {

    private val questions = listOf(
        QuizQuestion(
            id = 1,
            question = "Apa ibu kota Indonesia?",
            options = listOf("Surabaya", "Bandung", "Jakarta", "Medan"),
            correctAnswer = "Jakarta"
        ),

        QuizQuestion(
            id = 2,
            question = "Berapa hasil dari 8 + 5?",
            options = listOf("11", "12", "13", "14"),
            correctAnswer = "13"
        ),

        QuizQuestion(
            id = 3,
            question = "Siapakah tokoh yang membacakan teks Proklamasi Kemerdekaan Indonesia pada tanggal 17 Agustus 1945?",
            options = listOf("Pria Solo", "Ir. Soekarno", "Gibran", "Prabowo"),
            correctAnswer = "Ir. Soekarno"
        ),

        QuizQuestion(
            id = 4,
            question = "Gunung tertinggi di Indonesia yang puncaknya tertutup salju abadi adalah",
            options = listOf("Gunung Rinjani", "Puncak Jaya", "Semua jawaban salah", "Gunung Arjuna"),
            correctAnswer = "Puncak Jaya"
        ),

        QuizQuestion(
            id = 5,
            question = "Hasil dari 2 + 3 * 10 - 5",
            options = listOf("27", "26", "25", "24"),
            correctAnswer = "27"
        ),

        QuizQuestion(
            id = 6,
            question = "Apakah rakyat Indonesia merasa bahagia tinggal di Indonesia?",
            options = listOf("Sangat bahagia", "Sangat menderita", "Bahagia sedikit", "Saya akan lawan"),
            correctAnswer = "Sangat bahagia"
        ),

        QuizQuestion(
            id = 7,
            question = "Ada berapa member di Enhypen?",
            options = listOf("7", "6", "8", "9"),
            correctAnswer = "6"
        ),

        QuizQuestion(
            id = 8,
            question = "1 tahun ada berapa bulan?",
            options = listOf("1", "10", "12", "15"),
            correctAnswer = "12"
        ),

    )

    fun getAllQuestion(): List<QuizQuestion> {
        return questions
    }
    fun getRandomQuestions(count: Int): List<QuizQuestion> {
        return questions.shuffled().take(count)
    }
    fun getQuestionCount(): Int {
        return questions.size
    }

}