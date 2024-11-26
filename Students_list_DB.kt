import java.sql.DriverManager


class Students_list_DB private constructor() {
    private val url = "jdbc:postgresql://localhost:5432/Students"
    private val user = "postgres"
    private val password = "1"

    companion object {
        @Volatile
        private var INSTANCE: Students_list_DB? = null

        fun getInstance(): Students_list_DB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Students_list_DB().also { INSTANCE = it }
            }
        }
    }


    fun getStudentById(id: Int): Student? {
        var student: Student? = null
        val query = "SELECT * FROM student WHERE id = ?"
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        try {
            pstmt.setInt(1, id)  // ��������� ��������� �������
            val rs = pstmt.executeQuery()

            if (rs.next()) {
                val studentId = rs.getInt("id")
                val surname = rs.getString("surname")
                val name = rs.getString("name")
                val patronymic = rs.getString("patronymic")
                val phone = rs.getString("phone")
                val telegram = rs.getString("telegram")
                val email = rs.getString("email")
                val git = rs.getString("git")
                student = Student(studentId, surname, name, patronymic, phone, telegram, email, git)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close()  // ��������� ����������
        }

        return student
    }

    fun get_k_n_student_short_list(k: Int, n: Int): MutableList<Student_Short> {
        val query = "SELECT id, surname, name, patronymic, phone, telegram, email, git FROM student ORDER BY id LIMIT ? OFFSET ?"
        val studentList = mutableListOf<Student_Short>()
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        return try {
            pstmt.setInt(1, n)  // ��������� LIMIT
            pstmt.setInt(2, k)  // ��������� OFFSET
            val rs = pstmt.executeQuery()

            while (rs.next()) {
                val id = rs.getInt("id")
                val surname = rs.getString("surname")
                val name = rs.getString("name")
                val patronymic = rs.getString("patronymic")
                val phone = rs.getString("phone")
                val telegram = rs.getString("telegram")
                val email = rs.getString("email")
                val git = rs.getString("git")
                studentList.add(Student_Short(id, surname + name + patronymic, git, phone + telegram + email))
            }
            studentList  // ���������� �������� ������ ���������
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()  // ���������� ������ ������ � ������ ������
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close() // ��������� ����������
        }
    }

    fun addStudent(student: Student): Boolean {
        val query = "INSERT INTO student (surname, name, patronymic, phone, telegram, email, git) VALUES (?, ?, ?, ?, ?, ?, ?)"
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        return try {
            pstmt.setString(1, student.surname)
            pstmt.setString(2, student.name)
            pstmt.setString(3, student.patronymic)
            pstmt.setString(4, student.phone)
            pstmt.setString(5, student.telegram)
            pstmt.setString(6, student.email)
            pstmt.setString(7, student.git)

            pstmt.executeUpdate() > 0 // true, ���� ������ ���� ��������
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close() // ��������� ����������
        }
    }


    fun updateStudent(student: Student): Boolean {
        val query = "UPDATE student SET surname = ?, name = ?, patronymic = ?, phone = ?, telegram = ?, email = ?, git = ? WHERE id = ?"
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        return try {
            pstmt.setString(1, student.surname)
            pstmt.setString(2, student.name)
            pstmt.setString(3, student.patronymic)
            pstmt.setString(4, student.phone)
            pstmt.setString(5, student.telegram)
            pstmt.setString(6, student.email)
            pstmt.setString(7, student.git)
            pstmt.setInt(8, student.id) // ��������� ID ��� ������

            pstmt.executeUpdate() > 0 // ���������� true, ���� ������ ���� ���������
        } catch (e: Exception) {
            e.printStackTrace()
            false // ���������� false � ������ ������
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close() // ��������� ����������
        }
    }

    fun deleteStudent(studentId: Int): Boolean {
        val query = "DELETE FROM student WHERE id = ?"
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        return try {
            pstmt.setInt(1, studentId)
            pstmt.executeUpdate() > 0 // true, ���� ������ ���� �������
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close() // ��������� ����������
        }
    }

    fun getStudentCount(): Int {
        val query = "SELECT COUNT(*) FROM student"
        val conn = DriverManager.getConnection(url, user, password)
        val pstmt = conn.prepareStatement(query)

        return try {
            val rs = pstmt.executeQuery()
            if (rs.next()) {
                rs.getInt(1)
            } else {
                0 // ���� �� ���, ���������� 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0 // ������ = 0
        } finally {
            pstmt.close() // ��������� PreparedStatement
            conn.close() // ��������� ����������
        }
    }

}