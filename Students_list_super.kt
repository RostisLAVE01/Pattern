import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

// Суперкласс
open class Students_list_super {

    // Список студентов
    var students = mutableListOf<Student>()

    //open fun writeToFile(){}

    // Метод для получения короткого списка студентов
    fun get_k_n_student_short_list(n: Int, k: Int): MutableList<Student_Short> {
        require(n >= 0) { "Индекс n должен быть больше или равен 0." }
        require(k > 0) { "Количество k должно быть больше 0." }

        // Преобразование студентов в Student_Short
        val shortList = students.drop(n).take(k).map { student ->

            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")

            Student_Short(
                id = student.id,
                surnameIN = surnameIN,
                git = student.git,
                contact = contact
            )
        }

        return shortList.toMutableList()
    }

    fun sortFIO(students: List<Student_Short>): List<Student_Short> {
        return students.sortedWith(compareBy({ it.surnameIn }))
    }

    fun addStudent(surname: String, name: String, patronymic: String?, phone: String?,
                   telegram: String?, email: String?, git: String?) {
        val newId = if (students.isEmpty()) 1 else students.last().id + 1
        val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
        students.add(newStudent)
        //writeToFile()
        println("Добавлен: $newStudent")
    }

    fun repStudId(id: Int, surname: String, name: String, patronymic: String?,
                  phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            //writeToFile()
            println("Студент с ID $id был обновлён.")
            true
        } else {
            println("Студент с ID $id не найден.")
            false
        }
    }

    fun removeStudent(id: Int): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students.removeAt(index)
            //writeToFile()
            println("Студент с ID $id был удалён.")
            true
        } else {
            println("Студент с ID $id не найден.")
            false
        }
    }

    fun getStudentCount(): Int {
        return students.size
    }

    open fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}