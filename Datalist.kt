class Datalist(val data: Array<Any>)
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

    fun get_names(id:Int): Array<String>
    {
        var name = data[id]!!::class.java.declaredFields.map { it.name }.toTypedArray()
        name[0] = id.toString()
        return name
    }

    fun get_data(): DataTable
    {
        var attr = mutableListOf<List<Any>>()
        for (id in select)
        {
            var arg = mutableListOf<Any>(id)
            arg.add(data[id]!!::class.java.declaredFields.map { it }.toList())
            attr.add(arg)

        }
        return DataTable(attr)
    }

}