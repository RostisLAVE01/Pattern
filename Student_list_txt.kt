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
            require(n >= 0) { "Индекс n должен быть больше или равен 0." }
            require(k > 0) { "Количество k должно быть больше 0." }

            return Data_list_student_short(students
                .drop(n)
                .take(k)
                .toTypedArray()
            )
        }

        // Функция сортировки по ФамилияИнициалы
        fun sortFIO(students: List<Student_Short>): List<Student_Short> {
            return students.sortedWith(compareBy({ it.surnameIn }))
        }

        private val students: MutableList<Student> = mutableListOf() // Список студентов

        // Функция добавления нового студента
        fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
            val newId = if (students.isEmpty()) 1 else students.last().id + 1 // Генерация нового ID
            val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
            students.add(newStudent)
            println("Добавлен: $newStudent")
        }

        // Функция замены студента по ID
        fun repStudId(id: Int,surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
            val index = students.indexOfFirst { it.id == id }
            return if (index != -1) {
                // Заменяем студента на нового с тем же ID
                students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
                println("Студент с ID $id был обновлён.")
                true
            } else {
                println("Студент с ID $id не найден.")
                false
            }
        }

        // Функция удаления студента по ID
        fun removeStudent(id: Int): Boolean {
            val index = students.indexOfFirst { it.id == id }
            return if (index != -1) {
                students.removeAt(index)
                println("Студент с ID $id был удалён.")
                true
            } else {
                println("Студент с ID $id не найден.")
                false
            }
        }

        // Функция получения количества студентов
        fun getStudentShortCount(): Int {
            return students.size
        }

    }
}