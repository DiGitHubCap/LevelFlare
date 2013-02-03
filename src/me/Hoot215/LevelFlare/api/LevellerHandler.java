package me.Hoot215.LevelFlare.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LevellerHandler
  {
    String name() default "Leveller";
    
    String version() default "1.0";
  }
