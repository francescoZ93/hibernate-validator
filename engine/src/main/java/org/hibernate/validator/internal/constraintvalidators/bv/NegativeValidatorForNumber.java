/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.internal.constraintvalidators.bv;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Check that the number being validated is negative.
 *
 * @author Hardy Ferentschik
 * @author Xavier Sosnovsky
 * @author Guillaume Smet
 * @author Marko Bekhta
 */
public class NegativeValidatorForNumber extends BaseNegativeValidator<Number> {

	private static final short SHORT_ZERO = (short) 0;
	private static final byte BYTE_ZERO = (byte) 0;

	@Override
	protected int compare(Number value) {
		// handling of NaN, positive infinity and negative infinity
		if ( value instanceof Double ) {
			if ( (Double) value == Double.NEGATIVE_INFINITY ) {
				return -1;
			}
			else if ( Double.isNaN( (Double) value ) || (Double) value == Double.POSITIVE_INFINITY ) {
				return 1;
			}
		}
		else if ( value instanceof Float ) {
			if ( (Float) value == Float.NEGATIVE_INFINITY ) {
				return -1;
			}
			else if ( Float.isNaN( (Float) value ) || (Float) value == Float.POSITIVE_INFINITY ) {
				return 1;
			}
		}

		int comparisonResult;
		if ( value instanceof BigDecimal ) {
			comparisonResult = ( (BigDecimal) value ).compareTo( BigDecimal.ZERO );
		}
		else if ( value instanceof BigInteger ) {
			comparisonResult = ( (BigInteger) value ).compareTo( BigInteger.ZERO );
		}
		else if ( value instanceof Long ) {
			comparisonResult = ( (Long) value ).compareTo( 0L );
		}
		else if ( value instanceof Integer ) {
			comparisonResult = ( (Integer) value ).compareTo( 0 );
		}
		else if ( value instanceof Float ) {
			comparisonResult = ( (Float) value ).compareTo( 0F );
		}
		else if ( value instanceof Double ) {
			comparisonResult = ( (Double) value ).compareTo( 0D );
		}
		else if ( value instanceof Short ) {
			comparisonResult = ( (Short) value ).compareTo( SHORT_ZERO );
		}
		else if ( value instanceof Byte ) {
			comparisonResult = ( (Byte) value ).compareTo( BYTE_ZERO );
		}
		else {
			return Double.compare( value.doubleValue(), 0D );
		}
		return comparisonResult;
	}
}
