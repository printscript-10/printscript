package interpreter

sealed class Variable {
    abstract val value: Any?
    abstract val isFinal: Boolean
}

data class StringVariable(override val value: String?, override val isFinal: Boolean) : Variable()

data class NumericVariable(override val value: Number?, override val isFinal: Boolean) : Variable() {
    operator fun plus(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) + (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result, isFinal)
    }

    operator fun minus(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) - (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result, isFinal)
    }

    operator fun times(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) * (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result, isFinal)
    }

    operator fun div(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) / (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result, isFinal)
    }
}
