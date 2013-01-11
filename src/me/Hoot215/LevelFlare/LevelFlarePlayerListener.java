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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final Color[] COLOURS = {Color.AQUA, Color.BLACK,
        Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME,
        Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE,
        Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};
    
    public LevelFlarePlayerListener(LevelFlare instance)
      {
        plugin = instance;
      }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLevelChange (PlayerLevelChangeEvent event)
      {
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
            for (String s : config.getStringList("fireworks"))
              {
                boolean flicker;
                boolean trail;
                Type type;
                List<Color> colours = new ArrayList<Color>();
                List<Color> fades = new ArrayList<Color>();
                if (s.equals("RANDOM"))
                  {
                    flicker = new Random().nextBoolean();
                    trail = new Random().nextBoolean();
                    type =
                        Type.values()[new Random()
                            .nextInt(Type.values().length)];
                    for (int i = 0; i < 3; i++)
                      {
                        colours.add(COLOURS[new Random()
                            .nextInt(COLOURS.length)]);
                        fades
                            .add(COLOURS[new Random().nextInt(COLOURS.length)]);
                      }
                  }
                else
                  {
                    if (config.getConfigurationSection(s) == null)
                      {
                        plugin.getLogger().warning(
                            "Firework '" + s
                                + "' does not exist in the config!");
                        continue;
                      }
                    flicker = config.getBoolean(s + ".flicker");
                    trail = config.getBoolean(s + ".trail");
                    String typeString = config.getString(s + ".type");
                    try
                      {
                        type = Type.valueOf(typeString);
                      }
                    catch (IllegalArgumentException e)
                      {
                        plugin.getLogger().warning(
                            "Invalid type specified for firework '" + s + "'!");
                        plugin.getLogger().warning("Using BALL instead");
                        type = Type.BALL;
                      }
                    catch (NullPointerException e)
                      {
                        plugin.getLogger().warning(
                            "Invalid type specified for firework '" + s + "'!");
                        plugin.getLogger().warning("Using BALL instead");
                        type = Type.BALL;
                      }
                    for (String t : config.getStringList(s + ".colours"))
                      {
                        try
                          {
                            colours.add((Color) Color.class.getField(t).get(
                                null));
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
                            plugin.getLogger().warning(
                                "Invalid colour '" + t
                                    + "' specified for firework '" + s + "'!");
                            plugin.getLogger().warning("Using WHITE instead");
                            colours.add(Color.WHITE);
                          }
                        catch (SecurityException e)
                          {
                            e.printStackTrace();
                          }
                      }
                    for (String t : config.getStringList(s + ".fades"))
                      {
                        try
                          {
                            fades
                                .add((Color) Color.class.getField(t).get(null));
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
                            plugin.getLogger().warning(
                                "Invalid fade '" + t
                                    + "' specified for firework '" + s + "'!");
                            plugin.getLogger().warning("Using WHITE instead");
                            fades.add(Color.WHITE);
                          }
                        catch (SecurityException e)
                          {
                            e.printStackTrace();
                          }
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
