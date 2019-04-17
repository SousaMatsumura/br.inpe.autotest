package parser;

import model.*;
import model.Class;
import org.apache.commons.lang3.StringUtils;


import java.util.HashSet;
import java.util.Set;

public class ClassSignatureVisitor extends Java8BaseVisitor<Set<Class>>{
   static final String COMMA = ",";
   private String directory, pack;
   private final Set<String> imports = new HashSet<>();

   public ClassSignatureVisitor(String directory){
      this.directory = directory;
   }

   @Override
   public Set<Class> visitPackageDeclaration(Java8Parser.PackageDeclarationContext ctx) {
      pack = ctx.packageName().getText();
      return null;
   }

   @Override
   public Set<Class> visitImportDeclaration(Java8Parser.ImportDeclarationContext ctx) {
      if(ctx != null){
         imports.add(StringUtils.remove(ctx.getText(),"import"));
      }
      return null;
   }

   @Override
   public Set<Class> visitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
      final Set<Class> result = new HashSet<>();
      final Set<String> implement = new HashSet<>();
      final Set<Method> methods = new HashSet<>();
      final Set<Variable> variables = new HashSet<>();
      final Set<Integer> modifiers = new HashSet<>();
      final Set<Class> innerClasses = new HashSet<>();
      Integer kind = null;
      String name = "", extend = "";
      if(ctx != null){
         int i = 0, s = ctx.getChildCount();
         while(ctx.getChild(i) instanceof Java8Parser.ClassModifierContext){
            modifiers.add(Modifiers.getIndex(ctx.getChild(i).getText()));
            i++;
         }
         if(ClassKind.isClassKind(ctx.getChild(i).getText())){
            kind = ClassKind.getIndex(ctx.getChild(i).getText());
            name = ctx.getChild(i+1).getText();
            i+=2;
         }
         if(ctx.getChild(i) instanceof Java8Parser.SuperclassContext){
            extend = ctx.getChild(i).getChild(1).getText();
            i++;
         }
         if (ctx.getChild(i) instanceof Java8Parser.SuperinterfacesContext){
            Java8Parser.SuperinterfacesContext interfaceList = (Java8Parser.SuperinterfacesContext) ctx.getChild(i);
            for(int j = 0, l = interfaceList.getChildCount(); j<l; j++){
               if(!interfaceList.getChild(j).getText().equals(COMMA)){
                  implement.add(interfaceList.getChild(j).getText());
               }
            }
         }
         if(ctx.classBody() != null){
            methods.addAll(new MethodSignatureVisitor().visit(ctx.classBody()));
         }
      }
      result.add(new Class.Builder(directory, name, kind).imports(imports).pack(pack).extend(extend).implement(implement)
         .methods(methods).build());
      return result;
   }

   @Override
   protected Set<Class> aggregateResult(Set<Class> aggregate, Set<Class> nextResult) {
      if(aggregate == null){
         return nextResult;
      }
      if(nextResult == null){
         return aggregate;
      }
      final Set<Class> result = new HashSet<>();
      result.addAll(aggregate);
      result.addAll(nextResult);
      return result;
   }
   /*@Override
   public Set<Class> visitCompilationUnit(Java8Parser.CompilationUnitContext ctx) {
      System.out.println(ctx.getChildCount());
      return super.visitCompilationUnit(ctx);
   }*/


}
