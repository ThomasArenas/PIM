package pim.estiam.particours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import pim.estiam.particours.fragments.ConnexionFragment
import pim.estiam.particours.fragments.InscriptionFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calandar_main)
        val setEvent = findViewById<AppCompatButton>(R.id.set_event)
        setEvent.setOnClickListener{
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "My Event")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Here")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis() )
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60*1000))

            startActivity(intent)
        }

    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val redirectionButton = findViewById<Button>(R.id.redirection_button)
        var buttonText = redirectionButton.text

        redirectionButton.setOnClickListener() {
            if (buttonText == resources.getString(R.string.connexion_page_inscription_button)) {
                loadFragment(InscriptionFragment(this), R.string.inscription_page_connexion_button)
            } else if (buttonText == resources.getString(R.string.inscription_page_connexion_button)) {
                loadFragment(ConnexionFragment(this), R.string.connexion_page_inscription_button)
            }
            buttonText = redirectionButton.text
        }

        loadFragment(ConnexionFragment(this), R.string.connexion_page_inscription_button)
    }*/

    private fun loadFragment(fragment: Fragment, string: Int) {
        findViewById<Button>(R.id.redirection_button).text = resources.getString(string)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {}
}