package model;

import org.apache.commons.lang3.StringUtils;

public enum ConstrainedStatements {
   IF(1), FOR(2), WHILE(3), ELSE(4);
   private int value;
   ConstrainedStatements(int value){this.value = value;}
   public static final String[] STRING_VALUES = stringValues();

   public static int getIndex(String value){
      switch(value){
         case "if": return 1;
         case "for": return 2;
         case "while": return 3;
         case "else": return 4;
         default: throw new NullPointerException(("Value "+value+" don't exists"));
      }
   }

   public static String getValue(int index){
      switch(index){
         case 1: return "if";
         case 2: return "for";
         case 3: return "while";
         case 4: return "else";
         default: throw new IndexOutOfBoundsException(("Index "+index+" don't exists"));
      }
   }

   public static boolean hasStatement(String declaration) {
      return false;
   }

   @Override
   public String toString(){
      switch(value){
         case 1: return "if";
         case 2: return "for";
         case 3: return "while";
         case 4: return "else";
         default: return null;
      }
   }

   private static String[] stringValues(){
      String[] result = new String[ConstrainedStatements.values().length];
      for(int i = 0; i<ConstrainedStatements.values().length; i++){
         result[i] = ConstrainedStatements.values()[i].toString();
      }
      return result;
   }
}
