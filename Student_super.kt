open class Student_super(
    id: Int,
    git: String? = null,
)
{

    var id: Int = id
        get() {
            return field
        }
        set(value) {
            require(value > 0)
            {
                "ID должен быть больше 0"
            }
            field = value
        }

    var git: String? = git
        get() {
            return field
        }
        set(value) {
            field = value
        }
}