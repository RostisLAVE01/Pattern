import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.awt.*
import javax.swing.table.TableRowSorter
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import javax.swing.table.TableModel
import kotlin.math.min

class Student_list_view(private val studentsList: Students_list_super) {

    private val frame = JFrame("Student List View")
    private val tabbedPane = JTabbedPane()
    private val tableModel = NonEditableTableModel(arrayOf("ID", "ФИО", "Git", "Контакт"))
    private val table = JTable(tableModel)

    // Добавляем TableRowSorter для сортировки
    private val sorter = TableRowSorter(tableModel)

    // Пагинация
    private var currentPage = 1
    private val pageSize = 20
    private val totalPages get() = (studentsList.students.size + pageSize - 1) / pageSize

    // Кнопки
    private val updateButton = JButton("Обновить")
    private val editButton = JButton("Изменить")
    private val removeButton = JButton("Удалить")
    private val addButton = JButton("Добавить")


    init {
        createAndShowGUI()
    }

    private fun createAndShowGUI() {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1000, 600)

        // Устанавливаем TableRowSorter для таблиц
        table.rowSorter = sorter

        // Второе окно - Вкладка со списком студентов
        val studentPanel = createStudentTab()

        tabbedPane.addTab("Список студентов", studentPanel)

        frame.add(tabbedPane)
        frame.isVisible = true

        refreshTable() // Заполняем таблицу

