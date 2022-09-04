package warehouse.assistant.data.local

import androidx.room.*

@Dao
interface AuthorizedUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorizedUser(user: AuthorizedUserEntity)
    @Delete
    suspend fun deleteUser(user: AuthorizedUserEntity)

    @Transaction
    @Query("UPDATE AuthorizedUserEntity SET role=(:role) WHERE LOWER(email)==LOWER(:email)")
    suspend fun updateUserRole(email:String,role:String)

    @Transaction
    @Query("SELECT * FROM AUTHORIZEDUSERENTITY WHERE LOWER(email) == LOWER(:query)")
    suspend fun getUSerByEmail(query: String):AuthorizedUserEntity

    //ITEM
    @Transaction
    @Query("SELECT * FROM AuthorizedUserEntity WHERE LOWER(username) " +
            "LIKE '%'||LOWER(:query)||'%' OR LOWER(email) LIKE '%'||LOWER(:query)||'%'")
    suspend fun getUsersByUsernameOrEmail(query:String):List<AuthorizedUserEntity>

}