package dem.llc.exitconfirmationsandroid.data.models.exit_permission

import dem.llc.exitconfirmationsandroid.data.models.Madrich
import dem.llc.exitconfirmationsandroid.data.models.Student
import dem.llc.exitconfirmationsandroid.utils.date.getDateString
import dem.llc.exitconfirmationsandroid.utils.date.getTimeString
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_DESTINATION
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_EXIT_DATE
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_EXIT_TIME
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_GROUP
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_ID
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_MADRICH_ID
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_MADRICH_NAME
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_RETURN_DATE
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_RETURN_TIME
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_STUDENT_ID
import dem.llc.exitconfirmationsandroid.utils.firebase.CHILD_STUDENT_NAME
import dem.llc.exitconfirmationsandroid.utils.firebase.FirebaseDataClass
import java.util.Date

data class ExitPermission(
    var id: String? = null,
    var status: String = ExitStatus.CREATED,
    var destination: String = "",
    var group: String = "",
    var madrich: Madrich,
    var student: Student,
    var exitDT: Date,
    var returnDT: Date,
    var confirmation: String // confirmation link for exit or return depending on status
) : FirebaseDataClass{
    override fun getAsHashMap(): HashMap<String, Any?> {
        val hashMap = HashMap<String, Any?>()

        hashMap[CHILD_ID] = id
        hashMap[CHILD_EXIT_DATE] = this.exitDT.getDateString()
        hashMap[CHILD_EXIT_TIME] = this.exitDT.getTimeString()
        hashMap[CHILD_DESTINATION] = destination
        hashMap[CHILD_GROUP] = group
        hashMap[CHILD_MADRICH_ID] = madrich.id
        hashMap[CHILD_MADRICH_NAME] = madrich.name
        hashMap[CHILD_RETURN_DATE] = this.returnDT.getDateString()
        hashMap[CHILD_RETURN_TIME] = this.returnDT.getTimeString()
        hashMap[CHILD_STUDENT_ID] = this.student.id
        hashMap[CHILD_STUDENT_NAME] = this.student.name

        return hashMap
    }

}