package company.com.movieappkotlin.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    val movieid:Int?,
    val movieName:String?,
    val movieImage:String?,
    val movierating:Float?
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int?=null

}
