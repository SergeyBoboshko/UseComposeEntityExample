package io.github.sergeyboboshko.usecomposeentityexample.daemons

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_2_3
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_3_4
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_4_5
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_5_6
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_6_7
import io.github.sergeyboboshko.usecomposeentityexample.daemons.migrations.MIGRATION_7_8
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
//.fallbackToDestructiveMigration() - убрали бо пробуємо створювати міграції без втрати даних
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "home-pay-manager"
        )
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .addMigrations(MIGRATION_5_6)
            .addMigrations(MIGRATION_6_7)
            .addMigrations(MIGRATION_7_8)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.d("MainViewModel", "Database has been created")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Log.d("MainViewModel", "Database has been opened")
                    val currentVersion = db.version
                    Log.d("DB_VERSION", "Database version = $currentVersion")
                }
            })
            .build()
    }
}

//http://www.homeclub.top/?p=1063
