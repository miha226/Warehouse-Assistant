package warehouse.assistant.data.local

import androidx.room.*

@Dao
interface AuthorizedUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorizedUser(user: AuthorizedUserEntity)

    @Delete
    suspend fun deleteUser(user: AuthorizedUserEntity)



    //ITEM
    @Transaction
    @Query("SELECT * FROM AuthorizedUserEntity WHERE LOWER(username) LIKE '%'||LOWER(:query)||'%' OR LOWER(email) LIKE '%'||LOWER(:query)||'%'")
    suspend fun getUsersByUsernameOrEmail(query:String):List<AuthorizedUserEntity>

}