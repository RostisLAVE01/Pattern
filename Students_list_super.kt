import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File

// ����������
open class Students_list_super {

    // ������ ���������
    var students = mutableListOf<Student>()

    //open fun writeToFile(){}

    // ����� ��� ��������� ��������� ������ ���������
    fun get_k_n_student_short_list(n: Int, k: Int): MutableList<Student_Short> {
        require(n >= 0) { "������ n ������ ���� ������ ��� ����� 0." }
        require(k > 0) { "���������� k ������ ���� ������ 0." }

        // �������������� ��������� � Student_Short
        val shortList = students.drop(n).take(k).map { student ->

            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")

            Student_Short(
                id = student.id,
                surnameIN = surnameIN,
                git = student.git,
                contact = contact
            )
        }

        return shortList.toMutableList()
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

    open fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}