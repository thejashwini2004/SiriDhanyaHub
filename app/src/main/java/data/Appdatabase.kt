package com.example.siridhanyahub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.siridhanyahub.data.entity.CartItemEntity
import com.example.siridhanyahub.data.entity.FarmerEntity
import com.example.siridhanyahub.data.entity.HealthFactEntity
import com.example.siridhanyahub.data.entity.MilletEntity
import com.example.siridhanyahub.data.entity.RecipeEntity

@Database(
    entities = [
        MilletEntity::class,
        RecipeEntity::class,
        HealthFactEntity::class,
        FarmerEntity::class,
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SiriDhanyaDatabase : RoomDatabase() {

    abstract fun milletDao(): MilletDao
    abstract fun recipeDao(): RecipeDao
    abstract fun healthFactDao(): HealthFactDao
    abstract fun farmerDao(): FarmerDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: SiriDhanyaDatabase? = null

        fun getDatabase(context: Context): SiriDhanyaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SiriDhanyaDatabase::class.java,
                    "siridhanya_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}