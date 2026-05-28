import android.text.Html
import com.example.quize.data.remote.dto.QuestionDto
import com.example.quize.domain.model.Question

fun QuestionDto.toDomain(): Question {
    val allAnswers = (incorrectAnswers + correctAnswer)
        .map { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString() }
        .shuffled()
    return Question(
        text = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString(),
        answers = allAnswers,
        correctAnswer = Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
    )
}