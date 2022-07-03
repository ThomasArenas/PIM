package pim.estiam.particours.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pim.estiam.particours.models.MessageModel
import pim.estiam.particours.repositories.MessageRepository.Singleton.databaseRef
import pim.estiam.particours.repositories.MessageRepository.Singleton.messageList

class MessageRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Message")
        val messageList = arrayListOf<MessageModel>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.orderByChild("date").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for (ds in snapshot.children) {
                    val message = ds.getValue(MessageModel::class.java)

                    if(message != null) {
                        messageList.add(message)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateMessage(message: MessageModel) = databaseRef.child(message.id).setValue(message)

    fun insertMessage(message: MessageModel) = databaseRef.child(message.id).setValue(message)
}