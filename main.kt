fun main() {
    val example_1 = Student(
        1,
        "Фолгимштейн",
        "Федор",
        "Крузинштейн",
        "+7-989-345-34-67",
        "@ROOMdoom",
        "RoomDoom@mail.ru",
        "https://github.com/Fedor"
    )

    val example_2 = Student(
        id = 2,
        surname = "Шаповалова",
        name = "Карина",
        phone = "+7-656-134-67-89"
    )

    val example_3 = Student(
        id = 3,
        surname = "Зайцев",
        name = "Иехонтий",
        email = "zoom@example.com"
    )

    val example_4 = Student(
        id = 4,
        surname = "Смурыгин",
        name = "Яхонт",
        telegram = "@yakhont"
    )


    val example_hashMap = hashMapOf<String, Any?>(
        "id" to 5,
        "surname" to "Фуркин",
        "name" to "Иван",
        "patronymic" to "Степанович",
        "phone" to "+7-987-336-56-89",
        "telegram" to "@samsam",
        "mail" to "ivan@mail.ru"
    )

    val example_validPhone = hashMapOf<String, Any?>(
        "id" to 6,
        "surname" to "Кирсанов",
        "name" to "Сергей",
        "patronymic" to "Генадьевич",
        "phone" to "+7-916-123-45-67", // Корректный номер
        "telegram" to "ivan_ivanov",
        "mail" to "ivan@example.com",
        "git" to "ivan_git"
    )
    val validPhone = Student(example_validPhone)

    val valid = Student(
        id = 4,
        surname = "Смурыгин",
        name = "Яхонт",
        git = "https://github.com/Smyrk"
    )

    valid.setContacts(phone = "+7-916-123-45-67", telegram = "johndoe", mail = "sdfasf@mail.ru")

    // Проверяем валидность данных после установки контактов
    val isValid = valid.validate()
    println("Данные студента валидны: $isValid")


    println(example_1)
    println(example_2)
    println(example_3)
    println(example_4)

    val hash = Student(example_hashMap)
    println(hash)
    println(validPhone)

    //lab2

    //строка для парсинга
    val studentData = "7 Мирногов Кирилл Иванович +7-916-123-45-67 mirno_ivanov kirill@example.com kirill-git"

    val stud = Student(studentData)

    println(stud)
    //№3 метод getInfo
    println(stud.getInfo())

    //№4 класс Student_short
    var st = Student_Short(stud)
    println(st)

    //№7,8 чтение из файла
    val students = Student.read_from_txt("fail.txt")
    for(stud in students)
    {
        println(stud)
    }

    //№9,10 чтение из файла
    Student.write_to_txt("write_file.txt", students)


    //DataTable
    val arr   = listOf(
        listOf("Мирногов Кирилл Иванович", "kirill-git", "+7-916-123-45-67"),
        listOf("Мишков Иван Витальевич", "Iva-git", "+7-977-111-45-56")
    )
    val datTab = DataTable(arr)

    println("Количество строк ${datTab.GetNumCol()}")
    println("Количество строк ${datTab.GetNumRow()}")

    val col = 0
    val row = 1
    println("Запись на строке $row и столбце $col: ${datTab.GetElem(row,col)}")
    println(datTab)

    //№2.5 класс Data_list_student_short
    val stArr = arrayOf(st)
    val datalist = Data_list_student_short(stArr)

    val dataTable = datalist.get_data()
    println(dataTable)

    //№2.8

    val datalist2 = Data_list_student_short(stArr)

    println("Название полей: ${datalist2.get_names(0)}")

    println("\n Записи")
    val datatab = datalist2.get_data()
    println(datatab)


    //lab 3

//    //№3.1
//    val stud_listTXT = Student_list_txt.read_from_txt("fail.txt")
//    for(stud_txt in stud_listTXT)
//    {
//        println(stud_txt)
//    }

    //JSON

    val studentManager = Student_Manager(strategy = Students_list_JSON())

    //путь к файлу
    val fileJson = "students.json"

    studentManager.addStudent("Иванов", "Иван", null, "123456789", null, "ivan@example.com", "https://github.com/ivan")
    studentManager.addStudent("Петров", "Петр", "Сидорович", "987654321", "@petrov", "petr@example.com", null)

    println("Количество студентов: ${studentManager.getStudentCount()}")

    println("Студент под ID: ${studentManager.getStudentById(2)}")

    // Запись в файл
    studentManager.writeToFile(fileJson)

    // Yaml
    val studentManageryaml = Student_Manager(strategy = Students_list_YAML())

    //путь к файлу
    val fileYaml = "write_file.yaml"

    studentManageryaml.addStudent("Иванов", "Иван", null, "123456789", null, "ivan@example.com", "https://github.com/ivan")
    studentManageryaml.addStudent("Петров", "Петр", "Сидорович", "987654321", "@petrov", "petr@example.com", null)

    // Запись в файл
    studentManageryaml.writeToFile(fileYaml)

    // Txt
    val studentManagerTxt = Student_Manager(strategy = Student_list_txt())

    //путь к файлу
    val fileTxt = "write_file.txt"

    studentManagerTxt.addStudent("Иванов", "Иван", null, "123456789", null, "ivan@example.com", "https://github.com/ivan")
    studentManagerTxt.addStudent("Петров", "Петр", "Сидорович", "987654321", "@petrov", "petr@example.com", null)

    // Запись в файл
    studentManagerTxt.writeToFile(fileTxt)


    // lab 4

    // номер 5 выполнение select

    val dbManager = DatabaseSelect()
    val students4 = dbManager.getAllStudents()

    for (student in students4) {
        println("ID: ${student.id}, FIO: ${student.surname} ${student.name} ${student.patronymic}, " +
                "Phone: ${student.phone}, Telegram: ${student.telegram}, Email: ${student.email}, Git: ${student.git}")
    }

}