package fr.matmatgamer.helebitcoins.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.matmatgamer.helebitcoins.Main;
import fr.matmatgamer.helebitcoins.utils.Crafts;
import fr.matmatgamer.helebitcoins.utils.GenerateYAML;
import fr.matmatgamer.helebitcoins.utils.timers.AllimEssenceGenerate;
import fr.matmatgamer.helebitcoins.utils.timers.AllimSolaireGenerate;
import fr.matmatgamer.helebitcoins.utils.timers.BatterieReceived;
import fr.matmatgamer.helebitcoins.utils.timers.BatterieSend;
import fr.matmatgamer.helebitcoins.utils.timers.BitcoinsGenerate;
import fr.matmatgamer.helebitcoins.utils.timers.PcEnergie;

public class Join implements Listener {
	
	private Main main;
	public Join(Main main) {
		this.main = main;
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		GenerateYAML generateYAML = new GenerateYAML(main);
		generateYAML.UpdateYAML(p);
		
		new BitcoinsGenerate(p, main).runTaskTimer(main, 0, 20);
		new AllimSolaireGenerate(p, main).runTaskTimer(main, 0, 20);
		new AllimEssenceGenerate(p, main).runTaskTimer(main, 0, 20);
		new BatterieReceived(p, main).runTaskTimer(main, 0, 10);
		new BatterieSend(p, main).runTaskTimer(main, 0, 10);
		new PcEnergie(p, main).runTaskTimer(main, 0, 20);
		
		new Crafts(main).unlockCrafts(e.getPlayer());

	}
	
}
