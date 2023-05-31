package fantasykingdom.fkd.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class ClickEvent  implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e){

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(ChatColor.AQUA + "Fantasy Kingdom Gui")) {

            switch (e.getCurrentItem().getType()){
                case COMPASS:
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "you have choosen warps!");
                    break;
            }

            e.setCancelled(true);
        }
    }
}
