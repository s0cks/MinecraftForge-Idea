package io.github.s0cks.mcfp.factory;

import java.io.IOException;
import java.io.StringWriter;

public final class ItemClassCodeFactory{
  public static String createItemClass(String pkg, String name)
  throws IOException {
    try(StringWriter sw = new StringWriter()){
      return sw.toString();
    }
  }
}