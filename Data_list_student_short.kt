class Data_list_student_short(studlist: Array<Student_Short>): Datalist<Student_Short>(studlist)
{
    override fun get_names(id: Int): Array<String>
    {
        return arrayOf("ID", "F.I.O", "Git", "Contact")
    }

    override fun get_data(): DataTable {
        val attr = mutableListOf<MutableList<Any>>() // ������, ������� �� ����� ��������������� � DataTable
        var count = 1

        for (str in data) {
            // ������� ������ ��� ������ � �������
            attr.add(mutableListOf(count, str.surnameIn?:"��� ������", str.git?:"��� ������", str.contact?:"��� ������"))
            count++
        }

        // ���������� DataTable, �������� ��� �� ������ attr
        return DataTable(attr)
    }
}