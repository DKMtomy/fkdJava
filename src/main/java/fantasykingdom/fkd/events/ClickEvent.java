package fantasykingdom.fkd.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickEvent implements Listener {

    private final Map<String, String> staffMembers;

    public ClickEvent() {
        staffMembers = new HashMap<>();
        staffMembers.put("vekqi", "Developer");
        staffMembers.put("seagod", "Developer");
        staffMembers.put("jornTheOnlyOne", "Owner");
        staffMembers.put("OxeyeNLD", "Builder");
        staffMembers.put("recovered", "Builder");
        staffMembers.put("Kokushibo", "Moderator");
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        String guiTitle = ChatColor.AQUA + "Fantasy Kingdom Gui";
        String staffGuiTitle = ChatColor.AQUA + "Staff Members";

        if (e.getView().getTitle().equals(guiTitle)) {
            e.setCancelled(true);

            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            switch (clickedItem.getType()) {
                case PLAYER_HEAD:
                    SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                    if (skullMeta != null && skullMeta.hasOwner() && skullMeta.getOwner() != null && skullMeta.getOwner().equals("Globe")) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.GREEN + "You have chosen warps!");
                    }
                    break;
                case REDSTONE_TORCH:
                    openStaffGUI(player);
                    break;
            }
        } else if (e.getView().getTitle().equals(staffGuiTitle)) {
            e.setCancelled(true);

            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.RED_WOOL) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "You have closed the staff GUI.");
            }
        }
    }

    private void openStaffGUI(Player player) {
        Inventory staffGUI = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Staff Members");

        for (String staffName : staffMembers.keySet()) {
            ItemStack staffItem = createMenuItem(Material.PLAYER_HEAD, ChatColor.GREEN + staffName, staffMembers.get(staffName));
            staffGUI.addItem(staffItem);
        }

        ItemStack exitButton = createMenuItem(Material.RED_WOOL, ChatColor.RED + "Exit", ChatColor.LIGHT_PURPLE + "Click to exit the staff GUI");
        staffGUI.setItem(8, exitButton);

        player.openInventory(staffGUI);
    }

    private ItemStack createMenuItem(Material material, String displayName, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        List<String> itemLore = new ArrayList<>();
        itemLore.add(ChatColor.LIGHT_PURPLE + lore);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
