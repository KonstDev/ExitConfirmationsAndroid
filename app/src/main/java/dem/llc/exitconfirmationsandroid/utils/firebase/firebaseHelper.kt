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

const val CHILD_EXIT_DATE = "exitDate"
const val CHILD_EXIT_TIME = "exitTime"
const val CHILD_RETURN_DATE = "returnDate"
const val CHILD_RETURN_TIME = "returnTime"

const val CHILD_STATUS = "status"
const val CHILD_DESTINATION = "destination"
const val CHILD_GROUP = "group"
const val CHILD_MADRICH_ID = "madrich_id"
const val CHILD_MADRICH_NAME = "madrich_name"
const val CHILD_STUDENT_ID = "student_id"
const val CHILD_STUDENT_NAME = "student_name"