import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import viihcerq.com.github.Alunos_RM552509_RM552500.R
import viihcerq.com.github.Alunos_RM552509_RM552500.model.Event

class EventAdapter(
    private var eventList: MutableList<Event>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.bind(currentEvent, position)
    }

    override fun getItemCount() = eventList.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewLocation: TextView = itemView.findViewById(R.id.textViewLocation)
        private val textViewEventType: TextView = itemView.findViewById(R.id.textViewEventType)
        private val textViewImpactLevel: TextView = itemView.findViewById(R.id.textViewImpactLevel)
        private val textViewEventDate: TextView = itemView.findViewById(R.id.textViewEventDate)
        private val textViewPeopleCount: TextView = itemView.findViewById(R.id.textViewPeopleCount)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        fun bind(event: Event, position: Int) {
            textViewLocation.text = "Local: ${event.location}"
            textViewEventType.text = "Evento: ${event.eventType}"
            textViewImpactLevel.text = "Impacto: ${event.impactLevel}"
            textViewEventDate.text = "Data: ${event.eventDate}"
            textViewPeopleCount.text = "Pessoas afetadas: ${event.affectedPeople}"

            buttonDelete.setOnClickListener {
                onDeleteClick(position)
            }
        }
    }
    fun updateList(newList: List<Event>) {
        eventList = newList.toMutableList()
        notifyDataSetChanged()
    }
}
