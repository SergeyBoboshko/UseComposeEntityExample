package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `ref_meters` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `date` INTEGER NOT NULL,
                `name` TEXT NOT NULL,                
                `isMarkedForDeletion` INTEGER NOT NULL
            )
        """.trimIndent())
    }
}