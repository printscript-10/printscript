package interpreter

sealed class Variable {
    abstract val value: Any?
}

data class StringVariable(override val value: String?) : Variable()

data class NumericVariable(override val value: Number?) : Variable() {
    operator fun plus(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) + (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result)
    }

    operator fun minus(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) - (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result)
    }

    operator fun times(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) * (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result)
    }

    operator fun div(other: NumericVariable): NumericVariable {
        val result = (this.value?.toDouble() ?: 0.0) / (other.value?.toDouble() ?: 0.0)
        return NumericVariable(if (result % 1 == 0.0) result.toInt() else result)
    }
}
