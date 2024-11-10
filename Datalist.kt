open class Datalist<T>(val data: Array<T>)
{
    private val select = mutableListOf<Int>()

    fun select(num:Int)
    {
        if(num < data.size)
        {
            select.add(num)
        }
    }

    fun get_selected() : MutableList<Int>
    {
        return select
    }

    open fun get_names(id:Int): DataTable
    {
        throw NotImplementedError(" ")
    }

    open fun get_data(): DataTable
    {

        throw NotImplementedError(" ")
    }

}