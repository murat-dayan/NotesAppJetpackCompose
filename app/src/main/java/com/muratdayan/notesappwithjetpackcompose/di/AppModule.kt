package com.muratdayan.notesappwithjetpackcompose.di

import android.content.Context
import androidx.room.Room
import com.muratdayan.notesappwithjetpackcompose.core.utils.Constants
import com.muratdayan.notesappwithjetpackcompose.data.locale.repository.RepositoryImpl
import com.muratdayan.notesappwithjetpackcompose.data.locale.services.NotesDao
import com.muratdayan.notesappwithjetpackcompose.data.locale.services.RmDatabase
import com.muratdayan.notesappwithjetpackcompose.domain.repository.NInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRmDatabase(@ApplicationContext context: Context): RmDatabase {
        return Room.databaseBuilder(
            context,
            RmDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun provideNotesDao(rmDatabase: RmDatabase): NotesDao {
        return rmDatabase.notesDao()
    }


    @Provides
    @Singleton
    fun providesNInterface(notesDao: NotesDao): NInterface {
        return RepositoryImpl(notesDao)
    }





}