package model;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public enum CodeBlock {
   INEQUALITY(1), BRACES(2), BRACKETS(3), PARENTHESES(4);
   private int value;
   public static final String[] STRING_VALUES = stringValues();
   public static final String SEMICOLON = ";", OPEN_COMMENT = "/*", CLOSE_COMMENT = "*/";
   public static final char QUOTES = '"', COMMA = ',';

   CodeBlock(int value) {
      this.value = value;
   }

   public static int getIndex(String value) {
      switch (value) {
         case "<>":
            return 1;
         case "{}":
            return 2;
         case "[]":
            return 3;
         case "()":
            return 4;
         default:
            return 0;
      }
   }

   public static String getValue(int index) {
      switch (index) {
         case 1:
            return "<>";
         case 2:
            return "{}";
         case 3:
            return "[]";
         case 4:
            return "()";
         default:
            return null;
      }
   }

   static boolean hasCodeBlock(final String declaration, final String... codeBlocks) {
      if (declaration != null && declaration.length() > 0) {
         for (String s : codeBlocks) {
            if (StringUtils.contains(declaration, s.charAt(0)) && StringUtils.contains(declaration, s.charAt(1))) {
               return true;
            }
         }
      }
      return false;
   }

   public static String[] getCodeBlocks(final String str) {
      if (str == null) {
         return null;
      }
      if (str.length() == 0) {
         return ArrayUtils.EMPTY_STRING_ARRAY;
      }
      final List<String> list = new ArrayList<String>();
      for (String s : STRING_VALUES) {
         if (hasCodeBlock(str, s)) {
            list.add(s);
         }
      }
      return list.toArray(new String[list.size()]);
   }

   static boolean hasCodeBlockSplitter(final String declaration, final int codeBlock, final boolean opening) {
      if (StringUtils.contains(declaration, getValue(codeBlock).charAt(opening ? 0 : 1))) {
         return true;
      }
      return false;
   }

   public static boolean containsIgnoringQuotes(final CharSequence seq, final int searchChar) {
      //From StringUtils
      if (isEmpty(seq)) {
         return false;
      }
      return indexOf(seq, searchChar, 0) >= 0;
   }

   static int indexOf(final CharSequence cs, final int searchChar, int start) {
      // from CharSequenceUtils
      final int NOT_FOUND = -1;
      int quotes = 0;
      if (cs instanceof String) {
         return ((String) cs).indexOf(searchChar, start);
      }
      final int sz = cs.length();
      if (start < 0) {
         start = 0;
      }
      for (int i = start; i < sz; i++) {
         if(cs.charAt(i) == QUOTES){
            quotes++;
         }
         if (cs.charAt(i) == searchChar && quotes%2 == 0) {
            return i;
         }
      }
      return NOT_FOUND;
   }

   public static String[] splitIgnoringCodeBlocks(final String str, final char separatorChar) {
      return splitWorker(str, separatorChar, false, getCodeBlocks(str));
   }

   public static String[] splitWorker(final String str, final char separatorChar, final boolean preserveAllTokens,
                                      final String[] ignoredBlocks) {
      // From StringUtils

      if (str == null) {
         return null;
      }
      final int len = str.length();
      if (len == 0) {
         return ArrayUtils.EMPTY_STRING_ARRAY;
      }
      final List<String> list = new ArrayList<String>();
      int i = 0, start = 0, opened = 0, closed = 0, quotes = 0;
      boolean match = false;
      boolean lastMatch = false;
      while (i < len) {
         if (ignoredBlocks != null) {
            for (String s : ignoredBlocks) {
               int index = s.indexOf(str.charAt(i));
               if (index == 0) {
                  opened++;
               }
               if (index == 1) {
                  closed++;
               }
            }
         }
         if(str.charAt(i) == QUOTES) quotes++;
         if (str.charAt(i) == separatorChar && opened - closed == 0 && quotes % 2 == 0) {
            if (match || preserveAllTokens) {
               list.add(str.substring(start, i));
               match = false;
               lastMatch = true;
            }
            start = ++i;
            continue;
         }
         lastMatch = false;
         match = true;
         i++;
      }
      if (match || preserveAllTokens && lastMatch) {
         list.add(str.substring(start, i));
      }
      return list.toArray(new String[list.size()]); }


   public static String cleanLine(String line) {
      if (StringUtils.contains(line, "//")) line = StringUtils.splitByWholeSeparator(line, "//")[0];
      if (StringUtils.contains(line, "/*")) line = StringUtils.splitByWholeSeparator(line, "/*")[0];
      if (StringUtils.contains(line, "*/")){
         if(StringUtils.splitByWholeSeparator(line, "*/").length == 1)
            line = StringUtils.EMPTY;
         else
            line = StringUtils.splitByWholeSeparator(line, "*/")[1];
      }

      if(StringUtils.deleteWhitespace(line) == StringUtils.EMPTY){
         return StringUtils.EMPTY;
      }

      for (int i = 0; i < line.length(); i++){
         if (i + 1 != line.length() && Character.isWhitespace(line.charAt(i)) && Character.isWhitespace(line.charAt(i+1))) {
            line = line.substring(0, i) + line.substring(i + 1);
            i--;
         }
      }

      while(line.length()>0 && Character.isWhitespace(line.charAt(0))){
         line = line.substring(1);
      }

      while(line.length()>0 && Character.isWhitespace(line.charAt(line.length()-1))){
         line = line.substring(0, line.length() - 1);
      }

      return line;
   }

   @Override
   public String toString(){
      switch(value){
         case 1: return "<>";
         case 2: return "{}";
         case 3: return "[]";
         case 4: return "()";
         default: return null;
      }
   }

   private static String[] stringValues(){
      final String[] result = new String[CodeBlock.values().length];
      for(int i = 0; i<CodeBlock.values().length; i++){
         result[i] = CodeBlock.values()[i].toString();
      }
      return result;
   }
}
