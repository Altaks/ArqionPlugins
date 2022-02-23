package fr.altaks.heleshop.manager;

public enum ShopMenu {
	
	TOOLS("outils","§eBoutique \u00BB Armes/Outils"),
	FOOD("nourriture","§eBoutique \u00BB Nourritures"),
	POTIONS("potions","§eBoutique \u00BB Potions"),
	LOOTS("butins","§eBoutique \u00BB Butins"),
	TERRAIN_BLOCKS("blocks_terrain","§eBoutique \u00BB Blocs de terrain"),
	BUILDING_BLOCKS("blocks_build","§eBoutique \u00BB Blocs de construction"),
	SEEDS("graines","§eBoutique \u00BB Graines/Pousses");
	
	public String id, title;
	
	private ShopMenu(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public static ShopMenu getFromId(String id) {
		for(ShopMenu menu : values()) {
			if(menu.id.equalsIgnoreCase(id)) {
				return menu;
			}
		}
		return null;
	}
	
}
