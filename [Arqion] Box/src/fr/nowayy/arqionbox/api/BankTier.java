package fr.nowayy.arqionbox.api;

public enum BankTier {
	
	FIRST(		1, 			0, 				250_000),
	SECOND(		2, 			175_000, 		1_000_000),
	THIRD(		3, 			500_000, 		2_000_000),
	FOURTH(		4,			1_000_000,		4_000_000),
	FIFTH(		5,			2_500_000, 		9_000_000),
	SIXTH(		6,			5_750_000, 		15_000_000);

	public int id;
	public long price, max_storage;
	
	private BankTier(int id, long price, long max_storage) {
		this.id = id;
		this.price = price;
		this.max_storage = max_storage;
	}
	
	public static BankTier getFromId(int id) {
		return values()[id-1];
	}
	
}
