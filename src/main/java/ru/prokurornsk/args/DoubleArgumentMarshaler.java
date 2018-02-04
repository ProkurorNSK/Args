package ru.prokurornsk.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue = 0;

    public static double getValue(ArgumentMarshaler am) {
        if (am != null && am instanceof DoubleArgumentMarshaler) {
            return ((DoubleArgumentMarshaler) am).doubleValue;
        } else {
            return 0.0;
        }
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_DOUBLE);
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_DOUBLE, parameter);
        }
    }
}
