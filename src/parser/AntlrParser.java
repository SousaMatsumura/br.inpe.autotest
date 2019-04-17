package parser;

import model.Method;
import model.Variable;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import org.antlr.v4.runtime.tree.ParseTreeWalker;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AntlrParser {
   public static void main(String[] args) throws IOException {
      CharStream charStream = CharStreams.fromFileName("C:\\core\\Estudante.java");
      Java8Lexer java8Lexer = new Java8Lexer(charStream);
      CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
      Java8Parser java8Parser = new Java8Parser(commonTokenStream);

      ParseTree tree = java8Parser.compilationUnit();

      //new ClassSignatureVisitor().visit(tree);


      Variable var = new Variable.Builder("Object...", "args").build();
      Variable var1 = new Variable.Builder("boolean", "b").build();
      //Method met = new Method.Builder("get").parameters(new HashSet<>().addAll(Array.toSet(new Variable[]{var}))).build();
      Set<Variable> set = new HashSet<>();
      set.add(var);
      set.add(var1);
      Method met = new Method.Builder("get").parameters(new HashSet<Variable>(set)).build();


      System.out.println(met.getMethodSignature());

      /*Set<Method> methods = new MethodSignatureVisitor().visit(tree);

      for(Method m : methods){
         System.out.println(m);
      }*/

      /*MethodSignatureListener extractor = new MethodSignatureListener();
      ParseTreeWalker.DEFAULT.walk(extractor, tree);*/

   }
}
