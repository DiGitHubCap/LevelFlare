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

import java.io.IOException;

import me.Hoot215.LevelFlare.metrics.BukkitMetrics;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelFlare extends JavaPlugin
  {
    private LevelFlarePlayerListener playerListener;
    
    public boolean onCommand (CommandSender sender, Command cmd,
      String commandLabel, String[] args)
      {
        if (cmd.getName().equals("levelflare"))
          {
            if (sender.hasPermission(cmd.getPermission()))
              {
                if (args.length == 0)
                  {
                    sender.sendMessage(ChatColor.GREEN + "/" + commandLabel
                        + ChatColor.WHITE + " - Displays LevelFlare help");
                    sender.sendMessage(ChatColor.GREEN + "/" + commandLabel
                        + " reload" + ChatColor.WHITE
                        + " - Reloads config file");
                    return true;
                  }
                if (args.length == 1)
                  {
                    if (args[0].equalsIgnoreCase("reload"))
                      {
                        if (sender.hasPermission("levelflare.reload"))
                          {
                            this.reloadConfig();
                            sender.sendMessage(ChatColor.GREEN
                                + "Config reloaded!");
                            return true;
                          }
                        sender.sendMessage(cmd.getPermissionMessage());
                        return true;
                      }
                    else
                      return false;
                  }
                return false;
              }
            sender.sendMessage(cmd.getPermissionMessage());
            return true;
          }
        return false;
      }
    
    @Override
    public void onDisable ()
      {
        this.getLogger().info("Is now disabled");
      }
    
    @Override
    public void onEnable ()
      {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        String version = this.getServer().getVersion();
        int start = version.lastIndexOf("-b") + 2;
        int stop = version.lastIndexOf("jnks");
        int build = 0;
        try
          {
            build = Integer.valueOf(version.substring(start, stop));
            if (build < 2565)
              {
                if (this.getConfig().getBoolean("allow-old-build"))
                  {
                    this.getLogger().warning(
                        "You are running an old version of Craftbukkit "
                            + "but have forced the plugin to load");
                    this.getLogger().warning(
                        "The plugin may not work correctly");
                  }
                else
                  {
                    this.getLogger()
                        .severe(
                            "You seem to be running an old "
                                + "version of Craftbukkit! Build 2565 or higher is "
                                + "required to use this plugin!");
                    this.getLogger()
                        .severe(
                            "Disabling plugin! You can force "
                                + "the plugin to load by setting allow-old-build to "
                                + "true in the config file");
                    this.getServer().getPluginManager().disablePlugin(this);
                    return;
                  }
              }
          }
        catch (NumberFormatException e)
          {
            this.getLogger()
                .warning(
                    "You seem to be running an unusual version "
                        + "of Craftbukkit");
            this.getLogger().warning(
                "Please make sure that it is compatible with the changes "
                    + "from build 2565+!");
          }
        catch (IndexOutOfBoundsException e)
          {
            this.getLogger()
                .warning(
                    "You seem to be running an unusual version "
                        + "of Craftbukkit");
            this.getLogger().warning(
                "Please make sure that it is compatible with the changes "
                    + "from build 2565+!");
          }
        this.getLogger().info("Detected build " + String.valueOf(build));
        
        playerListener = new LevelFlarePlayerListener(this);
        this.getServer().getPluginManager()
            .registerEvents(playerListener, this);
        
        try
          {
            BukkitMetrics metrics = new BukkitMetrics(this);
            metrics.start();
          }
        catch (IOException e)
          {
            e.printStackTrace();
          }
        
        this.getLogger().info("Is now enabled");
      }
  }
