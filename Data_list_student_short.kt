class Data_list_student_short(studlist: Array<Student_Short>): Datalist<Student_Short>(studlist)
{
    override fun get_names(id: Int): Array<String>
    {
        return arrayOf("ID", "F.I.O", "Git", "Contact")
    }

    override fun get_data(): DataTable {
        val attr = mutableListOf<MutableList<Any>>() // Список, который мы будем преобразовывать в DataTable
        var count = 1

        for (str in data) {
            // Создаем запись для строки с данными
            attr.add(mutableListOf(count, str.surnameIn?:"Нет данных", str.git?:"Нет данных", str.contact?:"Нет данных"))
            count++
        }

        // Возвращаем DataTable, создавая его из списка attr
        return DataTable(attr)
    }
}