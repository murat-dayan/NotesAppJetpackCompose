package com.example.notesappwithjetpackcompose.data.locale.repository

import com.example.notesappwithjetpackcompose.core.common.Resource
import com.example.notesappwithjetpackcompose.data.locale.dto.NoteDto
import com.example.notesappwithjetpackcompose.data.locale.mapper.toDomainNote
import com.example.notesappwithjetpackcompose.data.locale.services.NotesDao
import com.example.notesappwithjetpackcompose.domain.model.Note
import com.example.notesappwithjetpackcompose.domain.repository.NInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val notesDao: NotesDao
)  : NInterface {


    override fun allNotes(): Flow<Resource<List<Note>>>  = flow{

        emit(Resource.Loading())

        val result = notesDao.allNotes().map {
            it.toDomainNote()
        }
        emit(Resource.Success(result))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun searchNotes(searchedText: String): Flow<Resource<List<Note>>> = flow{

        emit(Resource.Loading())

        val result = notesDao.searchNotes(searchedText).map {
            it.toDomainNote()
        }
        emit(Resource.Success(result))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun getNoteById(noteId: Int): Flow<Resource<Note>> = flow{

        emit(Resource.Loading())

        val result = notesDao.getNoteById(noteId)
        if (result != null){
            result.toDomainNote()
            emit(Resource.Success(result.toDomainNote()))
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override suspend fun addNote(note: Note) {
        notesDao.addNote(note)
    }

    override suspend fun updateNote(note: Note) {
        notesDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }


}