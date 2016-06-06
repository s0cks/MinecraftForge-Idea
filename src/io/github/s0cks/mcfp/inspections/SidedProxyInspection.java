package io.github.s0cks.mcfp.inspections;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ex.BaseLocalInspectionTool;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.s0cks.mcfp.quickfix.CorrectMissingProxySideFix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public final class SidedProxyInspection
extends BaseLocalInspectionTool{
  @Nls
  @NotNull
  @Override
  public String getDisplayName() {
    return "Sided Proxy";
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
    return new SidedProxyVisitor(holder);
  }

  private static final class SidedProxyVisitor
  extends JavaElementVisitor{
    private final ProblemsHolder problems;

    private SidedProxyVisitor(ProblemsHolder problems){
      this.problems = problems;
    }

    @Override
    public void visitClass(PsiClass aClass) {
      if(!AnnotationUtil.isAnnotated(aClass, "net.minecraftforge.fml.common.Mod", false)) return;
      for(PsiField field : aClass.getAllFields()){
        this.visitField(field);
      }
    }

    @Override
    public void visitField(PsiField field) {
      if(!AnnotationUtil.isAnnotated(field, "net.minecraftforge.fml.common.SidedProxy", false)) return;

      PsiAnnotation proxy = AnnotationUtil.findAnnotation(field, "net.minecraftforge.fml.common.SidedProxy");
      String clientSide = proxy.findAttributeValue("clientSide") != null ? proxy.findAttributeValue("clientSide").getText() : null;
      String serverSide = proxy.findAttributeValue("serverSide") != null ? proxy.findAttributeValue("serverSide").getText() : null;

      Project project = field.getProject();
      GlobalSearchScope scope = GlobalSearchScope.projectScope(project);

      PsiClass serverClass = null;
      if(serverSide == null || (serverSide = serverSide.replace("\"", "")).isEmpty()){
        this.problems.registerProblem(proxy, "Missing server side", new CorrectMissingProxySideFix(false));
      } else{
        serverClass = PsiType.getTypeByName(serverSide, project, scope).resolve();
        if(serverClass == null || !serverClass.isValid()){
          // TODO: Register problem
        }
      }

      if(serverClass == null) return;

      if(clientSide == null || (clientSide = clientSide.replace("\"", "")).isEmpty()){
        this.problems.registerProblem(proxy, "Missing client side", new CorrectMissingProxySideFix(true));
      } else{
        PsiClass clientClass = PsiType.getTypeByName(clientSide, project, scope).resolve();
        if(clientClass == null || !clientClass.isValid()){
          // TODO: Register problem
        }

        if(!clientClass.isInheritor(serverClass, true)){
          //TODO: Register problem
        }
      }
    }
  }
}