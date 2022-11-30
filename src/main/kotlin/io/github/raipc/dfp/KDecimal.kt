package io.github.raipc.dfp

import com.epam.deltix.dfp.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class KDecimal private constructor(internal val value: Long) : Comparable<KDecimal> {
    /**
     * Convert to fixed-point representation: (123.4567, 2) -&gt; 12346
     *
     * @param numberOfDigits number of decimal digits representing fractional part
     * @return fixed-point decimal value represented as @{code long}
     */
    fun toFixedPoint(numberOfDigits: Int): Long = Decimal64Utils.toFixedPoint(value, numberOfDigits)

    /**
     * Convert `KDecimal` instance to `long` integer value by truncating fractional part towards zero
     *
     * @return `long` integer value
     */
    fun toLong(): Long = Decimal64Utils.toLong(value)

    /**
     * Convert `KDecimal` instance to `int` value by truncating fractional part towards zero
     *
     * @return `int` value
     */
    fun toInt(): Int = Decimal64Utils.toInt(value)

    /**
     * Convert `KDecimal` instance to 64-bit binary floating point (`double`) value.
     *
     * Note that not all decimal FP values can be exactly represented as binary FP values.
     *
     * @return `double` value
     */
    fun toDouble(): Double = Decimal64Utils.toDouble(value)

    /**
     * Convert `KDecimal` instance to `BigDecimal` binary floating point value.
     *
     * Note that not all decimal FP values can be exactly represented as binary FP values.
     *
     * @return `BigDecimal` value
     */
    fun toBigDecimal(): BigDecimal = Decimal64Utils.toBigDecimal(value)

    /**
     * Check, if this `KDecimal` instance holds a Not-a-Number value.
     *
     * @return `true`, if the value is NaN
     */
    fun isNaN(): Boolean = Decimal64Utils.isNaN(value)

    /**
     * Check, if this `KDecimal` instance holds is a positive or negative infinity.
     *
     * @return `true`, if the value is an infinity
     */
    fun isInfinity(): Boolean = Decimal64Utils.isInfinity(value)

    /**
     * Check, if this `KDecimal` instance holds a Positive Infinity value.
     *
     * @return `true`, if Positive Infinity
     */
    fun isPositiveInfinity(): Boolean = Decimal64Utils.isPositiveInfinity(value)

    /**
     * Check, if this `KDecimal` instance holds a Negative Infinity value.
     *
     * @return `true`, if Negative Infinity
     */
    fun isNegativeInfinity(): Boolean = Decimal64Utils.isNegativeInfinity(value)

    /**
     * Check, if this `KDecimal` instance holds a finite value(Not infinity or NaN).
     *
     * @return `true`, if finite. `false` if Infinity or NaN.
     */
    fun isFinite(): Boolean = Decimal64Utils.isFinite(value)

    /**
     * Check, if the value held by this `KDecimal` instance is normalized.
     *
     * @return `true`, if normalized.
     */
    fun isNormal(): Boolean = Decimal64Utils.isNormal(value)

    /**
     * Returns `true` if the value equals to null reference of type [KDecimal]
     * or corresponds special `NULL` constant.
     *
     * @param value the DFP value being checked
     * @return `true`, if `NULL`
     * `false` otherwise
     */
    fun isNull(): Boolean = Decimal64Utils.isNull(value)

    /**
     * Check, if two `KDecimal` instances hold the same underlying value.
     *
     *
     * This method returns `true` if and only if the underlying values of both `KDecimal` instances are the same.
     * This means that `KDecimal.NaN.isIdentical(KDecimal.NaN)` evaluates to `true`,
     * while at the same time comparing two different representations of equal real values will cause this
     * method to return `false`.
     * E.g. various representations of 0 are not considered same.
     *
     *
     * `isIdentical(x, y) => equals(x, y)`
     *
     * @param a the first `KDecimal` instance.
     * @param b the second `KDecimal` instance.
     * @return `true` if the binary representations of a and b are equal;
     * `false` otherwise.
     */
    fun isIdenticalTo(decimal: KDecimal): Boolean {
        return value == decimal.value
    }

    fun isZero(): Boolean = Decimal64Utils.isZero(value)

    fun isNonZero(): Boolean = Decimal64Utils.isNonZero(value)

    fun isPositive(): Boolean = Decimal64Utils.isPositive(value)

    fun isNegative(): Boolean = Decimal64Utils.isNegative(value)

    fun isNonPositive(): Boolean = Decimal64Utils.isNonPositive(value)

    fun isNonNegative(): Boolean = Decimal64Utils.isNonNegative(value)

    operator fun unaryMinus() = negate()

    fun negate(): KDecimal = KDecimal(Decimal64Utils.negate(value))

    fun abs(): KDecimal = KDecimal(Decimal64Utils.abs(value))

    operator fun plus(other: KDecimal) : KDecimal = add(other)

    fun add(other: KDecimal): KDecimal = KDecimal(Decimal64Utils.add(value, other.value))

    operator fun minus(other: KDecimal) : KDecimal = subtract(other)

    fun subtract(other: KDecimal): KDecimal = KDecimal(Decimal64Utils.subtract(value, other.value))

    operator fun times(other: KDecimal): KDecimal = multiply(other)

    operator fun times(other: Int): KDecimal = multiplyByInteger(other)

    operator fun times(other: Long): KDecimal = multiplyByInteger(other)

    fun multiply(other: KDecimal): KDecimal = KDecimal(Decimal64Utils.multiply(value, other.value))

    fun multiplyByInteger(other: Int): KDecimal = KDecimal(Decimal64Utils.multiplyByInteger(this.value, other))

    fun multiplyByInteger(other: Long): KDecimal = KDecimal(Decimal64Utils.multiplyByInteger(this.value, other))

    operator fun div(other: KDecimal): KDecimal = divide(other)

    operator fun div(other: Int): KDecimal = divideByInteger(other)

    operator fun div(other: Long): KDecimal = divideByInteger(other)

    fun divide(other: KDecimal): KDecimal = KDecimal(Decimal64Utils.divide(value, other.value))

    fun divideByInteger(value: Int): KDecimal = KDecimal(Decimal64Utils.divideByInteger(this.value, value))

    fun divideByInteger(value: Long): KDecimal = KDecimal(Decimal64Utils.divideByInteger(this.value, value))

    fun multiplyAndAdd(m: KDecimal, a: KDecimal): KDecimal =
        KDecimal(Decimal64Utils.multiplyAndAdd(value, m.value, a.value))

    fun scaleByPowerOfTen(n: Int): KDecimal = KDecimal(Decimal64Utils.scaleByPowerOfTen(value, n))

    fun average(other: KDecimal): KDecimal = KDecimal(Decimal64Utils.average(value, other.value))

    fun roundToReciprocal(r: Int, roundType: RoundingMode?): KDecimal =
        KDecimal(Decimal64Utils.roundToReciprocal(value, r, roundType))

    fun isRoundedToReciprocal(r: Int): Boolean = Decimal64Utils.isRoundedToReciprocal(value, r)

    fun round(n: Int, roundType: RoundingMode?): KDecimal = KDecimal(Decimal64Utils.round(value, n, roundType))

    fun isRounded(n: Int): Boolean = Decimal64Utils.isRounded(value, n)

    fun round(): KDecimal = KDecimal(Decimal64Utils.round(value))

    fun round(multiple: KDecimal): KDecimal = KDecimal(Decimal64Utils.round(value, multiple.value))

    fun ceiling(): KDecimal = KDecimal(Decimal64Utils.ceiling(value))

    fun roundTowardsPositiveInfinity(): KDecimal = KDecimal(Decimal64Utils.roundTowardsPositiveInfinity(value))

    fun floor(): KDecimal = KDecimal(Decimal64Utils.floor(value))

    fun roundTowardsNegativeInfinity(): KDecimal = KDecimal(Decimal64Utils.roundTowardsNegativeInfinity(value))

    fun truncate(): KDecimal = KDecimal(Decimal64Utils.truncate(value))

    fun roundTowardsZero(): KDecimal = KDecimal(Decimal64Utils.roundTowardsZero(value))

    fun roundToNearestTiesAwayFromZero(): KDecimal = KDecimal(Decimal64Utils.roundToNearestTiesAwayFromZero(value))

    fun roundToNearestTiesToEven(): KDecimal = KDecimal(Decimal64Utils.roundToNearestTiesToEven(value))

    fun roundTowardsPositiveInfinity(multiple: KDecimal): KDecimal =
        KDecimal(Decimal64Utils.roundTowardsPositiveInfinity(value, multiple.value))

    fun roundTowardsNegativeInfinity(multiple: KDecimal): KDecimal =
        KDecimal(Decimal64Utils.roundTowardsNegativeInfinity(value, multiple.value))

    fun roundToNearestTiesAwayFromZero(multiple: KDecimal): KDecimal =
        KDecimal(Decimal64Utils.roundToNearestTiesAwayFromZero(value, multiple.value))

    fun roundToNearestTiesToEven(multiple: KDecimal): KDecimal =
        KDecimal(Decimal64Utils.roundToNearestTiesToEven(value, multiple.value))

    /**
     * Returns the unscaled value of the `DFP` in the same way as [BigDecimal.unscaledValue] do.
     * For abnormal values return `Long.MIN_VALUE`.
     *
     * @return the unscaled value of `DFP` value.
     */
    fun getUnscaledValue(): Long = Decimal64Utils.getUnscaledValue(value)

    /**
     * Returns the unscaled value of the `DFP` in the same way as [BigDecimal.unscaledValue] do.
     *
     * @param abnormalReturn The value returned for abnormal values (NaN, +Inf, -Inf).
     * @return the unscaled value of `DFP` value.
     */
    fun getUnscaledValue(abnormalReturn: Long): Long = Decimal64Utils.getUnscaledValue(value, abnormalReturn)

    /**
     * Returns the scale of the `DFP` in the same way as [BigDecimal.scale] do.
     * For abnormal values return `Integer.MIN_VALUE`.
     *
     * @return the scale of `DFP` value.
     */
    fun getScale(): Int = Decimal64Utils.getScale(value)

    /**
     * Returns the scale of the `DFP` in the same way as [BigDecimal.scale] do.
     *
     * @param abnormalReturn The value returned for abnormal values (NaN, +Inf, -Inf).
     * @return the scale of `DFP` value.
     */
    fun getScale(abnormalReturn: Int): Int = Decimal64Utils.getScale(value, abnormalReturn)

    fun nextUp(): KDecimal = KDecimal(Decimal64Utils.nextUp(value))

    fun nextDown(): KDecimal = KDecimal(Decimal64Utils.nextDown(value))

    /**
     * Returns canonical representation of s `KDecimal` value.
     *
     *
     * We consider that all binary representations of one arithmetical value have the same canonical binary representation.
     * Canonical representation of zeros = [ZERO][.ZERO]
     * Canonical representation of NaNs = [NaN][.NaN]
     * Canonical representation of POSITIVE_INFINITYs = [POSITIVE_INFINITY][.POSITIVE_INFINITY]
     * Canonical representation of NEGATIVE_INFINITYs = [NEGATIVE_INFINITY][.NEGATIVE_INFINITY]
     *
     * @return Canonical representation of decimal.
     */
    fun canonize(): KDecimal = KDecimal(Decimal64Utils.canonize(value))

    override fun toString(): String = Decimal64Utils.toString(value)

    fun toScientificString(): String = Decimal64Utils.toScientificString(value)

    @Throws(IOException::class)
    fun appendTo(appendable: Appendable): Appendable = Decimal64Utils.appendTo(value, appendable)

    @Throws(IOException::class)
    fun scientificAppendTo(appendable: Appendable): Appendable = Decimal64Utils.scientificAppendTo(value, appendable)

    fun appendTo(builder: StringBuilder): StringBuilder = Decimal64Utils.appendTo(value, builder)

    fun scientificAppendTo(builder: StringBuilder): StringBuilder = Decimal64Utils.scientificAppendTo(value, builder)

    /**
     * Hash code of binary representation of given decimal.
     *
     * @return HashCode of given decimal.
     */
    fun identityHashCode(): Int = Decimal64Utils.identityHashCode(value)

    override fun compareTo(other: KDecimal): Int = Decimal64Utils.compareTo(value, other.value)

    companion object {
        /**
         * A constant holding canonical representation of Not-a-Number DFP value (not signaling NaN)
         */
        val NaN: KDecimal = KDecimal(Decimal64Utils.NaN)

        /**
         * A constant holding canonical representation of Positive Infinity value
         */
        val POSITIVE_INFINITY: KDecimal = KDecimal(Decimal64Utils.POSITIVE_INFINITY)

        /**
         * A constant holding canonical representation of Negative Infinity value
         */
        val NEGATIVE_INFINITY: KDecimal = KDecimal(Decimal64Utils.NEGATIVE_INFINITY)

        /**
         * A constant holding the smallest representable number: `-9999999999999999E+369`
         */
        val MIN_VALUE: KDecimal = KDecimal(Decimal64Utils.MIN_VALUE)

        /**
         * A constant holding the largest representable number: `9999999999999999E+369`
         */
        val MAX_VALUE: KDecimal = KDecimal(Decimal64Utils.MAX_VALUE)

        /**
         * A constant holding the smallest representable positive number: `1E-398`
         */
        val MIN_POSITIVE_VALUE: KDecimal = KDecimal(Decimal64Utils.MIN_POSITIVE_VALUE)

        /**
         * A constant holding the largest representable negative number: `-1E-398`
         */
        val MAX_NEGATIVE_VALUE: KDecimal = KDecimal(Decimal64Utils.MAX_NEGATIVE_VALUE)

        /**
         * Zero: `0`
         */
        val ZERO: KDecimal = KDecimal(Decimal64Utils.ZERO)

        /**
         * One: `1`
         */
        val ONE: KDecimal = KDecimal(Decimal64Utils.ONE)

        /**
         * Two: `2`
         */
        val TWO: KDecimal = KDecimal(Decimal64Utils.TWO)

        /**
         * Ten: `10`
         */
        val TEN: KDecimal = KDecimal(Decimal64Utils.TEN)

        /**
         * One Hundred: `100`
         */
        val HUNDRED: KDecimal = KDecimal(Decimal64Utils.HUNDRED)

        /**
         * One Thousand: `1000`
         */
        val THOUSAND: KDecimal = KDecimal(Decimal64Utils.THOUSAND)

        /**
         * One million: `1000_000`
         */
        val MILLION: KDecimal = KDecimal(Decimal64Utils.MILLION)

        /**
         * One tenth: `0.1`
         */
        val ONE_TENTH: KDecimal = KDecimal(Decimal64Utils.ONE_TENTH)

        /**
         * One hundredth: `0.01`
         */
        val ONE_HUNDREDTH: KDecimal = KDecimal(Decimal64Utils.ONE_HUNDREDTH)

        /**
         * Create `KDecimal` instance from `BigDecimal` binary floating point value.
         *
         * Note that not all binary FP values can be exactly represented as decimal FP values.
         *
         * @param value source `BigDecimal` binary floating point value
         * @return new `KDecimal` instance
         */
        fun fromBigDecimal(value: BigDecimal): KDecimal = KDecimal(Decimal64Utils.fromBigDecimal(value))

        /**
         * Create `KDecimal` instance from `BigDecimal` binary floating point value.
         *
         * @param value source `BigDecimal` binary floating point value
         * @return new `KDecimal` instance
         * @throws IllegalArgumentException if the value can't be converted to `KDecimal` without precision loss
         */
        @Throws(IllegalArgumentException::class)
        fun fromBigDecimalExact(value: BigDecimal): KDecimal = KDecimal(Decimal64Utils.fromBigDecimalExact(value))

        /**
         * Create `KDecimal` instance from fixed point decimal value: (12345, 2) -&gt; 123.45
         *
         * @param mantissa       source fixed point value represented as `long`
         * @param numberOfDigits number of decimal digits representing fractional part
         * @return new `KDecimal` instance
         * @see Decimal64Utils.fromFixedPoint
         */
        fun fromFixedPoint(mantissa: Long, numberOfDigits: Int): KDecimal {
            return KDecimal(Decimal64Utils.fromFixedPoint(mantissa, numberOfDigits))
        }

        /**
         * Create `KDecimal` instance from fixed point decimal value: (12345, 2) -&gt; 123.45
         * Overload of [.fromFixedPoint] for mantissa representable by `int`.
         * Faster than the full-range version.
         *
         * @param mantissa       source fixed point value represented as `int`
         * @param numberOfDigits number of decimal digits representing fractional part
         * @return new `KDecimal` instance
         * @see Decimal64Utils.fromFixedPoint
         */
        fun fromFixedPoint(mantissa: Int, numberOfDigits: Int): KDecimal =
            KDecimal(Decimal64Utils.fromFixedPoint(mantissa, numberOfDigits))

        /**
         * Create `KDecimal` instance from 64-bit binary floating point value(`double`)
         *
         * Note that not all binary FP values can be exactly represented as decimal FP values.
         *
         * @param value source 64-bit binary floating point value
         * @return new `KDecimal` instance
         */
        fun fromDouble(value: Double): KDecimal = KDecimal(Decimal64Utils.fromDouble(value))


        /**
         * Create `KDecimal` instance from `long` integer
         * Faster than the full-range version.
         *
         * @param value source `long` integer value
         * @return new `KDecimal` instance
         */
        fun fromLong(value: Long): KDecimal = KDecimal(Decimal64Utils.fromLong(value))

        /**
         * Create `KDecimal` instance from `int`
         *
         *
         * Faster than the version that takes `long`.
         *
         * @param value source `int` value
         * @return new `KDecimal` instance
         */
        fun fromInt(value: Int): KDecimal = KDecimal(Decimal64Utils.fromInt(value))

        /**
         * Try parse a dfp floating-point value from the given textual representation.
         *
         *
         * Besides regular floating-point values (possibly in scientific notation) the following special cases are accepted:
         *
         *  * `+Inf`, `Inf`, `+Infinity`, `Infinity` in any character case result in
         * `Decimal64Utils.POSITIVE_INFINITY`
         *  * `-Inf`, `-Infinity` in any character case result in
         * `Decimal64Utils.NEGATIVE_INFINITY`
         *  * `+NaN`, `-NaN`, `NaN` in any character case result in
         * `Decimal64Utils.NaN`
         *
         *
         * @param text       Textual representation of dfp floating-point value.
         * @param startIndex Index of character to start parsing at.
         * @param endIndex   Index of character to stop parsing at, non-inclusive.
         * @param outStatus  The parsing output status and value.
         * @return Return `true` if value was parsed exactly without rounding.
         * @throws NumberFormatException if `text` does not contain valid dfp value.
         */
        fun tryParse(text: CharSequence, outStatus: Decimal64Status, startIndex: Int = 0, endIndex: Int = text.length): Boolean =
            Decimal64Utils.tryParse(text, startIndex, endIndex, outStatus)

        /**
         * Parses a dfp floating-point value from the given textual representation.
         *
         *
         * Besides regular floating-point values (possibly in scientific notation) the following special cases are accepted:
         *
         *  * `+Inf`, `Inf`, `+Infinity`, `Infinity` in any character case result in
         * `Decimal64Utils.POSITIVE_INFINITY`
         *  * `-Inf`, `-Infinity` in any character case result in
         * `Decimal64Utils.NEGATIVE_INFINITY`
         *  * `+NaN`, `-NaN`, `NaN` in any character case result in
         * `Decimal64Utils.NaN`
         *
         *
         * @param text       Textual representation of dfp floating-point value.
         * @param startIndex Index of character to start parsing at.
         * @param stopIndex  Index of character to stop parsing at.
         * @return 64-bit dfp floating-point.
         * @throws NumberFormatException if `text` does not contain valid dfp floating value.
         */
        fun parse(text: CharSequence, startIndex: Int = 0, stopIndex: Int = text.length): KDecimal =
            KDecimal(Decimal64Utils.parse(text, startIndex, stopIndex))

        fun min(a: KDecimal, b: KDecimal): KDecimal = KDecimal(Decimal64Utils.min(a.value, b.value))

        fun min(a: KDecimal, b: KDecimal, c: KDecimal): KDecimal = KDecimal(Decimal64Utils.min(a.value, b.value, c.value))

        fun min(a: KDecimal, b: KDecimal, c: KDecimal, d: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.min(a.value, b.value, c.value, d.value))

        fun max(a: KDecimal, b: KDecimal): KDecimal = KDecimal(Decimal64Utils.max(a.value, b.value))

        fun max(a: KDecimal, b: KDecimal, c: KDecimal): KDecimal = KDecimal(Decimal64Utils.max(a.value, b.value, c.value))

        fun max(a: KDecimal, b: KDecimal, c: KDecimal, d: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.max(a.value, b.value, c.value, d.value))

        fun add(a: KDecimal, b: KDecimal): KDecimal = KDecimal(Decimal64Utils.add(a.value, b.value))

        fun add(a: KDecimal, b: KDecimal, c: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.add(a.value, b.value, c.value))

        fun add(a: KDecimal, b: KDecimal, c: KDecimal, d: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.add(a.value, b.value, c.value, d.value))

        fun multiply(a: KDecimal, b: KDecimal): KDecimal = KDecimal(Decimal64Utils.multiply(a.value, b.value))

        fun multiply(a: KDecimal, b: KDecimal, c: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.multiply(a.value, b.value, c.value))

        fun multiply(a: KDecimal, b: KDecimal, c: KDecimal, d: KDecimal): KDecimal =
            KDecimal(Decimal64Utils.multiply(a.value, b.value, c.value, d.value))

    }
}

/**
 * Get binary representation as `long` (unboxing)
 *
 * @param obj `KDecimal` instance, `null` can be passed too
 * @return underlying binary representation as `long`
 */
fun KDecimal?.toUnderlying(): Long {
    return this?.value ?: Decimal64Utils.NULL
}

fun KDecimal?.isNull() = this == null || this.isNull()

