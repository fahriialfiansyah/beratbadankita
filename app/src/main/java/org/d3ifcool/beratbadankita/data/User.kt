package org.d3ifcool.beratbadankita.data

import com.google.firebase.database.Exclude

data class User(
//    @get:Exclude
    var userId: String? = null,
    var beratId: String? = null,
    var saatIni: String? = null,
    var tujuan: String? = null,
    var date: Long? = null
) {

}
