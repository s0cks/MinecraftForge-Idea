package io.github.s0cks.mcfp.inspections;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ex.BaseLocalInspectionTool;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class InvalidInstanceInspection
extends BaseLocalInspectionTool{
  @Override
  public boolean isEnabledByDefault() {
    return true;
  }

  @Nls
  @NotNull
  @Override
  public String getDisplayName() {
    return "Invalid Instance";
  }

  @NotNull
  @Override
  public String getShortName() {
    String simpleName = this.getClass().getSimpleName();
    return simpleName.substring(0, simpleName.length() - "Inspection".length());
  }

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
    return new InvalidInstanceVisitor(holder);
  }

  private static final class InvalidInstanceVisitor
  extends JavaElementVisitor{
    private final ProblemsHolder problems;

    private InvalidInstanceVisitor(ProblemsHolder problems){
      this.problems = problems;
    }

    @Override
    public void visitClass(PsiClass aClass) {
      if(!AnnotationUtil.isAnnotated(aClass, "net.minecraftforge.fml.common.Mod", false)){
        return;
      }

      PsiField instance = null;
      for(PsiField field : aClass.getFields()){
        if(AnnotationUtil.isAnnotated(field, "net.minecraftforge.fml.common.Mod.Instance", false)){
          instance = field;
          break;
        }
      }

      if(instance != null){
        PsiAnnotation modAnnot = AnnotationUtil.findAnnotation(aClass, "net.minecraftforge.fml.common.Mod");
        PsiAnnotation instanceAnnot = AnnotationUtil.findAnnotation(instance, "net.minecraftforge.fml.common.Mod.Instance");
        if (!modAnnot.findAttributeValue("modid")
                     .equals(instanceAnnot.findAttributeValue("value"))) {
          this.problems.registerProblem(instance, "Invalid Instance value. Suggested: " + modAnnot.findAttributeValue("modid").getText());
        }
      }
    }
  }
}