package pim.estiam.particours

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import pim.estiam.particours.fragments.CanalFragment
import pim.estiam.particours.repositories.CanalRepository
import pim.estiam.particours.repositories.UserRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.mipmap.ic_launcher)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        val repo = CanalRepository()
        repo.updateData {
            val intent = getIntent()

            if (intent.hasExtra("sessionUser")) {
                loadFragment(CanalFragment(this, intent))
            }
        }

    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {}
}