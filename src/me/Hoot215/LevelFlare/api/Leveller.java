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

package me.Hoot215.LevelFlare.api;

import java.util.logging.Logger;

import me.Hoot215.LevelFlare.LevelFlare;

public interface Leveller
  {
    public void initialize (LevelFlare instance);
    
    public void makeLogger ();
    
    public void onLoad ();
    
    public void onUnload ();
    
    public String getName ();
    
    public String getVersion ();
    
    public void onPlayerLevelChange (LevelFlarePlayerLevelChangeEvent event);
    
    public Logger getLogger ();
    
    public LevelFlare getPlugin ();
  }
