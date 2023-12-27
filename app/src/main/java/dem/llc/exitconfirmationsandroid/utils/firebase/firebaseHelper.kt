package dem.llc.exitconfirmationsandroid.utils.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

val DB_ROOT = FirebaseDatabase.getInstance()
val DB = DB_ROOT.reference
val AUTH = FirebaseAuth.getInstance()
val CURUSR = AUTH.currentUser

const val NODE_GROUPS = "Groups"
const val NODE_MADRICHS = "Madrichs"
const val NODE_STUDENTS = "Students"
const val NODE_GUARDS = "Guards"

const val CHILD_ID = "id"