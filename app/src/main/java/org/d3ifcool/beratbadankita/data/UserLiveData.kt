package org.d3ifcool.beratbadankita.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue


class UserLiveData(private val db: DatabaseReference) : LiveData<User>() {

    private val data = User()

    init {
        value = data
    }

    private val listener = object : ChildEventListener {
        override fun onCancelled(error: DatabaseError) { }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val user = snapshot.getValue<User>() ?: return
            user.userId = snapshot.key ?: return

//            val pos = data.indexOfFirst { it.userId == user.userId }
//            data[pos] = user
            value = user
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val user = snapshot.getValue<User>() ?: return
            user.userId = snapshot.key ?: return

//            data.add(user)
            value = user
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val userId = snapshot.key ?: return

//            val pos = data.indexOfFirst { it.userId == userId }
//            data.removeAt(pos)
//            value = data.toList()
        }
    }

    override fun onActive() {
        db.addChildEventListener(listener)
    }

    override fun onInactive() {
        db.removeEventListener(listener)
//        data.clear()
    }
}