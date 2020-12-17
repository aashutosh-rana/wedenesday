package com.bcebhagalpur.wednesdayplaybook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity(tableName = "words" )
data class Word (

    @PrimaryKey @ColumnInfo(name = "collectionId") var collectionId:Int,
    @ColumnInfo(name = "artworkUrl100") var artworkUrl100: String,
    @ColumnInfo(name = "trackName") var trackName: String,
    @ColumnInfo(name = "artistName") var artistName: String,
    @ColumnInfo(name = "primaryGenreName") var primaryGenreName: String

)
//{
//    @PrimaryKey(autoGenerate = true)  var id:Int=0
//}
