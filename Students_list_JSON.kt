import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
class Students_list_JSON(private val filePath: String) {

    private val gson = Gson()
    private val students: MutableList<Student> = readFromJson()

    // Функция чтения студентов из JSON файла
    private fun readFromJson(): MutableList<Student> {
        return try {
            val jsonString = File(filePath).readText()
            val listType: Type = object : TypeToken<MutableList<Student>>() {}.type
            gson.fromJson(jsonString, listType)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // Функция записи студентов в JSON файл
    private fun writeToJson() {
        val jsonString = gson.toJson(students)
        File(filePath).writeText(jsonString)
    }

    // Функция добавления нового студента
    fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
        val newId = if (students.isEmpty()) 1 else students.last().id + 1
        val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
        students.add(newStudent)
        writeToJson() // Обновляем файл после добавления
        println("Добавлен: $newStudent")
    }

    // Функция замены студента по ID
    fun repStudId(id: Int, surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            writeToJson() // Обновляем файл после изменения
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
            writeToJson() // Обновляем файл после удаления
            println("Студент с ID $id был удалён.")
            true
        } else {
            println("Студент с ID $id не найден.")
            false
        }
    }

    // Функция получения количества студентов
    fun getStudentCount(): Int {
        return students.size
    }

    // Функция получения студента по ID
    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}