package fantasykingdom.fkd.managers;
import fantasykingdom.fkd.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fantasykingdom.fkd.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class RankManager {
    private Map<String, List<String>> playerRanks;
    private File rankDataFile;
    private Gson gson;

    private Main plugin;

    public RankManager(Main plugin) {
        playerRanks = new HashMap<>();
        rankDataFile = new File("rankdata.json"); // Adjust the file name and path as needed
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadRankData();

        this.plugin = plugin;
        startRankUpdateTask();
        updatePlayerRanksOnJoin();
    }

    public void addRank(Player player, String rank) {
        List<String> ranks = playerRanks.getOrDefault(player.getUniqueId().toString(), new ArrayList<>());
        ranks.add(rank);
        playerRanks.put(player.getUniqueId().toString(), ranks);
        updatePlayerRank(player, ranks);
        saveRankData();
    }

    public void removeRank(Player player, String rank) {
        List<String> ranks = playerRanks.get(player.getUniqueId().toString());
        if (ranks != null) {
            ranks.remove(rank);
            updatePlayerRank(player, ranks);
            saveRankData();
        }
    }

    public void updatePlayerRanks() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            List<String> ranks = playerRanks.getOrDefault(player.getUniqueId().toString(), new ArrayList<>());
            updatePlayerRank(player, ranks);
        }
    }

    public List<String> getRanks(Player player) {
        return playerRanks.getOrDefault(player.getUniqueId().toString(), new ArrayList<>());
    }

    public String getRank(Player player) {
        List<String> ranks = getRanks(player);
        if (!ranks.isEmpty()) {
            return ranks.get(0);
        } else {
            return "kdloos"; // Default rank when no ranks are assigned
        }
    }

    private void updatePlayerRank(Player player, List<String> ranks) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        // Clear existing prefixes
        for (String entry : team.getEntries()) {
            team.removeEntry(entry);
        }

        // Set ranks as prefixes
        for (String rank : ranks) {
            team.addEntry(rank);
        }

        Objective objective = scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
        if (objective != null) {
            objective.getScore(player.getName()).setScore((int) player.getHealthScale()); // Set a unique score to update the player list
        }

        player.setPlayerListName(formatPlayerListName(player, ranks));
    }

    private void removePlayerRank(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam(player.getName());
        if (team != null) {
            team.unregister();
        }

        player.setPlayerListName(player.getName());
    }

    private String formatPlayerListName(Player player, List<String> ranks) {
        StringBuilder builder = new StringBuilder();
        for (String rank : ranks) {
            ChatColor color = ChatColor.WHITE; // Default color
            if (rank.length() > 2 && rank.charAt(0) == '&') {
                ChatColor rankColor = ChatColor.getByChar(rank.charAt(1));
                if (rankColor != null) {
                    color = rankColor;
                    rank = rank.substring(2);
                }
            }
            builder.append("[").append(color).append(rank).append(ChatColor.RESET).append("] ");
        }
        builder.append(player.getName());
        return builder.toString();
    }

    private void loadRankData() {
        if (!rankDataFile.exists()) {
            return;
        }

        try (Reader reader = new FileReader(rankDataFile)) {
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            playerRanks = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRankData() {
        try (Writer writer = new FileWriter(rankDataFile)) {
            gson.toJson(playerRanks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startRankUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Update ranks from file
                // Add your code here to load the ranks from a file and update the playerRanks map
            }
        }.runTaskTimerAsynchronously(plugin, 0, 100); // Update every 5 seconds (100 ticks)
    }


    private void updatePlayerRanksOnJoin() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            List<String> ranks = playerRanks.getOrDefault(player.getUniqueId().toString(), new ArrayList<>());
            updatePlayerRank(player, ranks);
        });
    }
}
