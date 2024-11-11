import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
class Students_list_JSON(filePath: String): Students_list_super(filePath) {

    init {
        objectMapper = ObjectMapper()
        students.addAll(readFromFile())
    }

    override fun readFromFile(): MutableList<Student> {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                objectMapper.readValue(
                    file,
                    objectMapper.typeFactory.constructCollectionType(MutableList::class.java, Student::class.java)
                )
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
            mutableListOf()
        }
    }

    override fun writeToFile() {
        try {
            objectMapper.writeValue(File(filePath), students)
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }
}