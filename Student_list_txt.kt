import java.io.File

class Student_list_txt(filePath: String): Students_list_super(filePath){
    init {
        students.addAll(readFromFile())
    }

    override fun readFromFile(): MutableList<Student> {
        return try {
            val file = File(filePath)
            if (file.exists()) {
                file.readLines().map { line ->
                    val parts = line.split(",")  // Ожидаем, что данные разделены запятыми
                    Student(
                        id = parts[0].toInt(),
                        surname = parts[1],
                        name = parts[2],
                        patronymic = parts.getOrNull(3),
                        phone = parts.getOrNull(4),
                        telegram = parts.getOrNull(5),
                        email = parts.getOrNull(6),
                        git = parts.getOrNull(7)
                    )
                }.toMutableList()
            } else {
                mutableListOf()
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
            mutableListOf()
        }
    }

    override fun writeToFile() {
        try {
            val file = File(filePath)
            file.printWriter().use { out ->
                students.forEach { student ->
                    out.println("${student.id},${student.surname},${student.name},${student.patronymic},${student.phone},${student.telegram},${student.email},${student.git}")
                }
            }
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }
}