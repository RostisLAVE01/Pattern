import java.sql.DriverManager


class DatabaseSelect {
    private val url = "jdbc:postgresql://localhost:5432/Students"
    private val user = "postgres"
    private val password = "1"

    fun getAllStudents(): List<Student> {
        val students: MutableList<Student> = ArrayList()
        try {
            DriverManager.getConnection(url, user, password).use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT * FROM student").use { rs ->
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
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return students
    }
}

