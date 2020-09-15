package org.orderapi.common.exception;

import javax.servlet.http.HttpServletResponse;

public class ApplicationBadInputException extends ApplicationException {
    private static final String MESSAGE = "Bad input";
    private static final int ERROR_STATUS = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    private static final long serialVersionUID = -4378369466280988947L;

    public ApplicationBadInputException() {
        super(MESSAGE, ERROR_STATUS);
    }

    public ApplicationBadInputException(final String message, final String errorStatus) {
        super(message, ERROR_STATUS);
    }

    public ApplicationBadInputException(final String message) {
        super(message, ERROR_STATUS);
    }

    public ApplicationBadInputException(final Throwable cause) {
        super(MESSAGE, cause, ERROR_STATUS);
    }

    public ApplicationBadInputException(final String message, final Throwable cause) {
        super(message, cause, ERROR_STATUS);
    }
}
