package fantasykingdom.fkd.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIcomand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Inventory gui = Bukkit.createInventory(player,9, ChatColor.AQUA + "Fantasy Kingdom Gui");

            ItemStack warps = new ItemStack(Material.COMPASS);

            ItemMeta warps_meta = warps.getItemMeta();
            warps_meta.setDisplayName(ChatColor.GREEN + "Warps");
            ArrayList<String> warps_lore = new ArrayList<>();
            warps_lore.add(ChatColor.LIGHT_PURPLE + "Open the warp menu");
            warps_meta.setLore(warps_lore);
            warps.setItemMeta(warps_meta);

            ItemStack[] menu_items = {warps};
            gui.setContents(menu_items);
            player.openInventory(gui);
        }

        return true;
    }
}
