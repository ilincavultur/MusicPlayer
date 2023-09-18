package com.example.musicplayer.data.remote.db

import com.example.musicplayer.domain.models.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreSongsDb {
    private val db = FirebaseFirestore.getInstance()

    private val songCollection = db.collection("songs")

    suspend fun getRemoteSongs(): List<Song> {
        return try {
            songCollection
                .get()
                .await()
                .toObjects(Song::class.java)
        } catch (e: java.lang.Exception) {
            emptyList<Song>()
        }
    }
}