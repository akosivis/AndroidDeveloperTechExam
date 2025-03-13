package com.akosivis.androiddevelopertechnicalchallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akosivis.androiddevelopertechnicalchallenge.database.converters.ArrayListConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.converters.ArrayListReviewConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.converters.DimensionsConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.converters.MetaConverter
import com.akosivis.androiddevelopertechnicalchallenge.database.converters.ReviewConverter

@Database(entities = [Product::class], version = 2, exportSchema = false)
@TypeConverters(ArrayListConverter::class, ArrayListReviewConverter::class, DimensionsConverter::class, MetaConverter::class, ReviewConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }
        }
    }
}