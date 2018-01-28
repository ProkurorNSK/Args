package ru.prokurornsk.args;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArgsTest {

    private Args args;

    @Before
    public void setup() {
        String[] arg = {"-l"};
        args = new Args("l,m", arg);
    }

    @Test
    public void successBoolean() {
        assertTrue(args.getBoolean('l'));
    }

    @Test
    public void failBoolean() {
        assertFalse(args.getBoolean('m'));
    }

    @Test(expected = NullPointerException.class)
    public void notFoundBoolean() {
        assertFalse(args.getBoolean('n'));
    }
}
