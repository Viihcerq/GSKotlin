package viihcerq.com.github.Alunos_RM552509_RM552500

import EventAdapter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import viihcerq.com.github.Alunos_RM552509_RM552500.model.Event

class MainActivity : AppCompatActivity() {

    private lateinit var eventList: MutableList<Event>
    private lateinit var eventAdapter: EventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextLocation: EditText
    private lateinit var editTextEventType: EditText
    private lateinit var editTextImpactLevel: EditText
    private lateinit var editTextEventDate: EditText
    private lateinit var editTextPeopleCount: EditText
    private lateinit var spinnerEventType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventList = mutableListOf()
        recyclerView = findViewById(R.id.recyclerViewEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)


        eventAdapter = EventAdapter(eventList) { position ->
            eventList.removeAt(position)
            eventAdapter.notifyItemRemoved(position)
        }
        recyclerView.adapter = eventAdapter


        editTextLocation = findViewById(R.id.editTextLocation)
        editTextEventType = findViewById(R.id.editTextEventType)
        editTextImpactLevel = findViewById(R.id.editTextImpactLevel)
        editTextEventDate = findViewById(R.id.editTextEventDate)
        editTextPeopleCount = findViewById(R.id.editTextPeopleCount)


        spinnerEventType = findViewById(R.id.spinnerEventType)
        val eventTypes = arrayOf("Todos", "Chuva intensa", "Seca", "Onda de calor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEventType.adapter = adapter


        spinnerEventType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedEventType = eventTypes[position]
                filterEventsByType(selectedEventType)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun filterEventsByType(eventType: String) {
        val filteredList = if (eventType == "Todos") {
            eventList
        } else {
            eventList.filter { it.eventType == eventType }
        }
        eventAdapter.updateList(filteredList)
    }

    fun onIncludeEventClick(view: View) {
        val location = editTextLocation.text.toString().trim()
        val eventType = editTextEventType.text.toString().trim()
        val impactLevel = editTextImpactLevel.text.toString().trim()
        val eventDate = editTextEventDate.text.toString().trim()
        val affectedPeople = editTextPeopleCount.text.toString().toIntOrNull()

        if (location.isBlank() || eventType.isBlank() || impactLevel.isBlank() || eventDate.isBlank()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            return
        }

        if (affectedPeople == null || affectedPeople <= 0) {
            Toast.makeText(this, "Número de pessoas afetadas deve ser maior que zero", Toast.LENGTH_SHORT).show()
            return
        }

        val newEvent = Event(location, eventType, impactLevel, eventDate, affectedPeople)
        eventList.add(newEvent)
        eventAdapter.notifyItemInserted(eventList.size - 1)

        editTextLocation.text.clear()
        editTextEventType.text.clear()
        editTextImpactLevel.text.clear()
        editTextEventDate.text.clear()
        editTextPeopleCount.text.clear()
    }

    fun onSortByDateClick(view: View) {
        eventList.sortBy { it.eventDate }
        eventAdapter.notifyDataSetChanged()
    }
}
