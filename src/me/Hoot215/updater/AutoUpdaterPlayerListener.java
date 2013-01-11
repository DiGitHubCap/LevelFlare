package me.Hoot215.updater;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoUpdaterPlayerListener implements Listener
  {
    private JavaPlugin plugin;
    private AutoUpdater autoUpdater;
    
    public AutoUpdaterPlayerListener(JavaPlugin instance, AutoUpdater updater)
      {
        plugin = instance;
        autoUpdater = updater;
      }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin (PlayerJoinEvent event)
      {
        if ( !autoUpdater.isUpToDate())
          {
            final String playerName = event.getPlayer().getName();
            plugin.getServer().getScheduler()
                .scheduleSyncDelayedTask(plugin, new Runnable()
                  {
                    public void run ()
                      {
                        Player player =
                            plugin.getServer().getPlayer(playerName);
                        if (player == null)
                          return;
                        if (player.hasPermission(plugin.getName().toLowerCase()
                            + ".update"))
                          {
                            player.sendMessage(ChatColor.GREEN
                                + "A newer version of " + ChatColor.RED
                                + plugin.getName() + ChatColor.GREEN
                                + " is available!" + ChatColor.GRAY + " (v"
                                + ChatColor.DARK_GREEN
                                + autoUpdater.getNewestVersion()
                                + ChatColor.GRAY + ")");
                            String site = autoUpdater.getSite();
                            if (site != null && !site.isEmpty())
                              {
                                player.sendMessage(ChatColor.GREEN
                                    + "Download it here: " + ChatColor.BLUE
                                    + site);
                              }
                          }
                      }
                  }, 60L);
          }
      }
  }
