package pim.estiam.particours.fragments

import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pim.estiam.particours.MainActivity
import pim.estiam.particours.R
import pim.estiam.particours.adapter.CanalItemDecoration
import pim.estiam.particours.adapter.DiscussionAdapter
import pim.estiam.particours.models.CanalModel
import pim.estiam.particours.models.InteretModel
import pim.estiam.particours.models.MessageModel
import pim.estiam.particours.models.UserModel
import pim.estiam.particours.repositories.CanalRepository
import pim.estiam.particours.repositories.InteretRepository
import pim.estiam.particours.repositories.MessageRepository
import pim.estiam.particours.repositories.MessageRepository.Singleton.messageList
import pim.estiam.particours.repositories.UserRepository
import java.util.*

class DiscussionFragment(
    private val context: MainActivity,
    private val canal: CanalModel,
    private val intent: Intent
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_discussion, container, false)

        val repo = CanalRepository()

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = DiscussionAdapter(context, messageList.filter { it.canalId == canal.id }, R.layout.item_message, intent)
        verticalRecyclerView.scrollToPosition(messageList.filter { it.canalId == canal.id }.size - 1)
        verticalRecyclerView.addItemDecoration(CanalItemDecoration())

        val canalImage = view.findViewById<ImageView>(R.id.canal_item_image)

        if("attente" in canal.state.lowercase()) {
            canalImage.setImageResource(R.mipmap.ic_attente)
        } else if("proposé" in canal.state.lowercase()) {
            canalImage.setImageResource(R.mipmap.ic_propose)
        } else if("prévu" in canal.state.lowercase()) {
            canalImage.setImageResource(R.mipmap.ic_valide)
        } else if("cloturé" in canal.state.lowercase()) {
            canalImage.setImageResource(R.mipmap.ic_cloture)
        }

        canalImage.setOnClickListener {
            repo.updateData {
                context.loadFragment(CanalFragment(context, intent))
            }
        }

        view.findViewById<TextView>(R.id.canal_item_name).text = canal.name
        view.findViewById<TextView>(R.id.canal_item_state).text = canal.state

        val canalIcon = view.findViewById<ImageView>(R.id.canal_item_icon)

        val interetRepo = InteretRepository()
        val userRepo = UserRepository()
        val user = userRepo.getUserById(intent.getStringExtra("sessionUser")!!)

        interetRepo.updateData {
            for (interet in InteretRepository.Singleton.interetList) {
                if (interet.userId == user!!.id && interet.canalId == canal.id) {
                    canalIcon.setImageResource(R.drawable.ic_liked)
                }
            }
        }

        canalIcon.setOnClickListener {
            var liked = false

            for (interet in InteretRepository.Singleton.interetList) {
                if (interet.userId == user!!.id && interet.canalId == canal.id) {
                    interetRepo.removeInteret(interet)
                    liked = true
                    break
                }
            }

            if (!liked) {
                val newInteret = InteretModel(
                    UUID.randomUUID().toString(),
                    canal.id,
                    user!!.id
                )

                interetRepo.insertInteret(newInteret)
            }

            repo.updateData {
                context.loadFragment(DiscussionFragment(context, canal, intent))
            }
        }

        val messageInput = view.findViewById<EditText>(R.id.message_input)
        val sendIcon = view.findViewById<ImageView>(R.id.send_icon)
        sendIcon.setOnClickListener {
            if (!messageInput.text.isEmpty()) {
                val userRepo = UserRepository()
                val user = userRepo.getUserById(intent.getStringExtra("sessionUser")!!)

                val messageRepo = MessageRepository()

                val newMessage = MessageModel(
                    UUID.randomUUID().toString(),
                    user!!.id,
                    canal.id,
                    System.currentTimeMillis(),
                    "Message",
                    messageInput.text.toString()
                )

                if (newMessage != null) {
                    messageRepo.insertMessage(newMessage)
                }

                messageRepo.updateData {
                    context.loadFragment(DiscussionFragment(context, canal, intent))
                }
            }
        }

        return view
    }
}