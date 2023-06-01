package fantasykingdom.fkd.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlySpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("fkd.flySpeed")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length == 1) {
            try {
                double speed = Double.parseDouble(args[0]);

                if (speed < 1.0 || speed > 10.0) {
                    player.sendMessage("Invalid speed value. Speed should be between 1.0 and 10.0.");
                    return true;
                }

                float convertedSpeed = (float) (speed / 10.0);

                player.setFlySpeed(convertedSpeed);
                player.sendMessage("Fly speed set to " + speed);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid speed value.");
            }
        } else {
            player.sendMessage("Usage: /flyspeed <speed>");
        }

        return true;
    }
}
