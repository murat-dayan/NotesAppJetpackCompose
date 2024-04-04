package com.example.notesappwithjetpackcompose.di

import android.content.Context
import androidx.room.Room
import com.example.notesappwithjetpackcompose.repo.NotesDaoRepository
import com.example.notesappwithjetpackcompose.data.locale.services.NotesDao
import com.example.notesappwithjetpackcompose.data.locale.services.RmDatabase
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
            "notes.sqlite"
        ).fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideNotesDao(rmDatabase: RmDatabase): NotesDao {
        return rmDatabase.notesDao()
    }


    @Provides
    @Singleton
    fun provideNotesDaoRepository(notesDao: NotesDao): NotesDaoRepository {
        return NotesDaoRepository(notesDao)
    }





}