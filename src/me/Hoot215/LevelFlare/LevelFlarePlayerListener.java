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

package me.Hoot215.LevelFlare;

import me.Hoot215.LevelFlare.api.LevelFlarePlayerLevelChangeEvent;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelFlarePlayerListener implements Listener
  {
    private LevelFlare plugin;
    
    public LevelFlarePlayerListener(LevelFlare instance)
      {
        plugin = instance;
      }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLevelChange (PlayerLevelChangeEvent event)
      {
        LevelFlarePlayerLevelChangeEvent levelFlareEvent =
            new LevelFlarePlayerLevelChangeEvent(event);
        plugin.getLevellerManager().onPlayerLevelChangeEvent(levelFlareEvent);
        if (levelFlareEvent.isCancelled())
          return;
        
        Player player = event.getPlayer();
        if ( !player.hasPermission("levelflare.flare"))
          return;
        
        FileConfiguration config = plugin.getConfig();
        int multiple = config.getInt("multiple");
        int level = event.getNewLevel();
        
        if (level % multiple == 0)
          {
            String message = config.getString("level-up-message");
            if ( !message.isEmpty())
              player.sendMessage(String.format(message, String.valueOf(level)));
            plugin.launchFireworks(player);
          }
      }
  }
