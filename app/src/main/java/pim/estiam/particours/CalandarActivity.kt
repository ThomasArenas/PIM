package pim.estiam.particours

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class CalandarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val setEvent = findViewById<AppCompatButton>(R.id.set_event)
        setEvent.setOnClickListener{
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, "My Event")
                .putExtra(EVENT_LOCATION, "Here")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis() )
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60*1000))

            startActivity(intent)
        }

    }


}