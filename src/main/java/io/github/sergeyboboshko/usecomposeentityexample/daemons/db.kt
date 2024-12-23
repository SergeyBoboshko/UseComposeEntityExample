package io.github.sergeyboboshko.usecomposeentityexample.daemons

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZonesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZonesEntity
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceDao
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsinvoiceEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersEntity

@Database(
    entities = [RefMeterZonesEntity::class,DocPaymentsinvoiceEntity::class,RefMetersEntity::class,RefMetersDetailsEntity::class
               ,RefAddressesEntity::class]
    , version = 6)
abstract class AppDatabase : RoomDatabase(){
    abstract fun refMeterZonesDao(): RefMeterZonesDao
    abstract fun DocPaymentsInvoiceDao(): DocPaymentsInvoiceDao
    abstract fun refMetersDao(): RefMetersDao
    abstract fun refMetersDetailsDao():RefMetersDetailsDao
    abstract fun refAddressesDao():RefAddressesDao
}