package io.github.sergeyboboshko.usecomposeentityexample.daemons

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesDao
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesEntity

@Database(
    entities = [RefMeterZonesEntity::class]
    , version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun refMeterZonesDao(): RefMeterZonesDao
}