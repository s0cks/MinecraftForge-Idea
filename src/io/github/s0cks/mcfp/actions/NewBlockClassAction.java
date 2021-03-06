package io.github.s0cks.mcfp.actions;

import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import io.github.s0cks.mcfp.factory.BlockClassCodeFactory;
import org.jetbrains.annotations.NotNull;

public final class NewBlockClassAction
extends CreateElementActionBase{
  public NewBlockClassAction(){
    super("New Block Class", "New Block Class", null);
  }

  @NotNull
  @Override
  protected PsiElement[] invokeDialog(Project project, PsiDirectory psiDirectory) {
    String name = Messages.showInputDialog("New Block Class", "Create Block Class", Messages.getQuestionIcon());
    if(name != null && !name.isEmpty()){
      MyInputValidator validator = new MyInputValidator(project, psiDirectory);
      if(validator.canClose(name)){
        return validator.getCreatedElements();
      }
    }

    return PsiElement.EMPTY_ARRAY;
  }

  @NotNull
  @Override
  protected PsiElement[] create(String s, PsiDirectory psiDirectory)
  throws Exception {
    Project project = psiDirectory.getProject();
    PsiFileFactory factory = PsiFileFactory.getInstance(project);
    String code = BlockClassCodeFactory.createBlockClass(psiDirectory.getName().replace("/", "."), s);
    PsiFile file = factory.createFileFromText("Block" + s + ".java", JavaFileType.INSTANCE, code);
    PsiElement shortFile = JavaCodeStyleManager.getInstance(project).shortenClassReferences(file);
    PsiElement newFile = CodeStyleManager.getInstance(project).reformat(shortFile);
    psiDirectory.add(newFile);
    return new PsiElement[]{
      newFile
    };
  }

  @Override
  protected String getErrorTitle() {
    return null;
  }

  @Override
  protected String getCommandName() {
    return null;
  }

  @Override
  protected String getActionName(PsiDirectory psiDirectory, String s) {
    return null;
  }
}