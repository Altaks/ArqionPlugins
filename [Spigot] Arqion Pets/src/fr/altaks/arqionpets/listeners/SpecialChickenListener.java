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

    private List<Entity> spawnedChickens = new ArrayList<Entity>(),
                         malusChicken = new ArrayList<Entity>();
    private HashMap<Entity, long> chickenMalusTimestamp = new HashMap<>();

    public SpecialChickenListener(Main main){
        new BukkitRunnable(){
            for(Entity chicken : malusChicken){
                // get entities around
                chicken.getWorld().getNearbyEntities(chicken.getLocation(), 3, 3, 3).forEach(e -> {
                    // apply effect
                    e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 2));
                    e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 10, 2));
                });
            }
            for(Entity chicken : spawnedChickens){
                if(chickenMalusTimestamp.get(chicken) > (System.currentTimeMillis() / 1000l)) {
                    // rendre chicken malus
                    malusChicken.add(chicken);
                    chickenMalusTimestamp.remove(chicken);
                }
            }
        }.runTaskTimer(main, 0, 20l);
    }

    @EventHandler
    public void onChickenDie(EntityDeathEvent event){
        if(this.spawnedChickens.contains(event.getEntity())){
            // c'est un poulet spawné
            float chance = new Random().nextFloat() * 100;
            if(chance < 5) {
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), new ItemStack(Material.DRAGON_EGG));
            }
            this.spawnedChickens.remove(event.getEntity());
            if(this.malusChicken.contains(event.getEntity())) this.malusChicken.remove(event.getEntity());
        }

    }

    @EventHandler
    public void onChickenSpawnEvent(EntitySpawnEvent event){
        if(event.getEntity().getType() == EntityType.CHICKEN){

            float random = new Random().nextFloat() * 100;
            if(random < 0.5){
                Chicken chicken = (Chicken) event.getEntity();

                spawnedChickens.add(chicken);
                chickenMalusTimestamp.put((System.currentTimeMillis() / 1000l) + (3 * 60), chicken);

                chicken.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1_000_000, 1));
                chicken.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1_000_000, 1));

            }

        }
    }

    public void flushEntities(){
        for(Entity entity : spawnedChickens) entity.remove();
    }

}