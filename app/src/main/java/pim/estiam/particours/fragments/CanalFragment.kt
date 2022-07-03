package pim.estiam.particours.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pim.estiam.particours.CanalPopup
import pim.estiam.particours.repositories.CanalRepository.Singleton.canalList
import pim.estiam.particours.MainActivity
import pim.estiam.particours.R
import pim.estiam.particours.adapter.CanalAdapter
import pim.estiam.particours.adapter.CanalItemDecoration
import pim.estiam.particours.models.UserModel

class CanalFragment(
    private val context: MainActivity,
    private val intent: Intent
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_canal, container, false)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = CanalAdapter(context, canalList, R.layout.item_canal, intent)
        verticalRecyclerView.addItemDecoration(CanalItemDecoration())

        val addButton = view.findViewById<ImageButton>(R.id.add_button)
        addButton.setOnClickListener {
            CanalPopup(CanalAdapter(context, canalList, R.layout.item_canal, intent), intent).show()
        }

        return view
    }

}