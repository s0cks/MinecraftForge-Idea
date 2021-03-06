package io.github.s0cks.mcfp.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class CorrectInstanceFix
implements LocalQuickFix {
  private final String modid;

  public CorrectInstanceFix(String modid) {
    this.modid = modid;
  }

  @Nls
  @NotNull
  @Override
  public String getName() {
    return "Correct Instance";
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return "Minecraft Forge";
  }

  @Override
  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
    GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
    PsiManager psiManager = PsiManager.getInstance(project);
    PsiElementFactory psiFactory = PsiElementFactory.SERVICE.getInstance(project);
    PsiAnnotation instanceAnnot = ((PsiAnnotation) problemDescriptor.getPsiElement());
    instanceAnnot.setDeclaredAttributeValue("value", psiFactory.createExpressionFromText(this.modid, PsiType.getJavaLangString(psiManager, scope).resolve()));
  }
}