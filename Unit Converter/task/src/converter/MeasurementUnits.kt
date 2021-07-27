package converter

enum class MeasurementUnits(val contracted: String, val singular: String, val plural: String, val type: String, vararg val others: String) {
    METERS("m", "meter", "meters", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 1.0
        override fun fromNeutral(num: Double): Double = num / 1.0
    },
    KILOMETERS("km", "kilometer", "kilometers", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 1000.0
        override fun fromNeutral(num: Double): Double = num / 1000.0
    },
    CENTIMETERS("cm", "centimeter", "centimeters", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.01
        override fun fromNeutral(num: Double): Double = num / 0.01
    },
    MILLIMETERS("mm", "millimeter", "millimeters", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.001
        override fun fromNeutral(num: Double): Double = num / 0.001
    },
    MILE("mi", "mile", "miles", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 1609.35
        override fun fromNeutral(num: Double): Double = num / 1609.35
    },
    YARDS("yd", "yard", "yards", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.9144
        override fun fromNeutral(num: Double): Double = num / 0.9144
    },
    FEET("ft", "foot", "feet", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.3048
        override fun fromNeutral(num: Double): Double = num / 0.3048
    },
    INCHES("in", "inch", "inches", DISTANCE_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.0254
        override fun fromNeutral(num: Double): Double = num / 0.0254
    },
    GRAMS("g", "gram", "grams", WEIGHT_TYPE) {
        override fun toNeutral(num: Double): Double = num * 1.0
        override fun fromNeutral(num: Double): Double = num / 1.0
    },
    KILOGRAMS("kg", "kilogram", "kilograms", WEIGHT_TYPE) {
        override fun toNeutral(num: Double): Double = num * 1000.0
        override fun fromNeutral(num: Double): Double = num / 1000.0
    },
    MILLIGRAMS("mg", "milligram", "milligrams", WEIGHT_TYPE) {
        override fun toNeutral(num: Double): Double = num * 0.001
        override fun fromNeutral(num: Double): Double = num / 0.001
    },
    POUNDS("lb", "pound", "pounds", WEIGHT_TYPE) {
        override fun toNeutral(num: Double): Double = num * 453.592
        override fun fromNeutral(num: Double): Double = num / 453.592
    },
    OUNCES("oz", "ounce", "ounces", WEIGHT_TYPE) {
        override fun toNeutral(num: Double): Double = num * 28.3495
        override fun fromNeutral(num: Double): Double = num / 28.3495
    },
    DEGREES("c", "degree Celsius", "degrees Celsius", TEMP_TYPE, "dc", "celsius") {
        override fun toNeutral(num: Double): Double = num * 1.0
        override fun fromNeutral(num: Double): Double = num / 1.0
    },
    FAHRENHEIT("f", "degree Fahrenheit", "degrees Fahrenheit", TEMP_TYPE, "df", "fahrenheit") {
        override fun toNeutral(num: Double): Double = (num - 32) * 5/9
        override fun fromNeutral(num: Double): Double = num * 9/5 + 32
    },
    KELVINS("k", "kelvin", "kelvins", TEMP_TYPE) {
        override fun toNeutral(num: Double): Double = num - 273.15
        override fun fromNeutral(num: Double): Double = num + 273.15
    },
    NONE(NONE_VALUE, NONE_VALUE, NONE_VALUE, NONE_VALUE){
        override fun toNeutral(num: Double): Double = 0.0
        override fun fromNeutral(num: Double): Double = 0.0
    };

    fun acceptedParameters(): List<String> = mutableListOf(contracted, singular, plural).let { it.addAll(others); it  }.toList()
    fun resultPhrase(fromTo: Pair<MeasurementUnits, MeasurementUnits>, num: Number, result: Number): String {
        return if (num.toDouble() == SINGULAR_VALUE && result.toDouble() == SINGULAR_VALUE) {
            "$num ${fromTo.first.singular} is $result ${fromTo.second.singular}"
        } else if (num.toDouble() == SINGULAR_VALUE) {
            "$num ${fromTo.first.singular} is $result ${fromTo.second.plural}"
        } else if (result.toDouble() == SINGULAR_VALUE) {
            "$num ${fromTo.first.plural} is $result ${fromTo.second.singular}"
        } else {
            "$num ${fromTo.first.plural} is $result ${fromTo.second.plural}"
        }
    }

    abstract fun toNeutral(num: Double): Double
    abstract fun fromNeutral(num: Double): Double
}