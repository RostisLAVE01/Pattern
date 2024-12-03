import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.awt.*

class Student_list_view(private val studentsList: Students_list_super) {

    private val frame = JFrame("Student List View")
    private val tabbedPane = JTabbedPane()
    private val tableModel = DefaultTableModel(arrayOf("ID", "ФИО", "Git", "Контакт"), 0)
    private val table = JTable(tableModel)

    private val tableModelAllStudents = DefaultTableModel(arrayOf("ID", "ФИО", "Git", "Контакт"), 0)
    private val tableAllStudents = JTable(tableModelAllStudents)

    init {
        createAndShowGUI()
    }

    private fun createAndShowGUI() {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 600)

        // Первое окно - Отображение всех студентов
        val allStudentsPanel = createAllStudentsTab()

        // Второе окно - Вкладка со списком студентов
        val studentPanel = createStudentTab()

        // Добавляем вкладки
        tabbedPane.addTab("Список студентов", allStudentsPanel)
        tabbedPane.addTab("Список студентов", studentPanel)
        tabbedPane.addTab("Вкладка 3", JPanel())

        frame.add(tabbedPane)
        frame.isVisible = true

        refreshTable() // Заполняем таблицу
    }

    private fun createAllStudentsTab(): JPanel {
        val panel = JPanel(BorderLayout())
        panel.add(JScrollPane(tableAllStudents), BorderLayout.CENTER)
        refreshAllStudentsTable() //все студенты
        return panel
    }



    private fun createStudentTab(): JPanel {
        val panel = JPanel(BorderLayout())

        // Область фильтрации
        val filterPanel = JPanel(GridLayout(5, 2))

        // Фамилия и инициалы
        val surnameField = JTextField()
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

        val gitSearchField = JTextField()
        gitSearchField.isEnabled = false

        gitYes.addActionListener { gitSearchField.isEnabled = true }
        gitNo.addActionListener { gitSearchField.isEnabled = false }
        gitDontCare.addActionListener { gitSearchField.isEnabled = false }

        filterPanel.add(JLabel("Наличие Git:"))
        filterPanel.add(gitYes)
        filterPanel.add(gitNo)
        filterPanel.add(gitDontCare)

        filterPanel.add(JLabel("Поиск по Git:"))
        filterPanel.add(gitSearchField)

        // Почта
        val emailField = JTextField()
        filterPanel.add(JLabel("Email:"))
        filterPanel.add(emailField)

        // Телефон
        val phoneField = JTextField()
        filterPanel.add(JLabel("Телефон:"))
        filterPanel.add(phoneField)

        // Telegram
        val telegramField = JTextField()
        filterPanel.add(JLabel("Telegram:"))
        filterPanel.add(telegramField)

        panel.add(filterPanel, BorderLayout.NORTH)

        // Таблица
        panel.add(JScrollPane(table), BorderLayout.CENTER)

        // Кнопки
        val controlPanel = JPanel()
        val addButton = JButton("Добавить")
        val removeButton = JButton("Удалить")

        addButton.addActionListener {
            // Добавления нового студента
            val surname = surnameField.text
            studentsList.addStudent(surname, "", null, null, null, null, null)
            refreshTable()
        }

        removeButton.addActionListener {
            // Удаления студента
            val selectedRow = table.selectedRow
            if (selectedRow >= 0) {
                val studentId = tableModel.getValueAt(selectedRow, 0) as Int
                studentsList.removeStudent(studentId)
                refreshTable()
            }
        }

        controlPanel.add(addButton)
        controlPanel.add(removeButton)
        panel.add(controlPanel, BorderLayout.SOUTH)

        return panel
    }

    private fun refreshTable() {
        tableModel.setRowCount(0) // Удалить старые данные
        for (student in studentsList.students) {
            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")
            tableModel.addRow(arrayOf(student.id, surnameIN, student.git, contact))
        }
    }


    private fun refreshAllStudentsTable() {
        tableModelAllStudents.setRowCount(0) // Удалить старые данные
        for (student in studentsList.students) {
            val contact = listOfNotNull(student.phone, student.telegram, student.email).joinToString(", ")
            val surnameIN = listOfNotNull(student.surname, student.name, student.patronymic).joinToString(", ")
            tableModelAllStudents.addRow(arrayOf(student.id, surnameIN, student.git, contact))
        }
    }
}