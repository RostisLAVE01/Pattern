import java.io.File
import java.io.FileReader

class Student_list_txt : Students_list_super(), StudentStrategy {

    override fun readFromFile(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                FileReader(file).use { reader ->
                    val loadedStudents = reader.readLines().map { line ->
                        val parts = line.split(",")
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

                    students.clear()
                    students.addAll(loadedStudents)
                }
            } else {
                println("Файл не найден: $path")
                students.clear()
            }
        } catch (e: Exception) {
            println("Ошибка при чтении файла: ${e.message}")
        }
    }

    override fun writeToFile(path: String) {
        try {
            val file = File(path)
            file.printWriter().use { out ->
                students.forEach { student ->
                    out.println("${student.id},${student.surname},${student.name},${student.patronymic},${student.phone},${student.telegram},${student.email},${student.git}")
                }
            }
            println("Данные успешно записаны в файл: $path")
        } catch (e: Exception) {
            println("Ошибка при записи в файл: ${e.message}")
        }
    }
}