package pim.estiam.particours.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pim.estiam.particours.models.CanalModel
import pim.estiam.particours.models.InteretModel
import pim.estiam.particours.repositories.InteretRepository.Singleton.interetList
import pim.estiam.particours.repositories.InteretRepository.Singleton.databaseRef


class InteretRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Interet")
        val interetList = arrayListOf<InteretModel>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                interetList.clear()

                for (ds in snapshot.children) {
                    val interet = ds.getValue(InteretModel::class.java)

                    if(interet != null) {
                        interetList.add(interet)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun removeInteret(interet: InteretModel) = databaseRef.child(interet.id).removeValue()

    fun updateInteret(interet: InteretModel) = databaseRef.child(interet.id).setValue(interet)

    fun insertInteret(interet: InteretModel) = databaseRef.child(interet.id).setValue(interet)
}