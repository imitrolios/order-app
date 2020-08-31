package org.sharedexpenses.common.exception;

import javax.servlet.http.HttpServletResponse;

public class ApplicationValidationException extends ApplicationException{
    private static final String MESSAGE = "Validation error occurred";
    private static final int ERROR_STATUS = HttpServletResponse.SC_BAD_REQUEST;
    private static final long serialVersionUID = -5800282692667353570L;

    public ApplicationValidationException() {
        super(MESSAGE, ERROR_STATUS);
    }

    public ApplicationValidationException(final String message) {
        super(message, ERROR_STATUS);
    }

    public ApplicationValidationException(final Throwable cause) {
        super(MESSAGE, cause, ERROR_STATUS);
    }

    public ApplicationValidationException(final String message, final Throwable cause) {
        super(message, cause, ERROR_STATUS);
    }
}
