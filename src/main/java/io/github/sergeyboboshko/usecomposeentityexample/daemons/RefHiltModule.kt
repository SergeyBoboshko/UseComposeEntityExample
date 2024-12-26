package io.github.sergeyboboshko.usecomposeentityexample.daemons

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesRepository
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZoneRepository
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZonesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersRepository
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesRepository

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

    //--------------------  RefAddresses  ------------------------
    @Provides
    fun provideRefAddressesDao(database: AppDatabase): RefAddressesDao {
        return database.refAddressesDao()
    }

    @Provides
    fun provideRefAddressesRepository(dao: RefAddressesDao): RefAddressesRepository {
        return RefAddressesRepository(dao)
    }
    //--------------------  RefAddresses  ------------------------
    @Provides
    fun provideRefUtilitiesDao(database: AppDatabase): RefUtilitiesDao {
        return database.refUtilitiesDao()
    }

    @Provides
    fun provideRefUtilitiesRepository(dao: RefUtilitiesDao): RefUtilitiesRepository {
        return RefUtilitiesRepository(dao)
    }
}