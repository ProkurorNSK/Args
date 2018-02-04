package ru.prokurornsk.args;

import java.util.Iterator;

class BooleanArgumentMarshaler implements ArgumentMarshaler {
    private boolean booleanValue = false;

    @Override
    public Object get() {
        return booleanValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) {
        booleanValue = true;
    }
}
