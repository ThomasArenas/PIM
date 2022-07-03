package pim.estiam.particours.models

import android.text.Editable
import java.util.*

class MessageModel(
    val id: String = "Message0",
    var userId: String = "ID de l'utilisateur",
    var canalId: String = "ID du canal",
    var date: Long = System.currentTimeMillis(),
    var type: String = "Message",
    var message: String = "Message de l'utilisateur"
)