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

class View(private val studentsList: Students_list_super) {

    public val frame = JFrame("Student List View")
    public val tabbedPane = JTabbedPane()
    public val tableModel = NonEditableTableModel(arrayOf("ID", "ФИО", "Git", "Контакт"))
    public val table = JTable(tableModel)

    // Добавляем TableRowSorter для сортировки
    public val sorter = TableRowSorter(tableModel)

    // Пагинация
    public var currentPage = 1
    public val pageSize = 20
    public val totalPages get() = (studentsList.students.size + pageSize - 1) / pageSize

    // Кнопки
    public val updateButton = JButton("Обновить")
    public val editButton = JButton("Изменить")
    public val removeButton = JButton("Удалить")
    public val addButton = JButton("Добавить")
    public val emailField = JTextField(20)
    public val phoneField = JTextField(20)
    public val telegramField = JTextField(20)
    public val surnameField = JTextField(20) // Установка ширины поля
    val gitSearchField = JTextField(20)
    val gitFilterGroup = ButtonGroup()
    val gitYes = JRadioButton("Да")
    val gitNo = JRadioButton("Нет")
    val gitDontCare = JRadioButton("Не важно")
    val emailYes = JRadioButton("Да")
    val emailNo = JRadioButton("Нет")
    val emailDontCare = JRadioButton("Не важно")
    val phoneYes = JRadioButton("Да")
    val phoneNo = JRadioButton("Нет")
    val phoneDontCare = JRadioButton("Не важно")
    val telegramYes = JRadioButton("Да")
    val telegramNo = JRadioButton("Нет")
    val telegramDontCare = JRadioButton("Не важно")



    init {
        createAndShowGUI()
    }

    private fun createAndShowGUI() {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(1000, 600)

        // Устанавливаем TableRowSorter для таблиц
        table.rowSorter = sorter

        //Вкладка со списком студентов
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
        filterPanel.add(JLabel("Фамилия и инициалы:"))
        filterPanel.add(surnameField)

        // Git фильтр
        gitFilterGroup.add(gitYes)
        gitFilterGroup.add(gitNo)
        gitFilterGroup.add(gitDontCare)

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

        val emailFilterGroup = ButtonGroup()
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
        }

        editButton.addActionListener {

        }

        removeButton.addActionListener {

        }

        updateButton.addActionListener {

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

    }

    private fun updateButtonState() {
        val selectedRows = table.selectedRows
        addButton.isEnabled = true
        editButton.isEnabled = selectedRows.size == 1
        removeButton.isEnabled = selectedRows.isNotEmpty()
        updateButton.isEnabled = true // Всегда доступен
    }
}

//class NonEditableTableModel(columnNames: Array<String>) : DefaultTableModel(columnNames, 0) {
//    override fun isCellEditable(row: Int, column: Int): Boolean {
//        return false // Запрет на редактирование всех ячеек
//    }
//}