/*
 * Fireworks display on levelling up.
 * Copyright (C) 2012 Andrew Stevanus (Hoot215) <hoot893@gmail.com>
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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class LevelFlarePlayerListener implements Listener
  {
    private LevelFlare plugin;
    
    public LevelFlarePlayerListener(LevelFlare instance)
      {
        plugin = instance;
      }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLevelChange (PlayerLevelChangeEvent event)
      {
        Player player = event.getPlayer();
        
        if (!player.hasPermission("levelflare.flare"))
          return;
        
        FileConfiguration config = plugin.getConfig();
        int multiple = config.getInt("multiple");
        int level = event.getNewLevel();
        
        if (level % multiple == 0)
          {
            for (String s : config.getStringList("fireworks"))
              {
                boolean flicker = config.getBoolean(s + ".flicker");
                boolean trail = config.getBoolean(s + ".trail");
                Type type = Type.valueOf(config.getString(s + ".type"));
                List<Color> colours = new ArrayList<Color>();
                for (String t : config.getStringList(s + ".colours"))
                  {
                    try
                      {
                        colours.add((Color) Color.class.getField(t).get(null));
                      }
                    catch (IllegalArgumentException e)
                      {
                        e.printStackTrace();
                      }
                    catch (IllegalAccessException e)
                      {
                        e.printStackTrace();
                      }
                    catch (NoSuchFieldException e)
                      {
                        e.printStackTrace();
                      }
                    catch (SecurityException e)
                      {
                        e.printStackTrace();
                      }
                  }
                List<Color> fades = new ArrayList<Color>();
                for (String t : config.getStringList(s + ".fades"))
                  {
                    try
                      {
                        fades.add((Color) Color.class.getField(t).get(null));
                      }
                    catch (IllegalArgumentException e)
                      {
                        e.printStackTrace();
                      }
                    catch (IllegalAccessException e)
                      {
                        e.printStackTrace();
                      }
                    catch (NoSuchFieldException e)
                      {
                        e.printStackTrace();
                      }
                    catch (SecurityException e)
                      {
                        e.printStackTrace();
                      }
                  }
                Firework firework =
                    (Firework) player.getWorld().spawnEntity(
                        player.getLocation(), EntityType.FIREWORK);
                FireworkMeta meta = firework.getFireworkMeta();
                FireworkEffect effect =
                    FireworkEffect.builder().flicker(flicker).trail(trail)
                        .with(type).withColor(colours).withFade(fades).build();
                meta.addEffect(effect);
                firework.setFireworkMeta(meta);
              }
          }
      }
  }
