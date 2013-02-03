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

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LevellerLogger extends Logger
  {
    private String levellerName;
    
    public LevellerLogger(Leveller leveller)
      {
        super(leveller.getClass().getCanonicalName(), null);
        levellerName = "[" + leveller.getName() + "] ";
        this.setParent(leveller.getPlugin().getLogger());
        this.setLevel(Level.ALL);
      }
    
    @Override
    public void log (LogRecord logRecord)
      {
        logRecord.setMessage(levellerName + logRecord.getMessage());
        super.log(logRecord);
      }
  }
