package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL(
            """
    CREATE TABLE IF NOT EXISTS `ref_adress_details` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `parentId` INTEGER NOT NULL,
        `utilityId` INTEGER NOT NULL,
        `meterId` INTEGER NOT NULL,
        FOREIGN KEY (`parentId`) REFERENCES `ref_adresses` (`id`) ON DELETE CASCADE
    )
""".trimIndent()
        )
    }
}