package ru.prokurornsk.args;

import java.util.*;

public class Args {

    private String schema;
    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<>();
    private Set<Character> argsFound = new HashSet<>();
    private Iterator<String> currentArgument;
    private List<String> argsList;

    public Args(String schema, String[] args) throws ArgsException {
        this.schema = schema;
        argsList = Arrays.asList(args);
        parse();
    }

    public static void main(String[] args) throws Exception {
        try {
            Args arg = new Args("f,l,m,t#,s*,e*", args);
            System.out.println(arg.argsList.toString());
            System.out.println(arg.usage());
            System.out.println(arg.marshalers.toString());
            System.out.println(arg.argsFound.toString());
            System.out.println(arg.cardinality());
        } catch (ArgsException e) {
            e.errorMessage();
        }
    }

    private void parse() throws ArgsException {
        parseSchema();
        parseArguments();
    }

    private void parseSchema() throws ArgsException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                String trimmedElement = element.trim();
                parseSchemaElement(trimmedElement);
            }
        }
    }

    private void parseSchemaElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if (elementTail.length() == 0) {
            marshalers.put(elementId, new BooleanArgumentMarshaler());
        } else if (elementTail.equals("*")) {
            marshalers.put(elementId, new StringArgumentMarshaler());
        } else if (elementTail.equals("#")) {
            marshalers.put(elementId, new IntegerArgumentMarshaler());
        } else if (elementTail.equals("##")) {
            marshalers.put(elementId, new DoubleArgumentMarshaler());
        } else {
            throw new ArgsException(String.format("Argument: %c has invalid format: %s.", elementId, elementTail));
        }
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId)) {
            throw new ArgsException("Bad character:" + elementId + " in Args format: " + schema);
        }
    }

    private void parseArguments() throws ArgsException {
        for (currentArgument = argsList.iterator(); currentArgument.hasNext(); ) {
            String arg = currentArgument.next();
            parseArgument(arg);
        }
    }

    private void parseArgument(String arg) throws ArgsException {
        if (arg.startsWith("-")) {
            parseElements(arg);
        }
    }

    private void parseElements(String arg) throws ArgsException {
        for (int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }

    private void parseElement(char argChar) throws ArgsException {
        if (setArgument(argChar)) {
            argsFound.add(argChar);
        } else {
            throw new ArgsException(ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, argChar, null);
        }
    }

    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m == null) return false;
        try {
            m.set(currentArgument);
            return true;
        } catch (ArgsException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
    }

    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0) {
            return "-[" + schema + "]";
        } else {
            return "";
        }
    }

    public boolean getBoolean(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am != null && (Boolean) am.get();
        } catch (ClassCastException e) {
            return false;
        }
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am == null ? "" : (String) am.get();
        } catch (ClassCastException e) {
            return "";
        }
    }

    public int getInt(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am == null ? 0 : (Integer) am.get();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public double getDouble(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        try {
            return am == null ? 0.0 : (Double) am.get();
        } catch (ClassCastException e) {
            return 0.0;
        }
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

}