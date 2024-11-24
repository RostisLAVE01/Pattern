interface StudentStrategy {
    var students:MutableList<Student>
    fun readFromFile(path: String)
    fun writeToFile(path: String)
}

class Student_Manager(private var strategy: StudentStrategy): Students_list_super() {
    fun setStrategy(strategy: StudentStrategy) {
        this.strategy = strategy
    }

    fun readFromFile(path: String) {
        strategy.readFromFile(path)
        strategy.students = this.students
    }

    fun writeToFile(path: String) {

        strategy.students = this.students
        strategy.writeToFile(path)
    }
}