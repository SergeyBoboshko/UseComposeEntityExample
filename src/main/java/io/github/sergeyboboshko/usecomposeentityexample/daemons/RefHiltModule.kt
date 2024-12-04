package io.github.sergeyboboshko.usecomposeentityexample.daemons

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZoneRepository
import io.github.sergeyboboshko.usecomposeentityexample.RefMeterZonesDao

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
}