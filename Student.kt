class Student(
    private val id: Int,
    private var surname: String,
    private var name: String,
    private var patronymic: String? = null, //То что разрешено оставить незаполненым
    private var phone: String? = null,
    private var telegram: String? = null,
    private var email: String? = null,
    private var git: String? = null
) {

    // Первичный конструктор, который принимает обязательные поля
    init {
        require(surname.isNotBlank()) { "Фамилия не может быть пустой" }
        require(name.isNotBlank()) { "Имя не может быть пустым" }
    }

    // Вторичный конструктор, который принимает необязательные поля
    constructor(
        id: Int,
        surname: String,
        name: String,
        phone: String? = null,
        telegram: String? = null,
        email: String? = null,
        git: String? = null
    ) : this(id, surname, name, null, phone, telegram, email, git)

    // Вторичный конструктор, который принимает HashMap
    constructor(hashStud: HashMap<String, Any?>) : this(
        (hashStud["id"] as? Int) ?: throw IllegalArgumentException("ID обязателен"),
        (hashStud["lastname"] as? String) ?: throw IllegalArgumentException("Фамилия обязательна"),
        (hashStud["name"] as? String) ?: throw IllegalArgumentException("Имя обязательно"),
        hashStud["fathername"] as? String,
        (hashStud["phone"] as? String)?.also { checkPhone(it) }, // Проверка телефона
        hashStud["telegram"]?.toString(),
        hashStud["mail"]?.toString(),
        hashStud["git"]?.toString()
    )

    companion object {
        fun ValidPhone(phone: String?): Boolean {
            return phone?.matches(Regex("^\\+\\d{1,3}(-\\d{3}){2}-\\d{2}-\\d{2}\$")) ?: false
        }

        // Метод проверки имени пользователя Telegram
        fun ValidTelegram(telegram: String?): Boolean {
            return telegram?.matches(Regex("^[a-zA-Z0-9_]{5,32}\$")) ?: false
        }

        // Метод проверки формата электронной почты
        fun ValidMail(mail: String?): Boolean {
            return mail?.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")) ?: false
        }

        // Метод проверки формата Git URL
        fun ValidGit(git: String?): Boolean {
            return git?.matches(Regex("^[a-zA-Z0-9-_]+/[a-zA-Z0-9-_]+$")) ?: false
        }


        // Метод проверки номера телефона с выбрасыванием исключения
        internal fun checkPhone(phone: String?) {
            if (!ValidPhone(phone)) {
                throw IllegalArgumentException("Неправильный формат телефонного номера")
            }
        }

        // Метод проверки Telegram с выбрасыванием исключения
        internal fun checkTelegram(telegram: String?) {
            if (!ValidTelegram(telegram)) {
                throw IllegalArgumentException("Неправильный формат Telegram ")
            }
        }

        // Метод проверки электронной почты с выбрасыванием исключения
        internal fun checkMail(mail: String?) {
            if (!ValidMail(mail)) {
                throw IllegalArgumentException("Неправильный формат электронной почты")
            }
        }

        // Метод проверки Git с выбрасыванием исключения
        internal fun checkGit(git: String?) {
            if (!ValidGit(git)) {
                throw IllegalArgumentException("Неправильный формат Git")
            }
        }
    }

    // Метод для проверки наличия Git
    private fun validateGit(): Boolean {
        return !git.isNullOrEmpty()
    }

    // Метод для проверки наличия любого контакта
    private fun validateContact(): Boolean {
        return !phone.isNullOrEmpty() || !telegram.isNullOrEmpty() || !email.isNullOrEmpty()
    }

    // Основной метод валидации
    fun validate(): Boolean {
        return validateGit() && validateContact()
    }

    // Метод для установки значений контактов
    fun setContacts(phone: String? = null, telegram: String? = null, mail: String? = null) {
        this.phone = phone
        this.telegram = telegram
        this.email = mail
    }


    fun getId(): Int
    {
        return id
    }

    fun getSurname(): String
    {
        return surname
    }

    fun setSurname(value: String)
    {
        surname = value
    }

    fun getName(): String
    {
        return name
    }

    fun setName(value: String)
    {
        name = value
    }

    fun getPatronymic(): String?
    {
        return patronymic
    }

    fun setPatronymic(value: String?)
    {
        patronymic = value
    }

    fun getPhone(): String?
    {
        return phone
    }

    fun setPhone(value: String?)
    {
        phone = value
    }

    fun getTelegram(): String?
    {
        return telegram
    }

    fun setTelegram(value: String?)
    {
        telegram = value
    }

    fun getEmail(): String?
    {
        return email
    }

    fun setEmail(value: String?)
    {
        email = value
    }

    fun getGit(): String?
    {
        return git
    }

    fun setGit(value: String?)
    {
        git = value
    }

    override fun toString(): String {
        return "Студент(ID: $id, Фамилия: '$surname', Имя: '$name', Отчество: '${patronymic ?: "Нет"}', " +
                "Телефон: '${phone ?: "Нет"}', Телеграм: '${telegram ?: "Нет"}', " +
                "Почта: '${email ?: "Нет"}', Гит: '${git ?: "Нет"}')"
    }
}