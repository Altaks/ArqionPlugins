package fr.nowayy.helecore.utils;

public enum Messages {
	
	Enabling_Plugin					("Le plugin a été activé"),
	Disabling_Plugin				("Le plugin a été désactivé"),
	
	FlySpeed_ChangingYourself		("§aVous volez désormais à : §e{@FlySpeed}"),
	FlySpeed_ChangingOther			("§aLe joueur §6{@target} §avole désormais à la vitesse §e{@FlySpeed}"),
	
	WalkSpeed_ChangingYourself		("§aVous marchez désormais à la vitesse : §e{@WalkSpeed}"),
	WalkSpeed_ChangingOther			("Le joueur §6{@target} §amarche désormais à la vitesse de : §e{@WalkSpeed}"),
	
	Speed_ChangingError				("§cVous ne pouvez pas utiliser cette valeur en tant que multiplicateur de vitesse !"),
	
	Error_PlayerNotFound			("§cLe joueur §6{@target} §cn'est pas connecté !"),
	Error_HomeDontExists			("§cLe home §e{@Home} §cn'existe pas !"),
	Error_WarpDontExists			("§cLe warp §e{@WarpName} §cn'existe pas !"),
	Error_WorldDontExists			("§cLe monde §e{@WorldName} n'existe pas !"),
	Error_EmptyHandToEnchant		("§cVous ne pouvez pas enchanter votre main !"),
	Error_ArgumentsMissing			("§cIl manque le nom du warp !"),
	Error_NoWarps					("§cIl n'y a aucun warp de créé !"),
	
	Home_TeleportYourself			("§aVous venez d'être téléporté au home §e{@HomeName}"),
	Home_TeleportOther				("§aVous venez de téléporter §6{@target} §aau home §e{@HomeName}"),
	
	Home_Set						("§aLe home §e{@HomeName} §avient d'être défini sur votre position."),
	Home_Del						("§aLe home §e{@HomeName} §avient d'être supprimé."),
	Warp_Set						("§aLe warp §e{@WarpName} §avient d'être défini sur votre position."),
	Warp_Del						("§aLe warp §e{@WarpName} §avient d'être supprimé."),
	
	Fly_ChangeStateToOnYourself		("§aVous pouvez désormais voler !"),
	Fly_ChangeStateToOnOther		("§aLe joueur §6{@target} §apeut désormais voler !"),
	Fly_ChangeStateToOffYourself	("§aVous ne pouvez plus voler !"),
	Fly_ChangeStateToOffOther		("§aLe joueur §6{@target} §ane peut plus voler !"),
	
	GodMod_ChangeState_ToOnYourself	("§aVous êtes désormais invulnérable !"),
	GodMod_ChangeState_ToOnOther	("§aLe joueur §6{@target} §aest désormais invulnérable !"),
	GodMod_ChangeState_ToOffYourself("§aVous n'êtes plus invulnérable !"),
	GodMod_ChangeState_ToOffOther	("§aLe joueur §6{@target} §an'est plus invulnérable !"),
	
	PlayerInfo_CannotInspect		("§cVous ne pouvez pas inspecter le joueur §e{@target}"),
	
	Heal_Yourself					("§aVous avez été guéri !"),
	Heal_Other						("§aLe joueur §6{@target} §aa été guéri"),
	Heal_ErrorFullHealth			("§cVous ne pouvez pas guérir §6{@target} §ccar sa vie est déjà pleine !"),
	
	Feed_Yourself					("§aVous avez été nourri !"),
	Feed_Other						("§aLe joueur §6{@target} §aa été nourri !"),
	Feed_ErrorFullFood				("§cVous ne pouvez pas nourrir §6{@target} §ccar il est déjà rassasié !"),
	
	KillAll_HostilesEntities		(""),
	KillAll_AnimalsEntities			(""),
	KillAll_MinecartEntities		(""),
	KillAll_ArrowEntities			(""),
	
	NonPlayerSender_Error			("§cCette commande ne peut être exécutée que par un joueur !");
	
	
	
	
	public String fr_FR;
	
	private Messages(String fr_FR) {
		this.fr_FR = fr_FR;
	}
	
	@Override
    public String toString() {
        return this.fr_FR;
    }
}
