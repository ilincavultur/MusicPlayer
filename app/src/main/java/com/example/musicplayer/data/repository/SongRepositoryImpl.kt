package com.example.musicplayer.data.repository

import com.example.musicplayer.core.util.Resource
import com.example.musicplayer.data.local.SongDao
import com.example.musicplayer.data.local.entity.toSong
import com.example.musicplayer.data.remote.db.FirestoreSongsDb
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.domain.models.toSongEntity
import com.example.musicplayer.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SongRepositoryImpl @Inject constructor(
    private val dao: SongDao,
    private val firestoreSongsDb: FirestoreSongsDb
) : SongRepository {
    override suspend fun insertSong(song: Song) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSong(song: Song) {
        TODO("Not yet implemented")
    }

    override suspend fun getSongs(): Flow<Resource<List<Song>>> = flow {

        emit(Resource.Loading())

        val songs = dao.getSongs().map {
            it.toSong()
        }

        emit(Resource.Loading(data = songs))

        try {
            val remoteSongs = firestoreSongsDb.getRemoteSongs()

            dao.deleteSongs(remoteSongs.map {
                it.mediaId?.toInt() ?: 0
            })
            dao.insertSongs(
                remoteSongs.map {
                    it.toSongEntity()
                }
            )

        } catch (e: HttpException) {
            emit(Resource.Error(message = "Ooops something went wrong", data = songs))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Ooops something went wrong", data = songs))
        }

        val newSongs = dao.getSongs().map {
            it.toSong()
        }
        emit(Resource.Success(newSongs))

        emit(Resource.Success(songs))

    }

    override suspend fun exists(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}