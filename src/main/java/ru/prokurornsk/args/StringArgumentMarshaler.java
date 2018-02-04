package ru.prokurornsk.args;

import java.util.Iterator;

class StringArgumentMarshaler implements ArgumentMarshaler {
    private String stringValue = "";


    public static String getValue(ArgumentMarshaler am) {
        if (am != null && am instanceof StringArgumentMarshaler) {
            return ((StringArgumentMarshaler) am).stringValue;
        } else {
            return "";
        }
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
