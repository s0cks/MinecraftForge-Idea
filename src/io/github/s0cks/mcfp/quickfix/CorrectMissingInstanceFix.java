package io.github.s0cks.mcfp.quickfix;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class CorrectMissingInstanceFix
implements LocalQuickFix{
  private final String modid;

  public CorrectMissingInstanceFix(String modid){
    this.modid = modid;
  }

  @Nls
  @NotNull
  @Override
  public String getName() {
    return "Create Missing Instance";
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return "Minecraft Forge";
  }

  @Override
  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
    PsiClass modClass = ((PsiClass) problemDescriptor.getPsiElement());

    GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
    PsiManager psiManager = PsiManager.getInstance(project);
    PsiElementFactory psiFactory = PsiElementFactory.SERVICE.getInstance(project);

    PsiField field = psiFactory.createField("instance", PsiType.getTypeByName(modClass.getQualifiedName(), project, scope));
    field.getModifierList().setModifierProperty("static", true);
    field.getModifierList().setModifierProperty("public", true);

    PsiAnnotation instanceAnnot = field.getModifierList().addAnnotation("net.minecraftforge.fml.common.Mod.Instance");
    instanceAnnot.setDeclaredAttributeValue("value", psiFactory.createExpressionFromText(this.modid, PsiType.getJavaLangString(psiManager, scope).resolve()));

    modClass.addBefore(field, modClass.getFields()[0]);
  }
}