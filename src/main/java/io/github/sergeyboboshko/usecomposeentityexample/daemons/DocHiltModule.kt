package io.github.sergeyboboshko.usecomposeentityexample.daemons

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceDao
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsExpenseRepository


@Module
@InstallIn(SingletonComponent::class)
object DocRepositoryModule {
    //--------------------  RefMeterZonesDao  ------------------------
    @Provides
    fun provideDocPaymentsInvoiceDao(database: AppDatabase): DocPaymentsInvoiceDao {
        return database.DocPaymentsInvoiceDao()
    }

    @Provides
    fun provideRefMeterZonesRepository(dao: DocPaymentsInvoiceDao): DocPaymentsExpenseRepository {
        return DocPaymentsExpenseRepository(dao)
    }
}