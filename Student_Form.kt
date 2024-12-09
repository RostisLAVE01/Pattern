import javax.swing.*;
import java.awt.*;

class Student_Form(private val student: Student? = null) {
    var surname: String? = student?.surname
    var name: String? = student?.name
    var patronymic: String? = student?.patronymic
    var phone: String? = student?.phone
    var telegram: String? = student?.telegram
    var email: String? = student?.email
    var git: String? = student?.git

    fun showDialog(): Boolean {
        val dialog = JDialog()
        dialog.title = if (student != null) "Изменить студента" else "Добавить студента"
        dialog.setSize(400, 300)
        dialog.layout = GridBagLayout()
        val constraints = GridBagConstraints()

        // Метод для добавления компонентов с настройками
        fun addComponent(component: Component, gridX: Int, gridY: Int, width: Int, height: Int) {
            constraints.gridx = gridX
            constraints.gridy = gridY
            constraints.gridwidth = width
            constraints.gridheight = height
            constraints.fill = GridBagConstraints.HORIZONTAL
            constraints.weightx = 1.0
            constraints.insets = Insets(5, 5, 5, 5) // Отступы вокруг компонента
            dialog.add(component, constraints)
        }

        // Создание полей для ввода
        val surnameField = JTextField(surname)
        val nameField = JTextField(name)
        val patronymicField = JTextField(patronymic)
        val phoneField = JTextField(phone)
        val telegramField = JTextField(telegram)
        val emailField = JTextField(email)
        val gitField = JTextField(git)

        // Задаем предпочтительные размеры для полей
        val fieldSize = Dimension(200, 30)
        surnameField.preferredSize = fieldSize
        nameField.preferredSize = fieldSize
        patronymicField.preferredSize = fieldSize
        phoneField.preferredSize = fieldSize
        telegramField.preferredSize = fieldSize
        emailField.preferredSize = fieldSize
        gitField.preferredSize = fieldSize

        // Добавляем элементы на форму
        addComponent(JLabel("Фамилия:"), 0, 0, 1, 1)
        addComponent(surnameField, 1, 0, 2, 1)
        addComponent(JLabel("Имя:"), 0, 1, 1, 1)
        addComponent(nameField, 1, 1, 2, 1)
        addComponent(JLabel("Отчество:"), 0, 2, 1, 1)
        addComponent(patronymicField, 1, 2, 2, 1)
        addComponent(JLabel("Телефон:"), 0, 3, 1, 1)
        addComponent(phoneField, 1, 3, 2, 1)
        addComponent(JLabel("Telegram:"), 0, 4, 1, 1)
        addComponent(telegramField, 1, 4, 2, 1)
        addComponent(JLabel("Email:"), 0, 5, 1, 1)
        addComponent(emailField, 1, 5, 2, 1)
        addComponent(JLabel("Git:"), 0, 6, 1, 1)
        addComponent(gitField, 1, 6, 2, 1)

        // Кнопки
        val okButton = JButton("OK")
        val cancelButton = JButton("Отмена")

        okButton.addActionListener {
            surname = surnameField.text
            name = nameField.text
            patronymic = patronymicField.text
            phone = phoneField.text
            telegram = telegramField.text
            email = emailField.text
            git = gitField.text
            dialog.dispose() // Закрыть диалог
        }

        cancelButton.addActionListener { dialog.dispose() }

        addComponent(okButton, 1, 7, 1, 1)
        addComponent(cancelButton, 2, 7, 1, 1)

        // Показать диалог
        dialog.isModal = true
        dialog.isVisible = true
        return surname != null // Проверка на то, что форма была заполнена
    }
}