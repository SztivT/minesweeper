class Fabric(var color: String = "grey") {
    var pattern: String = "none"
    var patternColor: String = "none"

    init {
        println("$color fabric is created")
    }

    constructor(_color: String, _pattern: String, _patterncolor: String) : this() {
        this.color = _color
        this.pattern = _pattern
        this.patternColor = _patterncolor
        println("the fabric is dyed $color")
        println("$patternColor $pattern pattern is printed on the fabric")
    }
}