package company.com.movieappkotlin.network.models.UserDetailResponse

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID=0
@Entity
data class UserDetailResponse(val avatar: Avatar?, val id: Int?, val iso_639_1: String?, val iso_3166_1: String?, val name: String?, val include_adult: Boolean?, val username: String?){
    @PrimaryKey(autoGenerate = false)
    var uid: Int= CURRENT_USER_ID
}
