package io.github.sergeyboboshko.usecomposeentityexample.daemons

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesDao
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesEntity
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceDao
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsinvoiceEntity

@Database(
    entities = [RefMeterZonesEntity::class,DocPaymentsinvoiceEntity::class]
    , version = 3)
abstract class AppDatabase : RoomDatabase(){
    abstract fun refMeterZonesDao(): RefMeterZonesDao
    abstract fun DocPaymentsInvoiceDao(): DocPaymentsInvoiceDao
}