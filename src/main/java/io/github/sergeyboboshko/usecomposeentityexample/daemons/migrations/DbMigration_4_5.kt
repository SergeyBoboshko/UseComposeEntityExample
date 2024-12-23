package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `ref_meters_details` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `parentId` INTEGER NOT NULL,
        `zonesId` INTEGER NOT NULL,
        FOREIGN KEY (`parentId`) REFERENCES `ref_meters` (`id`) ON DELETE CASCADE
    )
""".trimIndent()
        )
    }
}