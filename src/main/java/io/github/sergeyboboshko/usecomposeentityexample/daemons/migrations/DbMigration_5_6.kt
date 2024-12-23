package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `ref_adresses` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `date` INTEGER NOT NULL,
                `name` TEXT NOT NULL,                
                `zipCode` TEXT NOT NULL,
                `city` TEXT NOT NULL,
                `address` TEXT NOT NULL,
                `houseNumber` INTEGER NOT NULL,
                `houseBlock` TEXT NOT NULL,
                `apartmentNumber` INTEGER NOT NULL,
                `isMarkedForDeletion` INTEGER NOT NULL
            )
        """.trimIndent())
        //ref_meters
        database.execSQL("""
                        ALTER TABLE `ref_meters`
            ADD COLUMN `addressId` INTEGER NOT NULL DEFAULT undefined
        """.trimIndent())
    }
}