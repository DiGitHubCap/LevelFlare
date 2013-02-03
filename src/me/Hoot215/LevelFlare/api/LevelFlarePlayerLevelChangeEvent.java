package me.Hoot215.LevelFlare.api;

import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelFlarePlayerLevelChangeEvent
  {
    private PlayerLevelChangeEvent event;
    private boolean cancelled = false;
    
    public LevelFlarePlayerLevelChangeEvent(PlayerLevelChangeEvent event)
      {
        this.event = event;
      }
    
    public PlayerLevelChangeEvent getEvent ()
      {
        return event;
      }
    
    public boolean isCancelled ()
      {
        return cancelled;
      }
    
    public void setCancelled (boolean value)
      {
        cancelled = value;
      }
  }
