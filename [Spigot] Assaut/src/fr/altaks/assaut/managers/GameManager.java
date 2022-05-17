package fr.altaks.assaut.managers;

import fr.altaks.assaut.Main;
import fr.altaks.assaut.util.ItemManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameManager implements Listener {

    private Main main;
    private int playersPerTeam;
    private GameTeam attackers, defenders;
    private GameState state;

    public GameManager(Main main) throws ItemManager.ItemBuildingError {
        this.main = main;
        List<ItemStack> attackersStuff = new ArrayList<>(), defendersStuff = new ArrayList<>();

        for(String item : main.getConfig().getConfigurationSection("attackers-stuff").getKeys(false)){
            attackersStuff.add(ItemManager.fromString(item));
        }
        for(String item : main.getConfig().getConfigurationSection("defenders-stuff").getKeys(false)){
            defendersStuff.add(ItemManager.fromString(item));
        }

        this.attackers = new GameTeam(main, "Attaquants", ChatColor.RED, "attackers", attackersStuff);
        this.defenders = new GameTeam(main, "Défenseurs", ChatColor.BLUE, "defenders", defendersStuff);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(Bukkit.getOnlinePlayers().size() > 2*this.playersPerTeam && state == GameState.WAITING) {
            // on start la game
            startGame();
        }
    }

    public void startGame(){
        state = GameState.STARTING;
        // on crée les teams
        boolean switcher = true;
        for(Player player : Bukkit.getOnlinePlayers()) {
            (switcher ? attackers : defenders).team.addPlayer(player);
            switcher = !switcher;
        }

        // teleport each player from each team to their spawnpoint + give their stuff to players
        for(OfflinePlayer player : attackers.team.getPlayers()){
            if(player.isOnline()) {
                Player p = (Player) player;
                p.teleport(attackers.spawnPoint);
                attackers.stuff.forEach(item -> p.getInventory().addItem(item));
            }
        }

        for(OfflinePlayer player : defenders.team.getPlayers()){
            if(player.isOnline()) {
                Player p = (Player) player;
                p.teleport(defenders.spawnPoint);
                defenders.stuff.forEach(item -> p.getInventory().addItem(item));
            }
        }
        // annoncer la partie !
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.getUniqueId().toString().equals("187ea8d0-f497-45bb-b774-8948ca9178f9") || player.getUniqueId().toString().equals("325b18f3-09c3-4db1-b97a-0ab736bacc23")){
                player.sendTitle("§kEWE§r §bYou got UwU-ed ! §kAWA§r", "§bNyaaa~ DADDY~ Onii-chan :3", 70, 2, 2);
            } else player.sendTitle("§6Début de la partie !", "§eBonne partie !", 70, 2, 2);
        });

        // donner un beacon a un joueur random des défenseurs
        // donner un trident avec loyauté a un joueur random des attaquants (si Alta dans la team, pas random uwu.)
        Random random = new Random();
        Player player = (Player) defenders.team.getPlayers().stream().filter(p -> p.isOnline()).collect(Collectors.toList()).get(random.nextInt(defenders.team.getPlayers().stream().filter(p -> p.isOnline()).collect(Collectors.toList()).size()));

    }

    public void endGame(){

    }

    public static class GameTeam {
        private String teamName, teamId;
        private ChatColor color;
        private Team team;
        private Location spawnPoint;
        private List<ItemStack> stuff;

        public GameTeam(Main main, String teamName, ChatColor color, String teamId, List<ItemStack> stuff) {
            this.teamName = teamName;
            this.color = color;
            this.teamId = teamId;

            team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamId);
            team.setColor(color);
            team.setAllowFriendlyFire(false);

            // récuperer le spawnpoint
            String[] location = main.getConfig().getString(teamId + "coordinates").split("/");

            World world = Bukkit.getWorld(location[0]);
            double x = Double.parseDouble(location[1]);
            double y = Double.parseDouble(location[2]);
            double z = Double.parseDouble(location[3]);

            this.spawnPoint = new Location(world, x, y, z);
            this.stuff = stuff;
        }

    }

    public static enum GameState {
        WAITING,
        STARTING,
        INGAME,
        ENDING
    }
}
