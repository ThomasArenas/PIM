package pim.estiam.particours.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import at.favre.lib.crypto.bcrypt.BCrypt
import pim.estiam.particours.LoginActivity
import pim.estiam.particours.MainActivity
import pim.estiam.particours.R
import pim.estiam.particours.models.UserModel
import pim.estiam.particours.repositories.UserRepository
import pim.estiam.particours.repositories.UserRepository.Singleton.userList


class ConnexionFragment(private val context: LoginActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_connexion, container, false)

        val connexionButton = view.findViewById<Button>(R.id.submit_button)

        connexionButton.setOnClickListener {
            val mailInput = view.findViewById<EditText>(R.id.email_input).text.toString()
            val passwordInput = view.findViewById<EditText>(R.id.password_input).text.toString()

            val repo = UserRepository()
            var userConnected:UserModel? = null

            repo.updateData {
                for (user in userList) {
                    if (user.email.lowercase() == mailInput.lowercase() && BCrypt.verifyer().verify(passwordInput.toCharArray(), user.password).verified) {
                        userConnected = user
                        val intention = Intent(context, MainActivity::class.java)
                        intention.putExtra("sessionUser", userConnected!!.id)
                        startActivity(intention)
                        break
                    }
                }

                if (mailInput.isEmpty() || passwordInput.isEmpty()) {
                    Toast.makeText(context, "Veuillez renseigner toutes les informations", Toast.LENGTH_SHORT).show()
                } else if (userConnected == null) {
                    Toast.makeText(context, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
}