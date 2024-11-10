import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
class Students_list_JSON(private val filePath: String) {

    private val gson = Gson()
    private val students: MutableList<Student> = readFromJson()

    // ������� ������ ��������� �� JSON �����
    private fun readFromJson(): MutableList<Student> {
        return try {
            val jsonString = File(filePath).readText()
            val listType: Type = object : TypeToken<MutableList<Student>>() {}.type
            gson.fromJson(jsonString, listType)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // ������� ������ ��������� � JSON ����
    private fun writeToJson() {
        val jsonString = gson.toJson(students)
        File(filePath).writeText(jsonString)
    }

    // ������� ���������� ������ ��������
    fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
        val newId = if (students.isEmpty()) 1 else students.last().id + 1
        val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
        students.add(newStudent)
        writeToJson() // ��������� ���� ����� ����������
        println("��������: $newStudent")
    }

    // ������� ������ �������� �� ID
    fun repStudId(id: Int, surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            writeToJson() // ��������� ���� ����� ���������
            println("������� � ID $id ��� �������.")
            true
        } else {
            println("������� � ID $id �� ������.")
            false
        }
    }

    // ������� �������� �������� �� ID
    fun removeStudent(id: Int): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students.removeAt(index)
            writeToJson() // ��������� ���� ����� ��������
            println("������� � ID $id ��� �����.")
            true
        } else {
            println("������� � ID $id �� ������.")
            false
        }
    }

    // ������� ��������� ���������� ���������
    fun getStudentCount(): Int {
        return students.size
    }

    // ������� ��������� �������� �� ID
    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}