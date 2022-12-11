package org.d3ifcool.beratbadankita.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.beratbadankita.data.User
import org.d3ifcool.beratbadankita.data.UserDao
import org.d3ifcool.beratbadankita.data.UserLiveData

class UserDb private constructor() {

    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    val dao = object : UserDao {
        override fun insertData(user: User) {
            database.push().setValue(user)
        }

        override fun getUserData(userId: String): LiveData<User> {
            return UserLiveData(database)
        }
    }


    companion object {
        private const val PATH = "User"

        @Volatile
        private var INSTANCE: UserDb? = null

        fun getInstance(requireContext: Context): UserDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = UserDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}