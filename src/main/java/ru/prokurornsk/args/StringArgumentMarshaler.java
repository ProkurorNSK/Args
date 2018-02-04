package ru.prokurornsk.args;

import java.util.Iterator;

class StringArgumentMarshaler implements ArgumentMarshaler {
    private String stringValue = "";

    @Override
    public Object get() {
        return stringValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_STRING);
        }
    }
}
