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
    }
}