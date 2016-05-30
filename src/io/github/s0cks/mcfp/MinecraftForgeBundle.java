package io.github.s0cks.mcfp;

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

public final class MinecraftForgeBundle{
  public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, @NotNull Object... params){
    return CommonBundle.message(getBundle(), key, params);
  }

  @NonNls public static final String BUNDLE = "io.github.s0cks.mcfp.MinecraftForgeBundle";

  private static Reference<ResourceBundle> bundleRef;

  public static ResourceBundle getBundle(){
    ResourceBundle bundle = SoftReference.dereference(bundleRef);
    if(bundle == null){
      bundle = ResourceBundle.getBundle(BUNDLE);
      bundleRef = new SoftReference<>(bundle);
    }
    return bundle;
  }
}