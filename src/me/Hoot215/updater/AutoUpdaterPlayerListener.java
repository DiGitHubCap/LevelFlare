/*
 * Fireworks display on levelling up.
 * Copyright (C) 2013 Andrew Stevanus (Hoot215) <hoot893@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
                            if (!site.isEmpty())
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
