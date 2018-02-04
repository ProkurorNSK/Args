package ru.prokurornsk.args;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgsTest {

    private Args args;

    @Before
    public void setup() throws Exception {
        try {
            String[] arg = new String[]{"-l", "-s", "abc", "-f", "-t", "456"};
            args = new Args("f,l,t#,s*", arg);
        } catch (ArgsException e) {
            System.out.println(e.errorMessage());
        }
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
    public void testSimpleDoublePresent() throws ArgsException {
        Args args1 = new Args("x##", new String[]{"-x", "42.3"});
        assertTrue(args1.has('x'));
        assertEquals(42.3, args1.getDouble('x'), .001);
    }

    @Test
    public void testInvalidDouble() {
        try {
            new Args("x##", new String[]{"-x", "Forty"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ArgsException.ErrorCode.INVALID_DOUBLE, e.getErrorCode());
            assertEquals('x', e.getErrorArgumentId());
            assertEquals("Forty", e.getErrorParameter());
        }
    }

    @Test
    public void testMissingDouble() {
        try {
            new Args("x##", new String[]{"-x"});
            fail();
        } catch (ArgsException e) {
            assertEquals(ArgsException.ErrorCode.MISSING_DOUBLE, e.getErrorCode());
            assertEquals('x', e.getErrorArgumentId());
        }
    }
}
