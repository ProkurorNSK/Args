package ru.prokurornsk.args;

import java.util.Iterator;

class BooleanArgumentMarshaler implements ArgumentMarshaler {
    private boolean booleanValue = false;

    public static boolean getValue(ArgumentMarshaler am) {
        if (am != null && am instanceof BooleanArgumentMarshaler) {
            return ((BooleanArgumentMarshaler) am).booleanValue;
        } else {
            return false;
        }
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        booleanValue = true;
    }
}
