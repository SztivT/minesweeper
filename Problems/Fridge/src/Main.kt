fun Fridge.take (productName: String): Product {
    open()
    val prod = find(productName)
    close()
    return prod
}