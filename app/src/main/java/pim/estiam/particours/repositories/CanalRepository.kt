package pim.estiam.particours.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pim.estiam.particours.repositories.CanalRepository.Singleton.canalList
import pim.estiam.particours.repositories.CanalRepository.Singleton.databaseRef
import pim.estiam.particours.models.CanalModel


class CanalRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Canal")
        val canalList = arrayListOf<CanalModel>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                canalList.clear()

                for (ds in snapshot.children) {
                    val canal = ds.getValue(CanalModel::class.java)

                    if(canal != null) {
                        canalList.add(canal)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateCanal(canal: CanalModel) = databaseRef.child(canal.id).setValue(canal)

    fun insertCanal(canal: CanalModel) = databaseRef.child(canal.id).setValue(canal)
}