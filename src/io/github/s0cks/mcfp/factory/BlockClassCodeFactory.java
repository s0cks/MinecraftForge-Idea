package io.github.s0cks.mcfp.factory;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.StringWriter;

public final class BlockClassCodeFactory {
  public static String createBlockClass(String pkg, String name)
  throws IOException {
    try (StringWriter sw = new StringWriter()) {
      TypeSpec blockClass = TypeSpec.classBuilder("Block" + name)
                                    .addModifiers(Modifier.PUBLIC)
                                    .superclass(TypeVariableName.get("net.minecraft.block.Block"))
                                    .addMethod(MethodSpec.constructorBuilder()
                                                         .addModifiers(Modifier.PUBLIC)
                                                         .addStatement("super($T.ROCK)", TypeVariableName.get("net.minecraft.block.material.Material"))
                                                         .build())
                                    .build();
      JavaFile code = JavaFile.builder(pkg, blockClass)
                              .build();
      code.writeTo(sw);
      return sw.toString();
    }
  }
}