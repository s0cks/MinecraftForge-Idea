package io.github.s0cks.mcfp.factory;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.StringWriter;

public final class ItemClassCodeFactory {
  public static String createItemClass(String pkg, String name)
  throws IOException {
    try (StringWriter sw = new StringWriter()) {
      TypeSpec itemClass = TypeSpec.classBuilder("Item" + name)
                                   .addModifiers(Modifier.PUBLIC)
                                   .superclass(TypeVariableName.get("net.minecraft.item.Item"))
                                   .build();
      JavaFile code = JavaFile.builder(pkg, itemClass)
                              .build();
      code.writeTo(sw);
      return sw.toString();
    }
  }
}