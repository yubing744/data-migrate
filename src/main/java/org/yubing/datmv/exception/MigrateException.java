package org.yubing.datmv.exception;

/**
 *	迁移异常
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-24
 */
public class MigrateException extends RuntimeException {

	private static final long serialVersionUID = 7422654242289682644L;

	public MigrateException() {
		super();
	}

	public MigrateException(String message, Throwable cause) {
		super(message, cause);
	}

	public MigrateException(String message) {
		super(message);
	}

	public MigrateException(Throwable cause) {
		super(cause);
	}

}
