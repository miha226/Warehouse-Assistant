package warehouse.assistant.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import warehouse.assistant.data.local.CompanyDB

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCompanyDatabase(app: Application):CompanyDB{
        return  Room.databaseBuilder(
            app,
            CompanyDB::class.java,
            "company.db"
        ).fallbackToDestructiveMigration().build()
    }


}