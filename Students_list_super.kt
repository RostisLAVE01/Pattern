import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

// Суперкласс
open class Students_list_super {

    // Список студентов
    var students = mutableListOf<Student>()

    //open fun writeToFile(){}

    // Добавление нового метода для получения короткого списка студентов
    fun get_k_n_student_short_list(n: Int, k: Int, students: List<Student_Short>): Data_list_student_short {
        require(n >= 0) { "Индекс n должен быть больше или равен 0." }
        require(k > 0) { "Количество k должно быть больше 0." }
        return Data_list_student_short(students.drop(n).take(k).toTypedArray())
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

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}