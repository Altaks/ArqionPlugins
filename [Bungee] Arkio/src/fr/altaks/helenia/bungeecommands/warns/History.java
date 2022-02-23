package fr.altaks.helenia.bungeecommands.warns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import fr.altaks.helenia.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class History extends Command {

	private Main main;
	
	public History(String name, Main main) {
		super("history","helenia.history");
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length > 0) {
			
			String playername = args[0];
			
			ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
			if(player == null) {
				sender.sendMessage(new TextComponent(Main.PREFIX + " " + playername + " n'est jamais venu(e) sur le serveur"));
				return;
			}
			
			try {
				for(TextComponent warn : getHistory(player.getUniqueId())) {
					sender.sendMessage(warn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}

	}
	
	@SuppressWarnings("deprecation")
	public List<TextComponent> getHistory(UUID id) throws SQLException {
		List<TextComponent> list = new ArrayList<TextComponent>();
		
		final Connection connection = main.getDatabaseManager().getActivatedConnection().getConnection();
		final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `warns` WHERE playerid=? ORDER BY timestamp DESC");
		
		statement.setString(1, id.toString());
		
		final ResultSet result = statement.executeQuery();
		
		if(result.first()) {
			do {
				
				String moderatorid = result.getString("moderatorid"), playerid = result.getString("playerid"), moderator = result.getString("moderator"), player = result.getString("player");
				String reason = result.getString("reason").trim().toString();
				
				String playerserver = result.getString("playerserver");
				
				Timestamp timestamp = result.getTimestamp("timestamp");
				
				// former un string touuuut boooooo
				
				// Joueur par Modo (id copiables)
				// Date 
				// Raison
				
				TextComponent playercomp = new TextComponent("§e"+player +" ");
				playercomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, player));
				playercomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));
				
				TextComponent moderatorcomp = new TextComponent("§e"+moderator+ " ");
				moderatorcomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, moderator));
				moderatorcomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));
				
				TextComponent playeridcomp = new TextComponent("§7[ID]§r");
				playeridcomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, playerid));
				playeridcomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));
				
				TextComponent moderatoridcomp = new TextComponent("§7[ID]§r");
				moderatoridcomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, moderatorid));
				moderatoridcomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));

				
				Date date = Date.from(timestamp.toInstant());
				DateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.FRANCE);
				TextComponent datecomp = new TextComponent(format.format(date));
				datecomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, format.format(date)));
				datecomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));

				
				TextComponent reasoncomp = new TextComponent(reason);
				reasoncomp.setClickEvent(new ClickEvent(Action.COPY_TO_CLIPBOARD, reason));
				reasoncomp.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§eCliques ici pour copier !")}));
				
				TextComponent total = new TextComponent(Main.PREFIX + "\n");
				
				total.addExtra(playercomp);
				total.addExtra(playeridcomp);
				
				total.addExtra(" | ");
				
				total.addExtra(moderatorcomp);
				total.addExtra(moderatoridcomp);
				
				total.addExtra(" sur §e");
				total.addExtra(playerserver);
				total.addExtra("§r\n");
				
				total.addExtra("§6Date : §e");
				total.addExtra(datecomp);
				
				total.addExtra("\n§6Raison : §e");
				total.addExtra(reasoncomp);

				list.add(total);
				
			} while(result.next());
		} else {
			// dire qu'il n'y a pas d'historique
			
			list.add(new TextComponent(Main.PREFIX + " Aucun historique trouvé"));
		}
		return list;
	}

}
