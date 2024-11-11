import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

class Students_list_YAML(private val filePath: String) {

    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory())
    private val students: MutableList<Student> = readFromYaml()

    // Функция чтения
    private fun readFromYaml(): MutableList<Student> {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                objectMapper.readValue(file, objectMapper.typeFactory.constructCollectionType(MutableList::class.java, Student::class.java))
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            mutableListOf() // Возвращаем пустой список в случае ошибки
        }
    }

    // Функция записи
    private fun writeToYaml() {
        try {
            objectMapper.writeValue(File(filePath), students)
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }

    // Функция добавления
    fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
        val newId = if (students.isEmpty()) 1 else students.last().id + 1 // Генерация нового ID
        val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
        students.add(newStudent)
        writeToYaml() // Обновляем файл после добавления
        println("Добавлен: $newStudent")
    }

    // Функция замены по ID
    fun repStudId(id: Int, surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            writeToYaml() // Обновляем файл после изменения
            println("Студент с ID $id был обновлён.")
            true
        } else {
            println("Студент с ID $id не найден.")
            false
        }
    }

    // Функция удаления по ID
    fun removeStudent(id: Int): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students.removeAt(index)
            writeToYaml() // Обновляем файл после удаления
            println("Студент с ID $id был удалён.")
            true
        } else {
            println("Студент с ID $id не найден.")
            false
        }
    }

    // Функция количества
    fun getStudentCount(): Int {
        return students.size
    }

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}