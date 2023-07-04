package alvarez.fernando.kotlincrud.extensions

import java.math.BigDecimal

fun BigDecimal.multiply(multiplicand: Long): BigDecimal {
    return this.multiply(BigDecimal.valueOf(multiplicand))
}

fun BigDecimal.multiply(multiplicand: Int): BigDecimal {
    return this.multiply(multiplicand.toLong())
}