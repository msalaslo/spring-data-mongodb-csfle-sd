package com.verisure.vcp.springdatamongodbcsfle.service.exception;

/**
 * Class for handling service layer exceptions in a common way.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Constructs an {@code ServiceException} with the specified detail message and no root cause.
     *
     * @param message
     *            The detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */	
	public ServiceException(String message) {
		super(message);
	}

    /**
     * Constructs an {@code ServiceException} with the specified root cause.
     *
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated into this
     * exception's detail message.
     *
     * @param cause
     *            The cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ServiceException(Throwable cause) { super(cause); }

    /**
     * Constructs an {@code ServiceException} with the specified detail message and root cause.
     *
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated into this
     * exception's detail message.
     *
     * @param message
     *            The detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     *
     * @param cause
     *            The cause (which is saved for later retrieval by the {@link #getCause()} method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */    
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}