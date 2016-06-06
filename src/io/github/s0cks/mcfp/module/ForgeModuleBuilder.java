package io.github.s0cks.mcfp.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import io.github.s0cks.mcfp.ForgeModuleType;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JLabel;

public final class ForgeModuleBuilder
extends ModuleBuilder{
  @Override
  public void setupRootModel(ModifiableRootModel model)
  throws ConfigurationException {
    if(this.myJdk != null) model.setSdk(this.myJdk);
    else model.inheritSdk();
  }

  @Nullable
  @Override
  public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
    return new ForgeWizardStep();
  }

  @Override
  public ModuleType getModuleType() {
    return ForgeModuleType.getInstance();
  }

  private static final class ForgeWizardStep
  extends ModuleWizardStep{
    @Override
    public JComponent getComponent() {
      return new JLabel("Hello World");
    }

    @Override
    public void updateDataModel() {

    }
  }
}