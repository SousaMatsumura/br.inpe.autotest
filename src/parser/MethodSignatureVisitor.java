package parser;

import model.Method;
import model.Modifiers;
import model.Variable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class MethodSignatureVisitor extends Java8BaseVisitor<Set<Method>>{
   static final String COMMA = ",", ELLIPSIS = "...";

   @Override
   public Set<Method> visitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
      final Set<Method> result = new HashSet<>();
      if(ctx != null){
         String name, returns;
         final Set<Variable> parameters = new HashSet<>();
         final Set<Integer> modifiers = new HashSet<>();
         final Set<String> exceptions = new HashSet<>();

         returns = ctx.methodHeader().result().getText();
         name = ctx.methodHeader().methodDeclarator().getChild(0).getText();

         for(Java8Parser.MethodModifierContext mod : ctx.methodModifier()){
            modifiers.add(Modifiers.getIndex(mod.getText()));
         }

         //Handle Parameters
         if(ctx.methodHeader().methodDeclarator().formalParameterList() != null) {
            if (ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters() != null) {
               for (Java8Parser.FormalParameterContext param : ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters().formalParameter()) {
                  String paramName = null, dataKind = null;
                  Set<Integer> paramModifiers = new HashSet<>();
                  for(int i = 0, s = param.getChildCount(); i<s; i++) {
                     if (param.getChild(i) instanceof Java8Parser.UnannTypeContext) {
                        dataKind = param.getChild(i).getText();
                     }else if(param.getChild(i) instanceof Java8Parser.VariableModifierContext){
                        paramModifiers.add(Modifiers.getIndex(param.getChild(i).getText()));
                     }else if(param.getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                        paramName = param.getChild(i).getText();
                     }
                  }
                  parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
               }
            }

            if (ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter() != null) {
               String paramName = null, dataKind = null;
               Set<Integer> paramModifiers = new HashSet<>();
               for(int i = 0; i<ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChildCount(); i++) {
                  if (ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.UnannTypeContext) {
                     dataKind = ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText();
                  }else if(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.VariableModifierContext){
                     paramModifiers.add(Modifiers.getIndex(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText()));
                  }else if(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                     paramName = ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText();
                  }
               }
               parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
            }else{
               String paramName = null, dataKind = null;
               Set<Integer> paramModifiers = new HashSet<>();
               for(int i = 0; i<ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChildCount(); i++) {
                  if (ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.UnannTypeContext) {
                     dataKind = ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText();
                  }else if(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.VariableModifierContext){
                     paramModifiers.add(Modifiers.getIndex(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText()));
                  }else if(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                     paramName = ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText();
                  }else if(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText().equals(ELLIPSIS)){
                     dataKind += ELLIPSIS;
                  }
               }
               parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
            }
         }

         //Handle Exceptions
         if(ctx.methodHeader().throws_() != null && ctx.methodHeader().throws_().exceptionTypeList() != null) {
            for (int i = 0, s = ctx.methodHeader().throws_().exceptionTypeList().getChildCount(); i < s; i++) {
               if (!ctx.methodHeader().throws_().exceptionTypeList().getChild(i).getText().equals(COMMA)) {
                  exceptions.add(ctx.methodHeader().throws_().exceptionTypeList().getChild(i).getText());
               }
            }
         }

         final Method method = new Method.Builder(name).returns(returns).parameters(parameters).modifiers(modifiers)
            .exceptions(exceptions).build();
         result.add(method);
      }
      return result;
   }


   @Override
   public Set<Method> visitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
      //Handle Modifiers
      final Set<Method> result = new HashSet<>();
      final Set<Integer> modifiers = new HashSet<>();
      final Set<Variable> parameters = new HashSet<>();
      final Set<String> exceptions = new HashSet<>();

      if(ctx.constructorModifier() != null){
         for (Java8Parser.ConstructorModifierContext mod : ctx.constructorModifier()) {
            modifiers.add(Modifiers.getIndex(mod.getText()));
         }
      }

      //Handle Constructor Declarator
      if(ctx.constructorDeclarator() != null){
         String name;
         name = ctx.constructorDeclarator().simpleTypeName().getText();

         //Handle Parameters
         if(ctx.constructorDeclarator().formalParameterList() != null) {
            if (ctx.constructorDeclarator().formalParameterList().formalParameters() != null) {
               for (Java8Parser.FormalParameterContext param : ctx.constructorDeclarator().formalParameterList().formalParameters().formalParameter()) {
                  String paramName = null, dataKind = null;
                  Set<Integer> paramModifiers = new HashSet<>();
                  for(int i = 0, s = param.getChildCount(); i<s; i++) {
                     if (param.getChild(i) instanceof Java8Parser.UnannTypeContext) {
                        dataKind = param.getChild(i).getText();
                     }else if(param.getChild(i) instanceof Java8Parser.VariableModifierContext){
                        paramModifiers.add(Modifiers.getIndex(param.getChild(i).getText()));
                     }else if(param.getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                        paramName = param.getChild(i).getText();
                     }
                  }
                  parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
               }
            }

            if (ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter() != null) {
               String paramName = null, dataKind = null;
               Set<Integer> paramModifiers = new HashSet<>();
               for(int i = 0; i<ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChildCount(); i++) {
                  if (ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.UnannTypeContext) {
                     dataKind = ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText();
                  }else if(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.VariableModifierContext){
                     paramModifiers.add(Modifiers.getIndex(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText()));
                  }else if(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                     paramName = ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText();
                  }
               }
               parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
            }else{
               String paramName = null, dataKind = null;
               Set<Integer> paramModifiers = new HashSet<>();
               for(int i = 0; i<ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChildCount(); i++) {
                  if (ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.UnannTypeContext) {
                     dataKind = ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText();
                  }else if(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.VariableModifierContext){
                     paramModifiers.add(Modifiers.getIndex(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText()));
                  }else if(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i) instanceof Java8Parser.VariableDeclaratorIdContext){
                     paramName = ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText();
                  }else if(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText().equals(ELLIPSIS)){
                     dataKind += ELLIPSIS;
                  }
               }
               parameters.add(new Variable.Builder(dataKind, paramName).modifiers(paramModifiers).build());
            }
         }

         //Handle Exceptions
         if(ctx.throws_() != null && ctx.throws_().exceptionTypeList() != null) {
            for (int i = 0, s = ctx.throws_().exceptionTypeList().getChildCount(); i < s; i++) {
               if (!ctx.throws_().exceptionTypeList().getChild(i).getText().equals(COMMA)) {
                  exceptions.add(ctx.throws_().exceptionTypeList().getChild(i).getText());
               }
            }
         }
         final Method method = new Method.Builder(name).parameters(parameters).modifiers(modifiers)
            .exceptions(exceptions).build();
         result.add(method);
      }
      return result;
   }

   @Override
   protected Set<Method> aggregateResult(Set<Method> aggregate, Set<Method> nextResult) {
      if(aggregate == null){
         return nextResult;
      }
      if(nextResult == null){
         return aggregate;
      }
      final Set<Method> result = new HashSet<>();
      result.addAll(aggregate);
      result.addAll(nextResult);
      return result;
   }
}
