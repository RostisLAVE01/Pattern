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

    }
}