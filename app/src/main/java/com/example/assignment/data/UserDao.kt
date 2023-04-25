package com.example.assignment.data

import androidx.constraintlayout.helper.widget.Flow
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.assignment.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): kotlinx.coroutines.flow.Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): kotlinx.coroutines.flow.Flow<List<User>>

}