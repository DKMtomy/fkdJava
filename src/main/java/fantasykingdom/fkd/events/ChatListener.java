package fantasykingdom.fkd.events;

import fantasykingdom.fkd.managers.RankManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.List;

public class ChatListener implements Listener {
    private RankManager rankManager;

    public ChatListener(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        List<String> ranks = rankManager.getRanks(player);
        String playerName = player.getName();
        String message = event.getMessage();

        StringBuilder rankBuilder = new StringBuilder();
        for (String rank : ranks) {
            ChatColor rankColor = ChatColor.WHITE; // Default color
            if (rank.length() > 2 && rank.charAt(0) == '&') {
                ChatColor color = ChatColor.getByChar(rank.charAt(1));
                if (color != null) {
                    rankColor = color;
                    rank = rank.substring(2);
                }
            }
            rankBuilder.append("[").append(rankColor).append(rank).append(ChatColor.RESET).append("] ");
        }

        String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);
        event.setFormat(rankBuilder.toString() + playerName + " >> " + formattedMessage);
    }
}
