package parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.*;

public class AntlrParser {
   static final char SEMI_COLON = ';', OPEN_BRACE = '{', CLOSE_BRACE = '}', DOTE = '.';
   static final char SPACE = ' ';

   public static void main(String[] args) throws IOException {
      CharStream charStream = CharStreams.fromFileName("C:\\JavaLib\\Java8\\HelloWorld.java");
      Java8Lexer java8Lexer = new Java8Lexer(charStream);
      CommonTokenStream commonTokenStream = new CommonTokenStream(java8Lexer);
      Java8Parser java8Parser = new Java8Parser(commonTokenStream);

      ParseTree parseTree = java8Parser.compilationUnit();

      printMethods(parseTree);






   }

   static void printTree(ParseTree parseTree){
      for(int i = 0; i < parseTree.getChildCount(); i++){
         if(parseTree.getChild(i) instanceof TerminalNode){
            if(contains(parseTree.getChild(i).getText(), SEMI_COLON) ||
               contains(parseTree.getChild(i).getText(), OPEN_BRACE) ||
               contains(parseTree.getChild(i).getText(), CLOSE_BRACE)){
               System.out.print(parseTree.getChild(i).getText()+CharUtils.LF);
            }else if(contains(parseTree.getChild(i).getText(), DOTE) ||
                  (i+1<parseTree.getChildCount() && contains(parseTree.getChild(1+i).getText(), DOTE))){
               System.out.print(parseTree.getChild(i).getText());
            }else{
               System.out.print(parseTree.getChild(i).getText()+SPACE);
            }
         }else{
            printTree(parseTree.getChild(i));
         }
      }
   }

   static int d = 0;

   static void printMethods(ParseTree parseTree){
      for(int i = 0; i < parseTree.getChildCount(); i++){
         for(int j = 0; j < d; j++){
            System.out.print("   ");
         }

         System.out.print(parseTree.getText()+CharUtils.LF);
         d++;
         printMethods(parseTree.getChild(i));
         d--;
      }
   }


}
