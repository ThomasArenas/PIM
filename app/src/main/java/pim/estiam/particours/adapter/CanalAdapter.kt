package pim.estiam.particours.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pim.estiam.particours.*
import pim.estiam.particours.fragments.CanalFragment
import pim.estiam.particours.fragments.DiscussionFragment
import pim.estiam.particours.models.CanalModel
import pim.estiam.particours.models.InteretModel
import pim.estiam.particours.models.UserModel
import pim.estiam.particours.repositories.CanalRepository
import pim.estiam.particours.repositories.InteretRepository
import pim.estiam.particours.repositories.InteretRepository.Singleton.interetList
import pim.estiam.particours.repositories.MessageRepository
import pim.estiam.particours.repositories.UserRepository
import java.util.*

class CanalAdapter(
    val context: MainActivity,
    private val canalList: List<CanalModel>,
    private val layoutId: Int,
    private val intent: Intent
) : RecyclerView.Adapter<CanalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val canalImage = view.findViewById<ImageView>(R.id.canal_item_image)
        val canalName = view.findViewById<TextView>(R.id.canal_item_name)
        val canalState = view.findViewById<TextView>(R.id.canal_item_state)
        val canalIcon = view.findViewById<ImageView>(R.id.canal_item_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCanal = canalList[position]

        val repo = CanalRepository()

        holder.canalName.text = currentCanal.name
        holder.canalState.text = currentCanal.state

        if("attente" in currentCanal.state.lowercase()) {
            holder.canalImage.setImageResource(R.mipmap.ic_attente)
        } else if("proposé" in currentCanal.state.lowercase()) {
            holder.canalImage.setImageResource(R.mipmap.ic_propose)
        } else if("prévu" in currentCanal.state.lowercase()) {
            holder.canalImage.setImageResource(R.mipmap.ic_valide)
        } else if("cloturé" in currentCanal.state.lowercase()) {
            holder.canalImage.setImageResource(R.mipmap.ic_cloture)
        }

        val interetRepo = InteretRepository()
        val userRepo = UserRepository()
        val user = userRepo.getUserById(intent.getStringExtra("sessionUser")!!)

        interetRepo.updateData {
            for (interet in interetList) {
                if (interet.userId == user!!.id && interet.canalId == currentCanal.id) {
                    holder.canalIcon.setImageResource(R.drawable.ic_liked)
                }
            }
        }

        holder.canalIcon.setOnClickListener {
            var liked = false

            for (interet in interetList) {
                if (interet.userId == user!!.id && interet.canalId == currentCanal.id) {
                    interetRepo.removeInteret(interet)
                    liked = true
                    break
                }
            }

            if (!liked) {
                val newInteret = InteretModel(
                    UUID.randomUUID().toString(),
                    currentCanal.id,
                    user!!.id
                )

                interetRepo.insertInteret(newInteret)
            }

            repo.updateData {
                context.loadFragment(CanalFragment(context, intent))
            }
        }

        holder.itemView.setOnClickListener {
            val repo = MessageRepository()
            repo.updateData {
                context.loadFragment(DiscussionFragment(context, currentCanal, intent))
            }
        }
    }

    override fun getItemCount(): Int = canalList.size

}