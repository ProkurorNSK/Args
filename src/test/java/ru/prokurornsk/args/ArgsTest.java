package ru.prokurornsk.args;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class ArgsTest {

    private Args args;

    @Before
    public void setup() throws ParseException {
        String[] arg = {"-l", "-s", "abc", "-f", "-n", "-t", "456", "-d"};
        args = new Args("f,l,m,t#,s*,e*,d#", arg);
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

    @Test
    public void successInt() {
        assertEquals(456, args.getInt('t'));
    }

    @Test
    public void testSimpleDoublePresent() throws Exception {
        Args args1 = new Args ("x##", new String[] {"-x", "42.3"});
        assertTrue(args1.isValid());
        assertEquals(1, args1.cardinality());
        assertTrue(args1.has('x'));
        assertEquals(42.3, args1.getDouble('x'), .001);
    }
}
