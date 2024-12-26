package io.github.sergeyboboshko.usecomposeentityexample.daemons

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceDao
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsExpenseRepository
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsRepository
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsDao
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsRepository


@Module
@InstallIn(SingletonComponent::class)
object DetailsModule {
    //--------------------  RefMeterZonesDao  ------------------------
    @Provides
    fun provideRefMetersDetailsEntityDao(database: AppDatabase): RefMetersDetailsDao {
        return database.refMetersDetailsDao()
    }

    @Provides
    fun provideRefMetersDetailsEntityRepository(dao: RefMetersDetailsDao): RefMetersDetailsRepository {
        return RefMetersDetailsRepository(dao)
    }

    //--------------------  RefMeterZonesDao  ------------------------
    @Provides
    fun provideRefAdressDetailsEntityDao(database: AppDatabase): RefAddressDetailsDao {
        return database.refAddressDetailsDao()
    }

    @Provides
    fun provideAdressDetailsEntityRepository(dao: RefAddressDetailsDao): RefAddressDetailsRepository {
        return RefAddressDetailsRepository(dao)
    }
}