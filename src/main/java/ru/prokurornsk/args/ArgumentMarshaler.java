package ru.prokurornsk.args;

import java.util.Iterator;

interface ArgumentMarshaler {

    Object get();

    void set(Iterator<String> currentArgument) throws ArgsException;
}
