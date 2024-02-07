package com.fylora.pomodorotimer.di

import android.app.Application
import androidx.room.Room
import com.fylora.pomodorotimer.core.Consts
import com.fylora.pomodorotimer.data.local.TaskDatabase
import com.fylora.pomodorotimer.data.notifications.NotificationServiceImpl
import com.fylora.pomodorotimer.data.repository.TaskRepositoryImpl
import com.fylora.pomodorotimer.domain.notifications.NotificationService
import com.fylora.pomodorotimer.domain.repository.TaskRepository
import com.fylora.pomodorotimer.domain.use_case.CalculateSessions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = TaskDatabase::class.java,
            name = Consts.TASK_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(database: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideCalculateSessionsUseCase(): CalculateSessions {
        return CalculateSessions()
    }

    @Provides
    @Singleton
    fun provideNotificationService(context: Application): NotificationService {
        return NotificationServiceImpl(context)
    }
}