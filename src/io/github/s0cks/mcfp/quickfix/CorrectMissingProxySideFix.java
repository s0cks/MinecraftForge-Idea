package io.github.s0cks.mcfp.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class CorrectMissingProxySideFix
implements LocalQuickFix{
  private final boolean isClient;

  public CorrectMissingProxySideFix(boolean isClient){
    this.isClient = isClient;
  }

  @Nls
  @NotNull
  @Override
  public String getName() {
    return "Correct Missing Proxy Side Fix";
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return "Minecraft Forge";
  }

  @Override
  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
    PsiAnnotation proxyAnnot = ((PsiAnnotation) problemDescriptor.getPsiElement());
    String className = Messages.showInputDialog(this.isClient ? "Client Side Name" : "Server Side Name", "Inject Side Name", Messages.getQuestionIcon());
    proxyAnnot.setDeclaredAttributeValue(this.isClient ? "clientSide" : "serverSide", PsiElementFactory.SERVICE.getInstance(project).createExpressionFromText('"' + className + '"', PsiType.getJavaLangString(PsiManager.getInstance(project), GlobalSearchScope.projectScope(project)).resolve()));
  }
}