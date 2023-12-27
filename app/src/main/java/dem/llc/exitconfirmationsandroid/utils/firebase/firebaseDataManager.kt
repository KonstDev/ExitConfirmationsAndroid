package dem.llc.exitconfirmationsandroid.utils.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseDatabaseException(message: String) : Exception(message)

// Function to read data from the database
suspend fun DatabaseReference.getDataOnce(): DataSnapshot {
    return suspendCancellableCoroutine { continuation ->
        this.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                continuation.resume(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                continuation.resumeWithException(
                    FirebaseDatabaseException("Database Error: ${databaseError.message}")
                )
            }
        })
    }
}