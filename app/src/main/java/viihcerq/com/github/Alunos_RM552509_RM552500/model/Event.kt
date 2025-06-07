package viihcerq.com.github.Alunos_RM552509_RM552500.model

data class Event(
    val location: String,
    val eventType: String,
    val impactLevel: String,
    val eventDate: String,
    val affectedPeople: Int
)