package fr.altaks.arqionpets.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.altaks.arqionpets.Main;

public class SpecialChickenListener implements Listener {

    private List<Entity> spawnedChickens = new ArrayList<Entity>();

    public SpecialChickenListener(Main main){

        new BukkitRunnable(){
            
        	@Override
        	public void run() {
        		
        	}

        }.runTaskTimer(main, 0, 5 * 60 * 20); // 5 min
    }

    @EventHandler
    public void onChickenDie(EntityDeathEvent event){
        if(this.spawnedChickens.contains(event.getEntity())){
            // c'est un poulet spawné
            float chance = new Random().nextFloat() * 100;
            if(chance < 5){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(Material.DRAGON_EGG));
            }

        }

    }

    public void flushEntities(){
        for(Entity entity : spawnedChickens) entity.remove();
    }

}