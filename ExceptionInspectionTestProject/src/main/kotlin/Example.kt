class Example {

    fun fun0() {
        throw UnsupportedOperationException()
    }

    class Nested {
        fun fun1() {
            throw IndexOutOfBoundsException()
        }
    }

    private fun fun2() {
        throw CustomException()
    }

    private val list = listOf("a", "b", "c")
    fun fun3() {
        list.forEach { l ->
            println(l)
            throw CustomException()
        }
    }

}

fun fun4() {
    throw IndexOutOfBoundsException()
}
