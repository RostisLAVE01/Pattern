class DataTable(private val data: List<List<Any>>) {

    fun GetNumCol() : Int
    {
        return data[0].size
    }

    fun GetNumRow() : Int
    {
        return data.size
    }

    fun GetElem(rowindex : Int,colindex : Int) : Any?
    {
        return data.getOrNull(rowindex)?.getOrNull(colindex)
    }

}