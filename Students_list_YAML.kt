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
            println("������ ��� ������ �����: ${e.message}")
        }
    }

    override fun writeToFile(path: String) {
        try {
            println("���������� � ����: ${students}")
            FileWriter(File(path)).use { writer ->
                yaml.dump(students, writer)
            }
            println("������ ������� �������� � ����: $path")
        } catch (e: Exception) {
            println("������ ��� ������ � ����: ${e.message}")
        }
    }
}