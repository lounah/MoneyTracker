package ru.popov.bodya.data.database

import android.arch.persistence.room.*

@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<T>)

    @Update
    fun update(item: T)

    @Update
    fun updateAll(items: List<T>)

    @Delete
    fun delete(item: T)
}
