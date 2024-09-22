fun main() {
    val example_1 = Student(
        id = 1,
        surname = "Фолгимштейн",
        name = "Федор",
        patronymic = "Крузинштейн",
        phone = "+7-989-345-34-67",
        telegram = "@ROOMdoom",
        email = "RoomDoom@mail.ru",
        git = "https://github.com/Fedor"
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
        "lastname" to "Фуркин",
        "name" to "Иван",
        "fathername" to "Степанович",
        "phone" to "+7-987-336-56-89",
        "telegram" to "@samsam",
        "mail" to "ivan@mail.ru"
    )

    println(example_1)
    println(example_2)
    println(example_3)
    println(example_4)

    val hash = Student(example_hashMap)
    println(hash)
}