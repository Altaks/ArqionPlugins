package fr.altaks.helemoney.api;

public class HeleMoneyException extends Exception {

	private static final long serialVersionUID = -1174164542448192486L;
	
	public HeleMoneyException(String errorMessage) {
		super(errorMessage);
	}

}