        // Листенеры для выбора строк
        table.selectionModel.addListSelectionListener(ListSelectionListener { e: ListSelectionEvent ->
            if (!e.valueIsAdjusting) updateButtonState()
        })
    }

    private fun createStudentTab(): JPanel {
        val panel = JPanel(BorderLayout())
        val constraints = GridBagConstraints()

        // Область фильтрации
        val filterPanel = JPanel(GridBagLayout())

        // Метод для добавления компонентов с настройками
        fun addComp(component: Component, gridX: Int, gridY: Int) {
            constraints.gridx = gridX
            constraints.gridy = gridY
            constraints.fill = GridBagConstraints.HORIZONTAL
            constraints.weightx = 1.0
            constraints.insets = Insets(5, 5, 5, 5) // Отступы вокруг компонента
            filterPanel.add(component, constraints)
        }

        // Фамилия и инициалы
        val surnameField = JTextField(20) // Установка ширины поля
        filterPanel.add(JLabel("Фамилия и инициалы:"))
        filterPanel.add(surnameField)

        // Git фильтр
        val gitFilterGroup = ButtonGroup()
        val gitYes = JRadioButton("Да")
        val gitNo = JRadioButton("Нет")
        val gitDontCare = JRadioButton("Не важно")
        gitFilterGroup.add(gitYes)
        gitFilterGroup.add(gitNo)
        gitFilterGroup.add(gitDontCare)

        val gitSearchField = JTextField(20)
        gitSearchField.isEnabled = false

        gitYes.addActionListener { gitSearchField.isEnabled = true }
        gitNo.addActionListener { gitSearchField.isEnabled = false }
        gitDontCare.addActionListener { gitSearchField.isEnabled = false }

        val gitPan = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS) // Вертикальное расположение
            add(gitYes)
            add(gitNo)
            add(gitDontCare)
        }

        addComp(JLabel("Наличие Git:"), 0, 1)
        addComp(gitPan, 1, 1)

        addComp(JLabel("Поиск по Git:"), 0, 2)
        addComp(gitSearchField, 1, 2)

        // Почта
        val emailField = JTextField(20)

        val emailFilterGroup = ButtonGroup()
        val emailYes = JRadioButton("Да")
        val emailNo = JRadioButton("Нет")
        val emailDontCare = JRadioButton("Не важно")
        emailFilterGroup.add(emailYes)
        emailFilterGroup.add(emailNo)
        emailFilterGroup.add(emailDontCare)

        emailYes.addActionListener { emailField.isEnabled = true }
        emailNo.addActionListener { emailField.isEnabled = false }
        emailDontCare.addActionListener { emailField.isEnabled = false }

        val emailPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)

            add(emailYes)
            add(emailNo)
            add(emailDontCare)
        }

        addComp(JLabel("Наличие Email:"), 0, 3)
        addComp(emailPanel, 1, 3)
        addComp(JLabel("Email:"), 0, 4)
        addComp(emailField, 1, 4)

        // Телефон
        val phoneField = JTextField(20)
        val phoneYes = JRadioButton("Да")
        val phoneNo = JRadioButton("Нет")
        val phoneDontCare = JRadioButton("Не важно")
        val phoneFilterGroup = ButtonGroup()
        phoneFilterGroup.add(phoneYes)
        phoneFilterGroup.add(phoneNo)
        phoneFilterGroup.add(phoneDontCare)

        phoneYes.addActionListener { phoneField.isEnabled = true }
        phoneNo.addActionListener { phoneField.isEnabled = false }
        phoneDontCare.addActionListener { phoneField.isEnabled = false }

        val phonePanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)

            add(phoneYes)
            add(phoneNo)
            add(phoneDontCare)
        }

        addComp(JLabel("Наличие Телефон:"), 0, 5)
        addComp(phonePanel, 1, 5)
        addComp(JLabel("Телефон:"), 0, 6)
        addComp(phoneField, 1, 6)

        // Telegram
        val telegramField = JTextField(20)

        val telegramYes = JRadioButton("Да")
        val telegramNo = JRadioButton("Нет")
        val telegramDontCare = JRadioButton("Не важно")
        val telegramFilterGroup = ButtonGroup()
        telegramFilterGroup.add(telegramYes)
        telegramFilterGroup.add(telegramNo)
        telegramFilterGroup.add(telegramDontCare)

        telegramYes.addActionListener { telegramField.isEnabled = true }
        telegramNo.addActionListener { telegramField.isEnabled = false }
        telegramDontCare.addActionListener { telegramField.isEnabled = false }

        val telegramPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)

            add(telegramYes)
            add(telegramNo)
            add(telegramDontCare)
        }

        addComp(JLabel("Наличие Telegram:"), 0, 7)
        addComp(telegramPanel, 1, 7)
        addComp(JLabel("Telegram:"), 0, 8)
        addComp(telegramField, 1, 8)

        panel.add(filterPanel, BorderLayout.EAST)

        panel.add(JScrollPane(table), BorderLayout.CENTER)

        // Область управления кнопками
        val controlPanel = JPanel()
        addButton.addActionListener {
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

        editButton.addActionListener {
            // Логика изменения студента
            val selectedRow = table.selectedRow
            println(selectedRow)
            if (selectedRow >= 0) {
                val studentId = table.getValueAt(selectedRow, 0) as Int
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

        removeButton.addActionListener {
            // Логика удаления студента
            val selectedRows = table.selectedRows
            if (selectedRows.isNotEmpty()) {
                for (row in selectedRows) {
                    val studentId = table.getValueAt(row, 0) as Int
                    studentsList.removeStudent(studentId)
                }
                refreshTable()
            }
        }

        updateButton.addActionListener {
            // Логика обновления (поиск) согласно установленным фильтрам
            val surnameFilter = surnameField.text.trim()
            var gitFilterSelected: String
            if (gitYes.isSelected) {
                gitFilterSelected = "Да"
            } else if (gitNo.isSelected) {
                gitFilterSelected = "Нет"
            } else if (gitDontCare.isSelected) {
                gitFilterSelected = "Не важно"
            } else {
                gitFilterSelected = "Не важно"
            }

            var emailFilterSelected: String
            if (emailYes.isSelected) {
                emailFilterSelected = "Да"
            } else if (emailNo.isSelected) {
                emailFilterSelected = "Нет"
            } else if (emailDontCare.isSelected) {
                emailFilterSelected = "Не важно"
            } else {
                emailFilterSelected = "Не важно"
            }

            var phoneFilterSelected: String
            if (phoneYes.isSelected) {
                phoneFilterSelected = "Да"
            } else if (phoneNo.isSelected) {
                phoneFilterSelected = "Нет"
            } else if (phoneDontCare.isSelected) {
                phoneFilterSelected = "Не важно"
            } else {
                phoneFilterSelected = "Не важно"
            }

            var telegramFilterSelected: String
            if (telegramYes.isSelected) {
                telegramFilterSelected = "Да"
            } else if (telegramNo.isSelected) {
                telegramFilterSelected = "Нет"
            } else if (telegramDontCare.isSelected) {
                telegramFilterSelected = "Не важно"
            } else {
                telegramFilterSelected = "Не важно"
            }

            // Получаем значение для поиска по Git, если радиокнопка "Да" выбрана
            val gitSearchValue = if (gitYes.isSelected) gitSearchField.text.trim() else ""

            val emailValue = if (emailYes.isSelected) emailField.text.trim() else ""

            val phoneValue = if (phoneYes.isSelected) phoneField.text.trim() else ""

            val telegramValue = if (telegramYes.isSelected) telegramField.text.trim() else ""

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

            // Применяем фильтр к сортировщику таблицы
            sorter.setRowFilter(rowFilter)
            refreshTable() // Обновляем таблицу
        }

        controlPanel.add(addButton)
        controlPanel.add(editButton)
        controlPanel.add(removeButton)
        controlPanel.add(updateButton)

        panel.add(controlPanel, BorderLayout.NORTH)

        // Пагинация
        val paginationPanel = JPanel()
        val previousButton = JButton("Предыдущая")
        val nextButton = JButton("Следующая")
        val pageLabel = JLabel("Страница $currentPage из $totalPages")

        previousButton.addActionListener {
            if (currentPage > 1) {
                currentPage--
                refreshTable()
                pageLabel.text = "Страница $currentPage из $totalPages"
            }
        }

        nextButton.addActionListener {
            if (currentPage < totalPages) {
                currentPage++
                refreshTable()
                pageLabel.text = "Страница $currentPage из $totalPages"
            }
        }

        paginationPanel.add(previousButton)
        paginationPanel.add(nextButton)
        paginationPanel.add(pageLabel)

        panel.add(paginationPanel, BorderLayout.PAGE_END)

        return panel
    }

    private fun refreshTable() {
        tableModel.setRowCount(0) // Удалить старые данные
        val start = (currentPage - 1) * pageSize
        val end = min(start + pageSize, studentsList.students.size)

        for (i in start until end) {
            val student = studentsList.students[i]
            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")
            tableModel.addRow(arrayOf(student.id, surnameIN, student.git, contact))
        }
    }

    private fun updateButtonState() {
        val selectedRows = table.selectedRows
        addButton.isEnabled = true
        editButton.isEnabled = selectedRows.size == 1
        removeButton.isEnabled = selectedRows.isNotEmpty()
        updateButton.isEnabled = true // Всегда доступен
    }

}

class NonEditableTableModel(columnNames: Array<String>) : DefaultTableModel(columnNames, 0) {
    override fun isCellEditable(row: Int, column: Int): Boolean {
        return false // Запрет на редактирование всех ячеек
    }
}