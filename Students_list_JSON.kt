import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
class Students_list_JSON: Students_list_super(),StudentStrategy {

    private val gson = Gson()

    override fun readFromFile(path: String) {
        try {
            val file = File(path)
            val type: Type = object : TypeToken<List<Student>>() {}.type
            val loadedStudents: List<Student> = gson.fromJson(file.reader(), type)
            students.clear()
            students.addAll(loadedStudents)
        } catch (e: Exception) {
            println("������ ��� ������ �����: ${e.message}")
        }
    }

    override fun writeToFile(path: String) {
        try {
            println("���������� � ����: ${students}")
            val file = File(path)
            // ������ � ����
            file.writer().use { writer ->
                gson.toJson(students, writer)
            }
            println("������ ������� �������� � ����: $path")
        } catch (e: Exception) {
            println("������ ��� ������ � ����: ${e.message}")
        }
    }
}