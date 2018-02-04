package ru.prokurornsk.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

class IntegerArgumentMarshaler implements ArgumentMarshaler {
    private int intValue = 0;

    @Override
    public Object get() {
        return intValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_INTEGER);
        } catch (NumberFormatException e) {
            throw new ArgsException(ArgsException.ErrorCode.INVALID_INTEGER, parameter);
        }
    }
}
