package fantasykingdom.fkd.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class flyCommand implements CommandExecutor {

    private ArrayList<Player> list_of_flying_players = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                flyMethod(player);
            } else if (args.length == 1 ) {
                if (!player.hasPermission("fkd.flyTarget")) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to make others fly!");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                flyMethod(target);
            }
        }
        return true;
    }

    private void flyMethod(Player player) {
        if (player.hasPermission("fkd.fly")) {
            if (list_of_flying_players.contains(player)) {
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.RED + "Successfully disabled fly");
                list_of_flying_players.remove(player);
            } else {
                player.setAllowFlight(true);
                player.sendMessage(ChatColor.GREEN + "Successfully enabled fly");
                list_of_flying_players.add(player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission for this command");
        }
    }
}
