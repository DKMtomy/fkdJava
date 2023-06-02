package fantasykingdom.fkd.commands;

import fantasykingdom.fkd.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetRankCommand implements CommandExecutor {
    private RankManager rankManager;

    public SetRankCommand(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /setrank <player> <rank>");
            return true;
        }

        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) {
            sender.sendMessage("Player not found: " + playerName);
            return true;
        }

        String rank = args[1];
        rankManager.addRank(player, rank);
        sender.sendMessage("Added rank " + rank + " for player " + playerName);
        return true;
    }
}
