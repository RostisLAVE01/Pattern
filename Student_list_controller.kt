import javax.swing.RowFilter
import javax.swing.table.TableModel
import kotlin.math.min
import View.*
import javax.swing.SwingUtilities


class Student_list_controller(private val view: View, private val studentsList: Students_list_super) {

    init {
        refreshTable()
        view.addButton.addActionListener {
            addStudent()
        }

        view.editButton.addActionListener {
            editStudent()
        }

        view.removeButton.addActionListener {
            // Логика удаления студента
            val selectedRows = view.table.selectedRows
            if (selectedRows.isNotEmpty()) {
                for (row in selectedRows) {
                    val studentId = view.table.getValueAt(row, 0) as Int
                    studentsList.removeStudent(studentId)
                }
                refreshTable()
            }
        }

        view.updateButton.addActionListener {
            updateStudent()
        }
    }

    fun refreshTable() {
        view.tableModel.setRowCount(0) // Удалить старые данные
        val start = (view.currentPage - 1) * view.pageSize
        val end = min(start + view.pageSize, studentsList.students.size)

        for (i in start until end) {
            val student = studentsList.students[i]
            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")
            view.tableModel.addRow(arrayOf(student.id, surnameIN, student.git, contact))
        }
    }



    fun editStudent() {
        // изменения студента
        val selectedRow = view.table.selectedRow
        println(selectedRow)
        if (selectedRow >= 0) {
            val studentId = view.table.getValueAt(selectedRow, 0) as Int
            println(studentId)
            val student = studentsList.students.find { it.id == studentId }
            val studentForm = Student_Form(student) // заполняем форму текущими данными
            if (studentForm.showDialog()) {
                // Обновить данные студента
                studentsList.repStudId(
                    studentId,
                    studentForm.surname ?: "",
                    studentForm.name ?: "",
                    studentForm.patronymic,
                    studentForm.phone,
                    studentForm.telegram,
                    studentForm.email,
                    studentForm.git
                )
                refreshTable()
            }
        }
    }

    fun removeStudent(studentId: Int) {
        // Логика удаления студента
        val selectedRows = view.table.selectedRows
        if (selectedRows.isNotEmpty()) {
            for (row in selectedRows) {
                val studentId = view.table.getValueAt(row, 0) as Int
                studentsList.removeStudent(studentId)
            }
            refreshTable()
        }
    }


    fun addStudent() {
        val studentForm = Student_Form() // создаем новую форму для добавления
        if (studentForm.showDialog()) {
            // Логика добавления нового студента
            studentsList.addStudent(
                studentForm.surname ?: "",
                studentForm.name ?: "",
                studentForm.patronymic,
                studentForm.phone,
                studentForm.telegram,
                studentForm.email,
                studentForm.git
            )
            refreshTable()
        }
    }

    fun  updateStudent() {
        //обновления (поиск) согласно установленным фильтрам
        val surnameFilter = view.surnameField.text.trim()
        var gitFilterSelected: String
        if (view.gitYes.isSelected) {
            gitFilterSelected = "Да"
        } else if (view.gitNo.isSelected) {
            gitFilterSelected = "Нет"
        } else if (view.gitDontCare.isSelected) {
            gitFilterSelected = "Не важно"
        } else {
            gitFilterSelected = "Не важно"
        }

        var emailFilterSelected: String
        if (view.emailYes.isSelected) {
            emailFilterSelected = "Да"
        } else if (view.emailNo.isSelected) {
            emailFilterSelected = "Нет"
        } else if (view.emailDontCare.isSelected) {
            emailFilterSelected = "Не важно"
        } else {
            emailFilterSelected = "Не важно"
        }

        var phoneFilterSelected: String
        if (view.phoneYes.isSelected) {
            phoneFilterSelected = "Да"
        } else if (view.phoneNo.isSelected) {
            phoneFilterSelected = "Нет"
        } else if (view.phoneDontCare.isSelected) {
            phoneFilterSelected = "Не важно"
        } else {
            phoneFilterSelected = "Не важно"
        }

        var telegramFilterSelected: String
        if (view.telegramYes.isSelected) {
            telegramFilterSelected = "Да"
        } else if (view.telegramNo.isSelected) {
            telegramFilterSelected = "Нет"
        } else if (view.telegramDontCare.isSelected) {
            telegramFilterSelected = "Не важно"
        } else {
            telegramFilterSelected = "Не важно"
        }

        // Получаем значение для поиска по Git, если радиокнопка "Да" выбрана
        val gitSearchValue = if (view.gitYes.isSelected) view.gitSearchField.text.trim() else ""

        val emailValue = if (view.emailYes.isSelected) view.emailField.text.trim() else ""

        val phoneValue = if (view.phoneYes.isSelected) view.phoneField.text.trim() else ""

        val telegramValue = if (view.telegramYes.isSelected) view.telegramField.text.trim() else ""

        // Создаем фильтр для отображения студентов
        val rowFilter = object : RowFilter<TableModel, Int>() {
            override fun include(entry: Entry<out TableModel, out Int>): Boolean {
                val studentSurname = entry.getValue(1).toString()// Индекс фамилии
                val studentGit = entry.getValue(2).toString() // Индекс Git
                val contactInfo = entry.getValue(3).toString() // Индекс строки Контакт
                val contactParts = contactInfo.split(", ")

                val studentPhone = contactParts.getOrNull(0) ?: "" // Номер телефона
                val studentTelegram = contactParts.getOrNull(1) ?: "" // Telegram
                val studentEmail = contactParts.getOrNull(2) ?: "" // Email

                // Проверяем соответствие ФИО
                if (surnameFilter.isNotEmpty()) {
                    val fioParts = surnameFilter.split(" ")
                    val studentFioParts = studentSurname.split(", ")

                    // Если введена полная Фамилия
                    val lastNameMatch = studentFioParts[0].startsWith(fioParts[0]) // Сравниваем только с Фамилией

                    // Проверка на совпадение с первой буквой имени
                    val firstNameMatch = if (fioParts.size > 1) {
                        studentFioParts[1].startsWith(fioParts[1].take(1))
                    } else {
                        true // Имя не проверяется, если нет второго слова
                    }

                    // Проверка на совпадение с первой буквой отчества
                    val patronymicMatch = if (fioParts.size > 2) {
                        studentFioParts[2].startsWith(fioParts[2].take(1))
                    } else {
                        true // Отчество не проверяется, если нет третьего слова
                    }

                    // Все проверки должны быть истинными для соответствия
                    if (!lastNameMatch || !firstNameMatch || !patronymicMatch) {
                        return false
                    }
                }

                // Применяем фильтр по Git
                if (gitFilterSelected == "Да" && !studentGit.contains(gitSearchValue)) return false
                if (gitFilterSelected == "Нет" && studentGit.isNotEmpty()) return false

                // Применяем фильтр по Email
                if (emailFilterSelected == "Да" && !studentEmail.contains(emailValue)) return false
                if (emailFilterSelected == "Нет" && studentEmail.isNotEmpty()) return false

                // Применяем фильтр по phone
                if (phoneFilterSelected == "Да" && !studentPhone.contains(phoneValue)) return false
                if (phoneFilterSelected == "Нет" && studentPhone.isNotEmpty()) return false

                // Применяем фильтр по telegram
                if (telegramFilterSelected == "Да" && !studentTelegram.contains(telegramValue)) return false
                if (telegramFilterSelected == "Нет" && studentTelegram.isNotEmpty()) return false

                return true // Если все проверки пройдены
            }
        }
    }
}