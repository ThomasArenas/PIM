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
import java.util.*

class InscriptionFragment(private val context: LoginActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_inscription, container, false)

        val connexionButton = view.findViewById<Button>(R.id.submit_button)

        connexionButton.setOnClickListener {
            val nameInput = view.findViewById<EditText>(R.id.last_name_input).text.toString()
            val firstNameInput = view.findViewById<EditText>(R.id.first_name_input).text.toString()
            val mailInput = view.findViewById<EditText>(R.id.email_input).text.toString()
            val passwordInput = view.findViewById<EditText>(R.id.password_input).text.toString()
            val confirmPasswordInput = view.findViewById<EditText>(R.id.confirm_password_input).text.toString()

            val repo = UserRepository()
            repo.updateData {
                var mailExist = false
                for (user in userList) {
                    if (user.email == mailInput) {
                        mailExist = true
                        break
                    }
                }

                var samePassword = false
                if (passwordInput == confirmPasswordInput) {
                    samePassword = true
                }

                if (nameInput.isEmpty() || firstNameInput.isEmpty() || mailInput.isEmpty() || passwordInput.isEmpty() || confirmPasswordInput.isEmpty()) {
                    Toast.makeText(context, "Veuillez renseigner toutes les informations", Toast.LENGTH_SHORT).show()
                } else if (mailExist) {
                    Toast.makeText(context, "Cet email est déjà attribué à un utilisateur", Toast.LENGTH_SHORT).show()
                } else if (!samePassword) {
                    Toast.makeText(context, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = UserModel(
                        UUID.randomUUID().toString(),
                        nameInput,
                        firstNameInput,
                        mailInput,
                        BCrypt.withDefaults().hashToString(12, passwordInput.toCharArray())
                    )

                    repo.insertUser(newUser)
                    val intention = Intent(context, MainActivity::class.java)
                    intention.putExtra("sessionUser", newUser.id)
                    startActivity(intention)
                }
            }
        }

        return view
    }
}