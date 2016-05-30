package io.github.s0cks.mcfp.inspections;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ex.BaseLocalInspectionTool;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class MissingInstanceInspection
extends BaseLocalInspectionTool{
  @Override
  public boolean isEnabledByDefault() {
    return true;
  }

  @Nls
  @NotNull
  @Override
  public String getDisplayName() {
    return "Missing Instance";
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
    return new MissingInstanceVisitor(holder);
  }

  private static final class MissingInstanceVisitor
  extends JavaElementVisitor{
    private final ProblemsHolder problems;

    private MissingInstanceVisitor(ProblemsHolder problems){
      this.problems = problems;
    }

    @Override
    public void visitClass(PsiClass aClass) {
      if(!AnnotationUtil.isAnnotated(aClass, "net.minecraftforge.fml.common.Mod", false)){
        return;
      }

      boolean foundInstance = false;
      for(PsiField field : aClass.getFields()){
        if(AnnotationUtil.isAnnotated(field, "net.minecraftforge.fml.common.Mod.Instance", false)){
          foundInstance = true;
          break;
        }
      }

      if(!foundInstance) this.problems.registerProblem(aClass, "Mod class missing instance");
    }
  }
}