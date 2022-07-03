package pim.estiam.particours.repositories

import android.util.Log
import com.google.firebase.database.*
import pim.estiam.particours.models.UserModel
import pim.estiam.particours.repositories.UserRepository.Singleton.databaseRef
import pim.estiam.particours.repositories.UserRepository.Singleton.userList


class UserRepository {

    object Singleton {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Utilisateur")
        val userList = arrayListOf<UserModel>()
    }

    fun updateData(callback: () -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for (ds in snapshot.children) {
                    val user = ds.getValue(UserModel::class.java)

                    if(user != null) {
                        userList.add(user)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getUserById(id: String): UserModel? {
        for (user in userList) {
            if (user.id == id) {
                return user
            }
        }
        return null
    }

    fun updateUser(user: UserModel) = databaseRef.child(user.id).setValue(user)

    fun insertUser(user: UserModel) = databaseRef.child(user.id).setValue(user)

}