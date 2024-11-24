import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class Students_list_YAML : Students_list_super(), StudentStrategy {

    private val yaml = Yaml()

    override fun readFromFile(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                FileReader(file).use { reader ->
                    val loadedStudents: List<Student>? = yaml.load(reader)
                    students.clear()
                    students.addAll(loadedStudents ?: mutableListOf())
                }
            } else {
                students.clear()
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
        }
    }

    override fun writeToFile(path: String) {
        try {
            println("Записываем в файл: ${students}")
            FileWriter(File(path)).use { writer ->
                yaml.dump(students, writer)
            }
            println("Данные успешно записаны в файл: $path")
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }
}