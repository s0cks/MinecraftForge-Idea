package io.github.s0cks.mcfp.actions;

import com.intellij.openapi.actionSystem.DefaultActionGroup;

public final class MinecraftForgeActionGroup
extends DefaultActionGroup{
  public MinecraftForgeActionGroup(){
    super("Minecraft Forge", true);
    this.getTemplatePresentation().setDescription("Minecraft Forge Templates");
    this.getTemplatePresentation().setVisible(true);
  }
}