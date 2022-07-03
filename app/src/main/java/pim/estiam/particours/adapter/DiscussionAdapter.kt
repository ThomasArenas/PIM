package pim.estiam.particours.adapter

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pim.estiam.particours.MainActivity
import pim.estiam.particours.R
import pim.estiam.particours.models.MessageModel
import pim.estiam.particours.repositories.UserRepository
import java.text.SimpleDateFormat
import java.util.*


class DiscussionAdapter(
    val context: MainActivity,
    private val messageList: List<MessageModel>,
    private val layoutId: Int,
    private val intent: Intent
) : RecyclerView.Adapter<DiscussionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageDate = view.findViewById<TextView>(R.id.message_date)
        val message = view.findViewById<TextView>(R.id.message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = simpleDateFormat.format(Date(currentMessage.date))

        holder.messageDate.text = date.toString()

        val userRepo = UserRepository()
        val messageUser = userRepo.getUserById(currentMessage.userId)

        val messageContent = "<b style='font-size:18dp;font-weight:900'>" + messageUser!!.first_name + " " + messageUser!!.last_name + " : </b>" + currentMessage.message
        holder.message.text = Html.fromHtml(messageContent)
    }

    override fun getItemCount(): Int = messageList.size

}