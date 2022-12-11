package org.d3ifcool.beratbadankita.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.d3ifcool.beratbadankita.data.UserDao
import org.d3ifcool.beratbadankita.data.User

class OnBoardingViewModel(private val db : UserDao) : ViewModel() {

//    val data = db.getUserData()

    fun insertData(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(user)
            }
        }
    }

    fun getUserData(userId: String) {
//        val newUserIds = userIds.toList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.getUserData(userId)
            }
        }
    }
}