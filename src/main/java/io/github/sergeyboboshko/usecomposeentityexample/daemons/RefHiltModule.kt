package io.github.sergeyboboshko.usecomposeentityexample.daemons

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZoneRepository
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZonesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    //--------------------  RefMeterZonesDao  ------------------------
    @Provides
    fun provideRefMeterZonesDao(database: AppDatabase): RefMeterZonesDao {
        return database.refMeterZonesDao()
    }

    @Provides
    fun provideRefMeterZonesRepository(dao: RefMeterZonesDao): RefMeterZoneRepository {
        return RefMeterZoneRepository(dao)
    }
    //--------------------  RefMeters  ------------------------
    @Provides
    fun provideRefMetersDao(database: AppDatabase): RefMetersDao {
        return database.refMetersDao()
    }

    @Provides
    fun provideRefMetersRepository(dao: RefMetersDao): RefMetersRepository {
        return RefMetersRepository(dao)
    }
}