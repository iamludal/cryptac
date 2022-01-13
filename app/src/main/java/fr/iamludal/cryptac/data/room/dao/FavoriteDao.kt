package fr.iamludal.cryptac.data.room.dao

import androidx.room.*
import fr.iamludal.cryptac.data.entity.Favorite
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Single<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(favorite: Favorite): Completable

    @Delete
    fun remove(favorite: Favorite): Completable
}
