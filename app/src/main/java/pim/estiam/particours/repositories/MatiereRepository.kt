package pim.estiam.particours.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pim.estiam.particours.repositories.MatiereRepository.Singleton.arrayList
import pim.estiam.particours.repositories.MatiereRepository.Singleton.databaseRef
import pim.estiam.particours.models.MatiereModel


class MatiereRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Matiere")
        val arrayList = arrayListOf<String>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()

                for (ds in snapshot.children) {
                    val matiere = ds.getValue(MatiereModel::class.java)

                    if(matiere != null) {
                        arrayList.add(matiere.name)
                    }
                }

                arrayList.sort()
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateMatiere(matiere: MatiereModel) = databaseRef.child(matiere.id).setValue(matiere)
}