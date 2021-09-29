package com.rjhwork.mycompany.opggcloneapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rjhwork.mycompany.opggcloneapp.data.db.converter.FavoriteTypeConverter
import com.rjhwork.mycompany.opggcloneapp.data.db.dao.FavoriteDao
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(FavoriteTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val DATABASE_NAME = "favorite.db"

        fun build(context: Context): AppDatabase =
             Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}