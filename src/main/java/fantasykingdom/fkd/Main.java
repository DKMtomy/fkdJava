package fantasykingdom.fkd;

import fantasykingdom.fkd.commands.FlySpeedCommand;
import fantasykingdom.fkd.commands.GUIcomand;
import fantasykingdom.fkd.commands.SetRankCommand;
import fantasykingdom.fkd.commands.flyCommand;
import fantasykingdom.fkd.events.ChatListener;
import fantasykingdom.fkd.events.ClickEvent;
import fantasykingdom.fkd.managers.RankManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin  implements Listener {
    private RankManager rankManager;
    @Override
    public void onEnable() {
        rankManager = new RankManager(this);
        // Plugin startup logic

        System.out.println("FantasyKingdom has started!");

        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(rankManager), this);

        getCommand("gui").setExecutor(new GUIcomand());
        getCommand("fly").setExecutor(new flyCommand());
        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("setrank").setExecutor(new SetRankCommand(rankManager));


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("a player has joined");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("FantasyKingdom has stopped");
    }
}
