package ru.prokurornsk.args;

class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;

    public enum ErrorCode {
        OK,
        MISSING_STRING,
        MISSING_INTEGER,
        INVALID_INTEGER,
        MISSING_DOUBLE,
        INVALID_DOUBLE,
        UNEXPECTED_ARGUMENT
    }

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorParameter = errorParameter;
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
        this.errorCode = errorCode;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here.");
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpects.", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%C.", errorArgumentId);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%C.", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -%c expects an integer but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%C.", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format("Argument -%c expects an double but was '%s'.", errorArgumentId, errorParameter);
        }
        return "";
    }
}
