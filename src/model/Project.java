package model;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringUtils;
import parser.Java8Lexer;
import parser.Java8Parser;
import parser.MethodSignatureListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Project{
   final String address;
   List<String> javaFiles;
   List<java.lang.Class> classes;

   public Project(String address){
      this.address = address;
      fill(address);
   }

   public String getAddress() {
      return address;
   }

   public List<String> getJavaFiles() {
      return javaFiles;
   }

   public List<java.lang.Class> getClasses() {
      return classes;
   }

   void fill(String address){
      fillJavaFiles(address);
      try{
         fillClasses();
      }catch (IOException e){
         e.printStackTrace();
      }
   }

   void fillJavaFiles(String address){
      Path p = Paths.get(address);
      try(Stream<Path> subPaths = Files.walk(p)){
         javaFiles = subPaths.filter(Files::isRegularFile).filter(path -> {
            String s = path.toAbsolutePath().toString();
            return s.contains(".java");
         }).map(Objects::toString).collect(Collectors.toList());
      }catch (Exception e){
         e.printStackTrace();
      }
   }

   private void fillClasses() throws IOException {
      for(String file : javaFiles){
         CharStream charStream = CharStreams.fromFileName(file);
         Java8Lexer java8Lexer = new Java8Lexer(charStream);
         CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
         Java8Parser java8Parser = new Java8Parser(commonTokenStream);

         ParseTree tree = java8Parser.compilationUnit();

         MethodSignatureListener extractor = new MethodSignatureListener();
         ParseTreeWalker.DEFAULT.walk(extractor, tree);
      }
   }
}
