package pim.estiam.particours

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import pim.estiam.particours.repositories.MatiereRepository.Singleton.arrayList
import pim.estiam.particours.adapter.CanalAdapter
import pim.estiam.particours.fragments.CanalFragment
import pim.estiam.particours.models.CanalModel
import pim.estiam.particours.models.UserModel
import pim.estiam.particours.repositories.CanalRepository
import pim.estiam.particours.repositories.MatiereRepository
import java.util.*


class CanalPopup(
    private val adapter: CanalAdapter,
    private val intent: Intent
) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_create_canal)
        setupCloseButton()
        setupAddButton()

        val repo = MatiereRepository()
        repo.updateData {
            val arrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(adapter.context, android.R.layout.simple_spinner_item, arrayList)
            findViewById<Spinner>(R.id.popup_name_spinner).setAdapter(arrayAdapter);
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_close_icon).setOnClickListener {
            dismiss()
        }
    }

    private fun setupAddButton() {
        findViewById<Button>(R.id.popup_submit_button).setOnClickListener {
            val repo = CanalRepository()

            val newCanal = CanalModel(
                UUID.randomUUID().toString(),
                findViewById<Spinner>(R.id.popup_name_spinner).selectedItem.toString(),
                "En attente d'un tuteur",
                "https://media.istockphoto.com/photos/colored-powder-explosion-on-black-background-picture-id1140180560?k=20&m=1140180560&s=612x612&w=0&h=X_400OQDFQGqccORnKt2PHYvTZ3dBLeEnCH_hRiUQrY=",
            )

            repo.insertCanal(newCanal)
            repo.updateData {
                adapter.context.loadFragment(CanalFragment(adapter.context, intent))
            }
            dismiss()
        }
    }
}