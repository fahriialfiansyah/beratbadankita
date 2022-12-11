package org.d3ifcool.beratbadankita.data

import com.google.firebase.database.Exclude

data class History(
    var userId: String? = null,
    @get:Exclude
    var beratId: String? = null,
    var saatIni: String? = null,
    var catatan: String? = null,
    var date: Long? = null
)
