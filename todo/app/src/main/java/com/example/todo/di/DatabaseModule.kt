package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.data.local.AppDatabase
import com.example.todo.data.local.AppDatabase2
import com.example.todo.data.local.AppDatabase3
import com.example.todo.data.local.repository.DayRepository
import com.example.todo.data.local.repository.ListTaskRepository
import com.example.todo.data.local.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ToDoDatabaseModule {

    @Provides
    fun provideAppToDO(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "day"
        ).build()
    }

    @Provides
    fun provideToDoDao(appDatabase: AppDatabase): DayRepository {
        return appDatabase.dayDao()
    }

    @Provides
    fun provideListTask(@ApplicationContext appContext: Context): AppDatabase2 {
        return Room.databaseBuilder(
            appContext,
            AppDatabase2::class.java,
            "listtask"
        ).build()
    }

    @Provides
    fun provideListTaskDao(appDatabase2: AppDatabase2): ListTaskRepository {
        return appDatabase2.listTaskDao()
    }

    @Provides
    fun provideSettings(@ApplicationContext appContext: Context): AppDatabase3 {
        return Room.databaseBuilder(
            appContext,
            AppDatabase3::class.java,
            "settings"
        ).build()
    }

    @Provides
    fun provideSettingsDao(appDatabase3: AppDatabase3): SettingsRepository {
        return appDatabase3.settingsDao()
    }


}

