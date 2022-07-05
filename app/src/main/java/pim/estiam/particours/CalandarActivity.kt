package pim.estiam.particours

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import pim.estiam.particours.databinding.ActivityCalandarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalandarActivity(
    private var monthYearText: TextView,
    private var calandarRecyclerView: RecyclerView,
    private var selectedDate: LocalDate
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }
    fun initWidgets()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));

    }

    private fun monthYearFromDate(LocalDate: date) : String {
        val  formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    fun setMonthView()
    {
        calandarRecyclerView = findViewById(R.id.calandarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

    }

    fun previousMonthAction(view: View) {}
    fun nextMonthAction(view: View) {}
}