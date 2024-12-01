// Определим общий интерфейс для работы со студентами
interface StudentListInterface {
    fun getStudentById(id: Int): Student?
    fun get_k_n_student_short_list(k: Int, n: Int): MutableList<Student_Short>
    fun addStudent(student: Student): Boolean
    fun updateStudent(student: Student): Boolean

    fun deleteStudent(studentId: Int): Boolean
    fun getStudentCount(): Int
}

 //Адаптер для работы с конкретным источником данных
class StudentListAdapter(private var path: String) : StudentListInterface {
    private var studentList: Student_Manager? = null

    init {
        when (path.split(".").last()) {
            "txt" -> studentList = Student_Manager(Student_list_txt())
            "json" -> studentList = Student_Manager(Students_list_JSON())
            "yaml" -> studentList = Student_Manager(Students_list_YAML())
        }
        studentList?.readFromFile(path)
    }

    override fun getStudentById(id: Int): Student? {
        return studentList?.getStudentById(id)
    }

    // Исправляем метод get_k_n_student_short_list
    override fun get_k_n_student_short_list(k: Int, n: Int): MutableList<Student_Short> {
        return studentList?.get_k_n_student_short_list(n, k)?.toMutableList() ?: mutableListOf()
    }

    override fun addStudent(student: Student): Boolean {
        studentList?.addStudent(
            surname = student.surname,
            name = student.name,
            patronymic = student.patronymic,
            phone = student.phone,
            telegram = student.telegram,
            email = student.email,
            git = student.git
        )
        return true
    }

    override fun updateStudent(student: Student): Boolean {
        return studentList?.repStudId(
            id = student.id,
            surname = student.surname,
            name = student.name,
            patronymic = student.patronymic,
            phone = student.phone,
            telegram = student.telegram,
            email = student.email,
            git = student.git
        ) ?: false
    }

    override fun deleteStudent(studentId: Int): Boolean {
        return studentList?.removeStudent(studentId) ?: false
    }

    override fun getStudentCount(): Int {
        return studentList?.getStudentCount() ?: 0
    }
}

// Класс стратегии
class StudentList(path: String) : StudentListInterface {
    private var studentList: StudentListInterface? = null

    init {
        if (path == "pg") {
            studentList = Students_list_DB.getInstance()
        } else {
            studentList = StudentListAdapter(path)
        }
    }

    override fun getStudentById(id: Int): Student? {
        return studentList?.getStudentById(id)
    }

    override fun get_k_n_student_short_list(k: Int, n: Int): MutableList<Student_Short> {
        return studentList?.get_k_n_student_short_list(k, n) ?: mutableListOf()
    }

    override fun addStudent(student: Student): Boolean {
        return studentList?.addStudent(student) ?: false
    }

    override fun updateStudent(student: Student): Boolean {
        return studentList?.updateStudent(student) ?: false
    }

    override fun deleteStudent(studentId: Int): Boolean {
        return studentList?.deleteStudent(studentId) ?: false
    }

    override fun getStudentCount(): Int {
        return studentList?.getStudentCount() ?: 0
    }
}
