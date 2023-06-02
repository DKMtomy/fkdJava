package fantasykingdom.fkd.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUIcomand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Fantasy Kingdom Gui");

            ItemStack tpa = createMenuItem(Material.ENDER_PEARL, ChatColor.GREEN + "TPA", ChatColor.LIGHT_PURPLE + "Request to teleport to another player");
            ItemStack help = createMenuItem(Material.BOOK, ChatColor.GREEN + "Help", ChatColor.LIGHT_PURPLE + "Open the help menu");
            ItemStack trade = createMenuItem(Material.GOLD_INGOT, ChatColor.GREEN + "Trade", ChatColor.LIGHT_PURPLE + "Open the trade menu");

            ItemStack globe = createCustomHeadItem("Globe", ChatColor.GREEN + "Warps", ChatColor.LIGHT_PURPLE + "Open the warp menu");

            ItemStack staff = createMenuItem(Material.REDSTONE_TORCH, ChatColor.GREEN + "Staff", ChatColor.LIGHT_PURPLE + "View the server staff");

            ItemStack[] menu_items = {globe, tpa, trade,staff,help};
            gui.setContents(menu_items);
            player.openInventory(gui);
        }

        return true;
    }

    private ItemStack createMenuItem(Material material, String displayName, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack createCustomHeadItem(String texture, String displayName, String lore) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setDisplayName(displayName);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        skullMeta.setLore(itemLore);
        skullMeta.setOwner(texture);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}
