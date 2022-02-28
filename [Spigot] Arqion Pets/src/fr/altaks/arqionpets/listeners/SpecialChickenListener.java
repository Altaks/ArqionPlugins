package fr.altaks.arqionpets.listeners;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.altaks.arqionpets.PluginItems;
import fr.altaks.arqionpets.utils.ItemManager;

public class SpecialChickenListener implements Listener {

    private List<Entity> spawnedChickens = new ArrayList<Entity>();

    public SpecialChickenListener(){

        new BukkitRunnable(){
            


        }.runTaskTimer(main, 0, 5 * 60 * 20); // 5 min
    }

    @EventHandler
    public void onChickenDie(EntityDeathEvent event){
        if(this.spawnedChickens.contains(event.getEntity())){
            // c'est un poulet spawné
            float chance = new Random().nextFloat() * 100;
            if(chance < 5){
                event.getEntiy().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(Material.DRAGON_EGG));
            }

        }

    }

    public void flushEntities(){
        for(Entity entity : spawnedChickens) entity.remove();
    }

}