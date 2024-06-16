package net.chocorot.cps;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CPSLimiterListener implements Listener {
    private final Map<Player, Queue<Long>> playerClicked = new HashMap<>();
    @EventHandler
    public void onPlayerClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (canClick(player)) {
            event.setCancelled(true);
        }else {
            addPlayer(player);
        }
    }


    public boolean canClick(Player player) {
        long currentTime = System.currentTimeMillis();
        int TIME_WINDOW = Integer.parseInt(Settings.getString("time"));
        int REQUIRED_COUNT = Integer.parseInt(Settings.getString("count"));

        if (!playerClicked.containsKey(player)) {
            playerClicked.put(player, new LinkedList<>());
        }

        Queue<Long> timestamps = playerClicked.get(player);

        // Remove timestamps that are outside the time window
        while (!timestamps.isEmpty() && currentTime - timestamps.peek() > TIME_WINDOW) {
            timestamps.poll();
        }

        // Check if there are at least REQUIRED_COUNT timestamps within the time window
        return timestamps.size() >= REQUIRED_COUNT;
    }

    public void addPlayer(Player player) {
        long currentTime = System.currentTimeMillis();

        if (!playerClicked.containsKey(player)) {
            playerClicked.put(player, new LinkedList<>());
        }

        Queue<Long> timestamps = playerClicked.get(player);
        timestamps.add(currentTime);
    }
}
