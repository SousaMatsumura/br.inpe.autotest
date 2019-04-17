/*
 * Tutorial:
 * https://stackoverflow.com/questions/48555825/antlr4-is-there-a-simple-example-of-using-the-parsetree-walker
 *
 */

package parser;

import model.Project;

public class MethodSignatureListener extends Java8BaseListener {
   int methodNumber, constructorNumber;
   Project project;

   static final String COMMA = ",";

   public MethodSignatureListener() {
      methodNumber = 0;
      constructorNumber = 0;
   }

   @Override
   public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx){
      methodNumber++;
      System.out.println("Enter Method Declaration "+ methodNumber +": ");
      System.out.println(ctx.getText());
      for(Java8Parser.MethodModifierContext mod : ctx.methodModifier()){
         System.out.print(mod.getText()+" ");
      }
      System.out.print(ctx.methodHeader().result().getText()+" ");//return
      System.out.print(ctx.methodHeader().methodDeclarator().getChild(0).getText()+" (");//name

      if(ctx.methodHeader().methodDeclarator().formalParameterList() != null) {
         if (ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters() != null) {

            for (Java8Parser.FormalParameterContext param : ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters().formalParameter()) {
               for (int i = 0; i < param.getChildCount(); i++) {
                  System.out.print(param.getChild(i).getText() +" ");
               }
               System.out.print(", ");
            }
         }

         if (ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter() != null) {
            for (int i = 0; i < ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChildCount(); i++) {
               System.out.print(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText() +
                     " ");
            }
         }else{
            for (int i = 0; i < ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChildCount(); i++) {
               System.out.print(ctx.methodHeader().methodDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText() +
                     " ");
            }
         }
      }
      System.out.print(")");
      if(ctx.methodHeader().throws_() != null && ctx.methodHeader().throws_().exceptionTypeList() != null) {
         System.out.print("throws ");
         for (int i = 0, s = ctx.methodHeader().throws_().exceptionTypeList().getChildCount(); i < s; i++) {
            if(!ctx.methodHeader().throws_().exceptionTypeList().getChild(i).getText().equals(COMMA)) {
               if (i != s - 1) {
                  System.out.print(ctx.methodHeader().throws_().exceptionTypeList().getChild(i).getText() +
                        ", ");
               } else {
                  System.out.print(ctx.methodHeader().throws_().exceptionTypeList().getChild(i).getText() +
                        " ");
               }
            }
         }
      }
      System.out.println();
   }

   @Override
   public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx){
      System.out.println("Exit Method Declaration "+ methodNumber +".\n");
   }

   @Override
   public void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
      constructorNumber++;
      System.out.println("Enter Constructor Declaration "+ constructorNumber +":");
      System.out.println(ctx.getText());
      for(Java8Parser.ConstructorModifierContext mod : ctx.constructorModifier()){
         System.out.print(mod.getText()+" ");
      }
      System.out.print(ctx.constructorDeclarator().simpleTypeName().getText()+" (");//name

      if(ctx.constructorDeclarator().formalParameterList() != null) {
         if (ctx.constructorDeclarator().formalParameterList().formalParameters() != null) {

            for (Java8Parser.FormalParameterContext param : ctx.constructorDeclarator().formalParameterList().formalParameters().formalParameter()) {
               for (int i = 0; i < param.getChildCount(); i++) {
                  System.out.print(param.getChild(i).getText() +" ");
               }
               System.out.print(", ");
            }
         }

         if (ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter() != null) {
            for (int i = 0; i < ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChildCount(); i++) {
               System.out.print(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().formalParameter().getChild(i).getText() +
                     " ");
            }
         }else{
            for (int i = 0; i < ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChildCount(); i++) {
               System.out.print(ctx.constructorDeclarator().formalParameterList().lastFormalParameter().getChild(i).getText() +
                     " ");
            }
         }

      }
      System.out.print(")");
      if(ctx.throws_() != null && ctx.throws_().exceptionTypeList() != null) {
         System.out.print("throws ");
         for (int i = 0, s = ctx.throws_().exceptionTypeList().getChildCount(); i < s; i++) {
            if (!ctx.throws_().exceptionTypeList().getChild(i).getText().equals(COMMA)){
               if (i != s - 1) {
                  System.out.print(ctx.throws_().exceptionTypeList().getChild(i).getText() +
                        ", ");
               } else {
                  System.out.print(ctx.throws_().exceptionTypeList().getChild(i).getText() +
                        " ");
               }
            }
         }
      }
      System.out.println();
   }

   @Override
   public void exitConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx){
      System.out.println("Exit Constructor Declaration "+ constructorNumber +".\n");
   }
}
