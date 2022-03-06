package fr.altaks.arqionpets;

public class PetCrafts {
	
	public static final PetCraft bat = new PetCraft(PluginItems.bat_pet, PetComponents.bat_components);
	public static final PetCraft silverfish = new PetCraft(PluginItems.silverfish_pet, PetComponents.silverfish_components);
	public static final PetCraft parrot = new PetCraft(PluginItems.parrot_pet, PetComponents.parrot_components);
	public static final PetCraft phantom = new PetCraft(PluginItems.phantom_pet, PetComponents.phantom_components);
	public static final PetCraft slime = new PetCraft(PluginItems.slime_pet, PetComponents.slime_components);
	public static final PetCraft pig = new PetCraft(PluginItems.pig_pet, PetComponents.pig_components);
	public static final PetCraft edrag = new PetCraft(PluginItems.ender_drag_pet, PetComponents.edrag_components);
	public static final PetCraft chicken = new PetCraft(PluginItems.chicken_pet, PetComponents.chicken_components);
	
	public static final PetCraft[] all_pets_crafts = {
		bat, silverfish, parrot, phantom, slime, pig, edrag, chicken	
	};
}
