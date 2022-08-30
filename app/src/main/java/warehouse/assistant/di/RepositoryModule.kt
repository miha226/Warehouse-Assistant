package warehouse.assistant.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import warehouse.assistant.data.csv.CSVParser
import warehouse.assistant.data.csv.ItemsParser
import warehouse.assistant.data.repository.StorageRepositoryImpl
import warehouse.assistant.domain.model.Item
import warehouse.assistant.domain.repository.StorageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemsParser(
        itemsParser:ItemsParser
    ):CSVParser<Item>

    @Binds
    @Singleton
    abstract fun bindStorageRepository(
        storageRepositoryImpl:StorageRepositoryImpl
    ):StorageRepository
}