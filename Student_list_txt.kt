class Student_list_txt: Student(0, "", ""){

    companion object{
        fun read_from_txt(filePath: String): List<Student>
        {
            return Student.read_from_txt(filePath)
        }

        fun write_to_txt(filePath: String, stud: List<Student>)
        {
            return Student.write_to_txt(filePath,stud)
        }

        fun getStudentId(id:Int,filePath: String):Student?
        {
            var arr = read_from_txt(filePath)
            for(arra in arr)
            {
                if(arra.id == id)
                {
                    return arra
                }
            }
            return null
        }

        //private lateinit var students: List<Student>
        fun get_k_n_student_short_list(n: Int, k: Int, students: List<Student_Short>): Data_list_student_short {
            require(n >= 0) { "������ n ������ ���� ������ ��� ����� 0." }
            require(k > 0) { "���������� k ������ ���� ������ 0." }

            return Data_list_student_short(students
                .drop(n)
                .take(k)
                .toTypedArray()
            )
        }

        // ������� ���������� �� ���������������
        fun sortFIO(students: List<Student_Short>): List<Student_Short> {
            return students.sortedWith(compareBy({ it.surnameIn }))
        }

        private val students: MutableList<Student> = mutableListOf() // ������ ���������

        // ������� ���������� ������ ��������
        fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
            val newId = if (students.isEmpty()) 1 else students.last().id + 1 // ��������� ������ ID
            val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
            students.add(newStudent)
            println("��������: $newStudent")
        }

        // ������� ������ �������� �� ID
        fun repStudId(id: Int,surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
            val index = students.indexOfFirst { it.id == id }
            return if (index != -1) {
                // �������� �������� �� ������ � ��� �� ID
                students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
                println("������� � ID $id ��� �������.")
                true
            } else {
                println("������� � ID $id �� ������.")
                false
            }
        }

        // ������� �������� �������� �� ID
        fun removeStudent(id: Int): Boolean {
            val index = students.indexOfFirst { it.id == id }
            return if (index != -1) {
                students.removeAt(index)
                println("������� � ID $id ��� �����.")
                true
            } else {
                println("������� � ID $id �� ������.")
                false
            }
        }

        // ������� ��������� ���������� ���������
        fun getStudentShortCount(): Int {
            return students.size
        }

    }
}