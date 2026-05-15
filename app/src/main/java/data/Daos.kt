package com.example.siridhanyahub.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.siridhanyahub.data.entity.CartItemEntity
import com.example.siridhanyahub.data.entity.FarmerEntity
import com.example.siridhanyahub.data.entity.HealthFactEntity
import com.example.siridhanyahub.data.entity.MilletEntity
import com.example.siridhanyahub.data.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

// ── DAO 1: Millet ────────────────────────────────────────────────
@Dao
interface MilletDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(millets: List<MilletEntity>)

    @Query("SELECT * FROM millet_table ORDER BY city ASC")
    fun getAll(): Flow<List<MilletEntity>>

    @Query(
        """SELECT * FROM millet_table WHERE
              city LIKE '%' || :q || '%' OR
              milletName LIKE '%' || :q || '%' OR
              milletType LIKE '%' || :q || '%'"""
    )
    fun search(q: String): Flow<List<MilletEntity>>

    @Query("UPDATE millet_table SET price = :price, trend = :trend WHERE id = :id")
    suspend fun updatePrice(id: Int, price: String, trend: String)

    @Query("DELETE FROM millet_table")
    suspend fun deleteAll()
}

// ── DAO 2: Recipe ────────────────────────────────────────────────
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipe_table ORDER BY name ASC")
    fun getAll(): Flow<List<RecipeEntity>>

    @Query(
        """SELECT * FROM recipe_table WHERE
              name LIKE '%' || :q || '%' OR
              milletType LIKE '%' || :q || '%'"""
    )
    fun search(q: String): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE milletType = :type ORDER BY name ASC")
    fun filterByType(type: String): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE isSaved = 1")
    fun getSaved(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipe_table SET isSaved = :saved WHERE id = :id")
    suspend fun toggleSaved(id: Int, saved: Boolean)

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAll()
}

// ── DAO 3: HealthFact ────────────────────────────────────────────
@Dao
interface HealthFactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(facts: List<HealthFactEntity>)

    @Query("SELECT * FROM health_table ORDER BY title ASC")
    fun getAll(): Flow<List<HealthFactEntity>>

    @Query(
        """SELECT * FROM health_table WHERE
              title LIKE '%' || :q || '%' OR
              milletName LIKE '%' || :q || '%'"""
    )
    fun search(q: String): Flow<List<HealthFactEntity>>

    @Query("DELETE FROM health_table")
    suspend fun deleteAll()
}

// ── DAO 4: Farmer ────────────────────────────────────────────────
@Dao
interface FarmerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(farmers: List<FarmerEntity>)

    @Query("SELECT * FROM farmer_table ORDER BY name ASC")
    fun getAll(): Flow<List<FarmerEntity>>

    @Query(
        """SELECT * FROM farmer_table WHERE
              name LIKE '%' || :q || '%' OR
              location LIKE '%' || :q || '%' OR
              millets LIKE '%' || :q || '%' OR
              fpoName LIKE '%' || :q || '%'"""
    )
    fun search(q: String): Flow<List<FarmerEntity>>

    @Query("DELETE FROM farmer_table")
    suspend fun deleteAll()
}

// ── DAO 5: Cart ──────────────────────────────────────────────────
@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity)

    @Query("SELECT * FROM cart_table ORDER BY id ASC")
    fun getAll(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_table WHERE farmerName = :name LIMIT 1")
    suspend fun getByFarmer(name: String): CartItemEntity?

    @Query("UPDATE cart_table SET qty = :qty WHERE id = :id")
    suspend fun updateQty(id: Int, qty: Int)

    @Query("SELECT SUM(qty) FROM cart_table")
    fun totalQty(): Flow<Int?>

    @Delete
    suspend fun delete(item: CartItemEntity)

    @Query("DELETE FROM cart_table")
    suspend fun clearAll()
}