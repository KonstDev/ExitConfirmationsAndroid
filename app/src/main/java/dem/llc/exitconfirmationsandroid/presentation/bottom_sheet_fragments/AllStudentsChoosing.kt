package dem.llc.exitconfirmationsandroid.presentation.bottom_sheet_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dem.llc.exitconfirmationsandroid.databinding.StudentsChoosingFragmentBinding
import dem.llc.exitconfirmationsandroid.data.models.Student
import dem.llc.exitconfirmationsandroid.presentation.recyclerview.students.StudentsAdapter

class AllStudentsChoosing(
    private val frameSwitcherData: FrameSwitcherData,
    private val go_back_btn: ImageButton
) : Fragment() {

    private lateinit var binding: StudentsChoosingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = StudentsChoosingFragmentBinding.inflate(inflater, container, false)

        go_back_btn.visibility = View.VISIBLE

        loadAllStudents()

        binding.continueBtn.setOnClickListener(View.OnClickListener { //chosen students ids
            val chosenStudentsIds =
                (binding.studentsRv.adapter as StudentsAdapter?)!!.chosenStudentsIds
            if (chosenStudentsIds == "") {
                Toast.makeText(context, "צריך לבחור לפחות תלמיד אחת", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            frameSwitcherData.exitPermission.students_ids = chosenStudentsIds

            //chosen students names
            frameSwitcherData.exitPermission.students_names =
                (binding.studentsRv.adapter as StudentsAdapter?)!!.chosenStudentsNames

            //setting group of the student/students
            frameSwitcherData.exitPermission.group = (binding.studentsRv.adapter as StudentsAdapter?)!!.groups

            frameSwitcherData.fragmentManager.beginTransaction()
                .replace(
                    frameSwitcherData.frame_layout_id,
                    ExitPermissionDetails(frameSwitcherData, go_back_btn)
                ).commit()
        })

        go_back_btn.setOnClickListener {
            frameSwitcherData.fragmentManager.beginTransaction()
                .replace(
                    frameSwitcherData.frame_layout_id, PermissionTypeChoosingFragment(
                        frameSwitcherData, go_back_btn
                    )
                ).commit()
        }

        binding.studentsSearchv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val studentsAdapter = (binding.studentsRv.adapter as StudentsAdapter?)!!
                studentsAdapter.filter(newText)
                return true
            }
        })
        return binding.root
    }

    private fun loadAllStudents() {
        FirebaseDatabase.getInstance().reference
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val students = ArrayList<Student>()
                    for (snapshot1 in snapshot.child("Groups").children) {
                        val studentsStr = snapshot1.value.toString().split(",".toRegex())
                            .dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                        for (studentId in studentsStr) {
                            val studentSnapshot = snapshot.child("Students").child(studentId)
                            students.add(
                                Student(
                                    studentId, studentSnapshot.child("name").value.toString(),
                                    snapshot1.key.toString(), false
                                )
                            )
                        }
                    }
                    binding.studentsRv.layoutManager = LinearLayoutManager(context)
                    binding.studentsRv.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.studentsRv.adapter =
                        StudentsAdapter(
                            students
                        )
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}
