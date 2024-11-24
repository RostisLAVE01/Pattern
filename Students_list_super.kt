import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

// ����������
open class Students_list_super {

    // ������ ���������
    var students = mutableListOf<Student>()

    //open fun writeToFile(){}

    // ���������� ������ ������ ��� ��������� ��������� ������ ���������
    fun get_k_n_student_short_list(n: Int, k: Int, students: List<Student_Short>): Data_list_student_short {
        require(n >= 0) { "������ n ������ ���� ������ ��� ����� 0." }
        require(k > 0) { "���������� k ������ ���� ������ 0." }
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
        println("��������: $newStudent")
    }

    fun repStudId(id: Int, surname: String, name: String, patronymic: String?,
                  phone: String?, telegram: String?, email: String?, git: String?): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students[index] = Student(id, surname, name, patronymic, phone, telegram, email, git)
            //writeToFile()
            println("������� � ID $id ��� �������.")
            true
        } else {
            println("������� � ID $id �� ������.")
            false
        }
    }

    fun removeStudent(id: Int): Boolean {
        val index = students.indexOfFirst { it.id == id }
        return if (index != -1) {
            students.removeAt(index)
            //writeToFile()
            println("������� � ID $id ��� �����.")
            true
        } else {
            println("������� � ID $id �� ������.")
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