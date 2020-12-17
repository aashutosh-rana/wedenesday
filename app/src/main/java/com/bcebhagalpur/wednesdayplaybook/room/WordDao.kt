package com.bcebhagalpur.wednesdayplaybook.room

import androidx.room.*
import com.bcebhagalpur.wednesdayplaybook.model.Word
import retrofit2.http.DELETE

@Dao
interface WordDao {
    @Query("SELECT * FROM words")
    fun getAlphabetizedWords(): List<Word>

    @Insert
     fun insert(word: Word)

    @Delete
     fun delete(word: Word)

//    @Query("DELETE FROM words")
//    fun deleteAll():List<Word>
}