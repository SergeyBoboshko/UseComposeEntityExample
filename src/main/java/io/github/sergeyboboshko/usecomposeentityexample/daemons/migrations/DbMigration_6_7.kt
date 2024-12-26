package io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `ref_utilities` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `date` INTEGER NOT NULL,
                `name` TEXT NOT NULL,                
                `isMarkedForDeletion` INTEGER NOT NULL,
                `address` TEXT NOT NULL,
                `serviceAccount` TEXT NOT NULL,
                `describe` TEXT NOT NULL
            )
        """.trimIndent())
    }
}