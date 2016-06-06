package io.github.s0cks.mcfp;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import io.github.s0cks.mcfp.module.ForgeModuleBuilder;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public final class ForgeModuleType
extends ModuleType<ForgeModuleBuilder>{
  public static ModuleType getInstance(){
    return ModuleTypeManager.getInstance().findByID(FORGE_MODULE);
  }

  @NonNls
  public static final String FORGE_MODULE = "FORGE_MODULE";

  public ForgeModuleType(){
    super(FORGE_MODULE);
  }

  @NotNull
  @Override
  public ForgeModuleBuilder createModuleBuilder() {
    return new ForgeModuleBuilder();
  }

  @NotNull
  @Override
  public String getName() {
    return "Minecraft Forge Module";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Minecraft Forge modules are used for developing Minecraft mods that use Minecraft Forge to load them";
  }

  @Override
  public Icon getBigIcon() {
    return null;
  }

  @Override
  public Icon getNodeIcon(@Deprecated boolean b) {
    return null;
  }
}