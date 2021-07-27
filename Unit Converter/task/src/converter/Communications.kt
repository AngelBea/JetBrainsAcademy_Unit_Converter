package converter

import java.lang.Exception

class Communications {

    fun getInput() {
        var input = ""

        while (input.lowercase() != COMMAND_EXIT) {
            try {
                PROMPT_ENTER_DISTANCE.let(::println)

                input = readLine()!!.lowercase()

                val allValues =
                    MeasurementUnits.values().filter { it.singular != NONE_VALUE }
                        .joinToString("|") {
                            it.acceptedParameters().joinToString("|") { s -> "\\b${s.lowercase()}\\b" }
                        }

                val isFormattedRegex =
                    "(-|)(\\d*\\.\\d*|\\d*) (${allValues.plus("|\\w*\\s\\w*|\\w*")}) (\\w*) (${allValues.plus("|\\w*\\s\\w*|\\w*")})".toRegex()

                if (input != COMMAND_EXIT) {
                    if (!isFormattedRegex.matches(input)) throw Exception("Parse error")

                    val number = "(-|)(\\d*\\.\\d*|\\d*)".toRegex().findAll(input).toList()[0].value.toDouble()
                    val measures = "($allValues)".toRegex().findAll(input).map { it.value }.toList()

                    var fromMeasure = ""
                    var toMeasure = ""

                    val indexOfMeasure = "\\w*\\.\\w*|((degree|degrees) \\w*)|\\w*".toRegex()
                        .findAll(input).map { it.value }.toList().filter { it.isNotBlank() }

                    when (measures.size) {
                        3 -> {
                            fromMeasure = measures[0]
                            toMeasure = measures[2]
                        }
                        2 -> {
                            if(indexOfMeasure.indexOf(measures[0]) == 1) fromMeasure = measures[0]
                            if (indexOfMeasure.indexOf(measures[1]) == indexOfMeasure.lastIndex) toMeasure = measures[1]
                        }

                        1 -> {
                            if (indexOfMeasure.indexOf(measures[0]) == 1) fromMeasure = measures[0]
                            if (indexOfMeasure.indexOf(measures[0]) == indexOfMeasure.lastIndex) toMeasure = measures[0]
                        }
                    }


                    val converter = MeasurementConverter(fromMeasure, toMeasure)

                    number.let(converter::convert)

                    converter.printConvertResult()
                }
            } catch (exc: Exception) {
                exc.message.let(::println)
            }finally{
                println("")
            }
        }
    }
}

