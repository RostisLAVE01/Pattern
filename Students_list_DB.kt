import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class Students_list_DB : Students_list_super() {
    private val url = "jdbc:postgresql://localhost:5432/Students"
    private val user = "postgres"
    private val password = "1"

    override fun getStudentById(id: Int): Student? {
        // ������� ��������� � ��������� ������
        var student = super.getStudentById(id)

        // ���� �������� �� �����, ��������� � ��
        if (student == null) {
            val query = "SELECT * FROM student WHERE id = ?"
            try {
                DriverManager.getConnection(url, user, password).use { conn ->
                    conn.prepareStatement(query).use { pstmt ->
                        pstmt.setInt(1, id)
                        val rs = pstmt.executeQuery()
                        if (rs.next()) {
                            val id = rs.getInt("id")
                            val surname = rs.getString("surname")
                            val name = rs.getString("name")
                            val patronymic = rs.getString("patronymic")
                            val phone = rs.getString("phone")
                            val telegram = rs.getString("telegram")
                            val email = rs.getString("email")
                            val git = rs.getString("git")
                            // ������� �������� �� ������ ������ �� ��
                            student = Student(id, surname, name, patronymic, phone, telegram, email, git)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return student
    }

    // ����� ��� ��������� �������� �� �� � ���������� ������ ���������
    fun loadStudentsFromDB() {
        val query = "SELECT * FROM student"

        try {
            Class.forName("org.postgresql.Driver")
            DriverManager.getConnection(url, user, password).use { conn ->
                conn.createStatement().use { stmt ->
                    val rs = stmt.executeQuery(query)
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val surname = rs.getString("surname")
                        val name = rs.getString("name")
                        val patronymic = rs.getString("patronymic")
                        val phone = rs.getString("phone")
                        val telegram = rs.getString("telegram")
                        val email = rs.getString("email")
                        val git = rs.getString("git")

                        students.add(Student(id, surname, name, patronymic, phone, telegram, email, git))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}