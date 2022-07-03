package pim.estiam.particours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import pim.estiam.particours.fragments.ConnexionFragment
import pim.estiam.particours.fragments.InscriptionFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        findViewById<Button>(R.id.redirection_button).text = resources.getString(string)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {}
}