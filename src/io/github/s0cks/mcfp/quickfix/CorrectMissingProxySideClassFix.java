package io.github.s0cks.mcfp.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class CorrectMissingProxySideClassFix
implements LocalQuickFix{
  @Nls
  @NotNull
  @Override
  public String getName() {
    return null;
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return null;
  }

  @Override
  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {

  }
}