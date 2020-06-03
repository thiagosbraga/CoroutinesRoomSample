package com.devtides.coroutinesroom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query(value = "SELECT * FROM user WHERE username= :username")
    suspend fun getUser(username:String): User

    @Query(value = "DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Long)

}