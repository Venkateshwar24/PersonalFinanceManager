package com.demo.personalfinancemanager.di

import com.demo.personalfinancemanager.data.repository.DefaultFinanceRepository
import com.demo.personalfinancemanager.data.repository.FinanceRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFinanceRepository(
        impl: DefaultFinanceRepository
    ): FinanceRepository
}
