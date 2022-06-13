package exception;


public class ConexaoException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ConexaoException(String msg) {
		super(msg);
	}
	
	public ConexaoException(Throwable e) {
		super(e);
	}
	
	public ConexaoException(String msg, Throwable e) {
		super(msg, e);
	}

}
