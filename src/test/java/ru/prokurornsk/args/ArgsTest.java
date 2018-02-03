package ru.prokurornsk.args;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class ArgsTest {

    private Args args;

    @Before
    public void setup() throws ParseException {
        String[] arg = {"-l", "-s", "abc", "-f", "-e", "-n"};
        args = new Args("f,l,m,s*,e*", arg);
    }

    @Test
    public void successBoolean() {
        assertTrue(args.getBoolean('l'));
    }

    @Test
    public void failBoolean() {
        assertFalse(args.getBoolean('m'));
    }

    @Test
    public void notFoundBoolean() {
        assertFalse(args.getBoolean('n'));
    }

    @Test
    public void successString() {
        assertEquals(args.getString('s'), "abc");
    }
}
