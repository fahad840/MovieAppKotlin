package company.com.movieappkotlin.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID=0
@Entity
data class User(
    var name: String?=null,
    var username:String?=null,
    var id :Int?=null,
    var hash:String?=null

) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int= CURRENT_USER_ID
}