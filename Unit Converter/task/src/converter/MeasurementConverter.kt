package converter

import java.lang.Exception

class MeasurementConverter(from: String, to: String) {
    lateinit var lastNum: Number
    lateinit var result: Number
    var fromUnit: MeasurementUnits
        private set
    var toUnit: MeasurementUnits
        private set

    init {
        val fromU = MeasurementUnits.values().filter { from in it.acceptedParameters().map { parameter -> parameter.lowercase() } }
        val toU = MeasurementUnits.values().filter { to in it.acceptedParameters().map { parameter -> parameter.lowercase() } }

        fromUnit = if (fromU.size == 1) fromU[0] else MeasurementUnits.NONE
        toUnit = if (toU.size == 1) toU[0] else MeasurementUnits.NONE

        if (fromUnit.type != toUnit.type || fromUnit.type == NONE_VALUE && toUnit.type == NONE_VALUE) {
            throw Exception("Conversion from ${fromUnit.plural} to ${toUnit.plural} is impossible")
        }
    }

    fun convert(num: Number): Number {
        if (fromUnit.type == WEIGHT_TYPE && num.toDouble() < 0) throw Exception("Weight shouldn't be negative")
        if (fromUnit.type == DISTANCE_TYPE && num.toDouble() < 0) throw Exception("Length shouldn't be negative")
        
        lastNum = num
        result = toUnit.fromNeutral(fromUnit.toNeutral(num.toDouble()))

        return result
    }

    fun printConvertResult() = fromUnit.resultPhrase(fromUnit to toUnit, lastNum, result).let(::println)


}