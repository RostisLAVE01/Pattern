import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

class Students_list_YAML(private val filePath: String) {

    private val objectMapper: ObjectMapper = ObjectMapper(YAMLFactory())
    private val students: MutableList<Student> = readFromYaml()

    // ������� ������
    private fun readFromYaml(): MutableList<Student> {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                objectMapper.readValue(file, objectMapper.typeFactory.constructCollectionType(MutableList::class.java, Student::class.java))
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            mutableListOf() // ���������� ������ ������ � ������ ������
        }
    }

    // ������� ������
    private fun writeToYaml() {
        try {
            objectMapper.writeValue(File(filePath), students)
        } catch (e: Exception) {
            println("������ ��� ������ � ����: ${e.message}")
        }
    }

    // ������� ����������
    fun addStudent(surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?) {
        val newId = if (students.isEmpty()) 1 else students.last().id + 1 // ��������� ������ ID
        val newStudent = Student(newId, surname, name, patronymic, phone, telegram, email, git)
        students.add(newStudent)
        writeToYaml() // ��������� ���� ����� ����������
        println("��������: $newStudent")
    }

    // ������� ������ �� ID
    fun repStudId(id: Int, surname: String, name: String, patronymic: String?, phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            writeToYaml() // ��������� ���� ����� ���������
            println("������� � ID $id ��� �������.")
            true
        } else {
            println("������� � ID $id �� ������.")
            false
        }
    }

    // ������� �������� �� ID
    fun removeStudent(id: Int): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students.removeAt(index)
            writeToYaml() // ��������� ���� ����� ��������
            println("������� � ID $id ��� �����.")
            true
        } else {
            println("������� � ID $id �� ������.")
            false
        }
    }

    // ������� ����������
    fun getStudentCount(): Int {
        return students.size
    }

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}