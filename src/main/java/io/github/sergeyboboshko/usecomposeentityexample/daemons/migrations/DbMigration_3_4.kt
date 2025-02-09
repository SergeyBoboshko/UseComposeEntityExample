package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.sergeyboboshko.composeentity_ksp.base.DatabaseMigration

@DatabaseMigration(version = 4)
object MIGRATION_3_4 : Migration(3, 4) {
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