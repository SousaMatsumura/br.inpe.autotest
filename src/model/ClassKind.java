package model;

import org.apache.commons.lang3.ArrayUtils;

public enum ClassKind {
   CLASS(1), INTERFACE(2), ENUM(3), ANNOTATION(4);
   private int value;
   public static final String[] STRING_VALUES = stringValues();
   ClassKind(int value){this.value = value;}

   public static int getIndex(String value){
      switch(value){
         case "class": return 1;
         case "interface": return 2;
         case "enum": return 3;
         case "@interface": return 4;
         default: return 0;
      }
   }

   public static String getValue(int index){
      switch(index){
         case 1: return "class";
         case 2: return "interface";
         case 3: return "enum";
         case 4: return "@interface";
         default: return null;
      }
   }

   @Override
   public String toString(){
      switch(value){
         case 1: return "class";
         case 2: return "interface";
         case 3: return "enum";
         case 4: return "@interface";
         default: return null;
      }
   }

   public static boolean isClassKind(String s){
      return ArrayUtils.contains(STRING_VALUES, s);
   }

   public static String[] stringValues(){
      String[] result = new String[ClassKind.values().length];
      for(int i = 0; i<ClassKind.values().length; i++){
         result[i] = ClassKind.values()[i].toString();
      }
      return result;
   }

}
