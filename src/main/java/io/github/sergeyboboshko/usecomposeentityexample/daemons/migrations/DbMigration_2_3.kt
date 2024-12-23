package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для сутності DocPaymentsinvoiceEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `doc_payments_invoice` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `date` INTEGER NOT NULL,
                `number` INTEGER NOT NULL,
                `isPosted` INTEGER NOT NULL,
                `isMarkedForDeletion` INTEGER NOT NULL,
                describe TEXT
            )
        """.trimIndent())
    }
}