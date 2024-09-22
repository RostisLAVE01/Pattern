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
        hashStud["phone"]?.toString(),
        hashStud["telegram"]?.toString(),
        hashStud["mail"]?.toString(),
        hashStud["git"]?.toString()
    )



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