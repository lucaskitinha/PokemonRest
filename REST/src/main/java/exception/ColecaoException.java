package exception;

public class ColecaoException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ColecaoException(String msg) {
		super(msg);
	}
	
	public ColecaoException(Throwable e) {
		super(e);
	}
	
	public ColecaoException(String msg, Throwable e) {
		super(msg, e);
	}

}
