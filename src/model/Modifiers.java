package model;

import org.apache.commons.lang3.StringUtils;

public enum Modifiers {
   PUBLIC(1), PRIVATE(2), PROTECTED(3), STATIC(4), FINAL(5), ABSTRACT(6), NATIVE(7), SYNCHRONIZED(8), TRANSIENT(9), VOLATILE(10), STRICTFP(11);
   private int value;
   public static final String[] STRING_VALUES = stringValues();
   Modifiers(int value){this.value = value;}

   public static int getIndex(String value){
      switch(value){
         case "public": return 1;
         case "private": return 2;
         case "protected": return 3;
         case "static": return 4;
         case "final": return 5;
         case "abstract": return 6;
         case "native": return 7;
         case "synchronized": return 8;
         case "transient": return 9;
         case "volatile": return 10;
         case "strictfp": return 11;
         default: throw new NullPointerException(("Value "+value+" don't exists"));
      }
   }

   public static String getValue(int index){
      switch(index){
         case 1: return "public";
         case 2: return "private";
         case 3: return "protected";
         case 4: return "static";
         case 5: return "final";
         case 6: return "abstract";
         case 7: return "native";
         case 8: return "synchronized";
         case 9: return "transient";
         case 10: return "volatile";
         case 11: return "strictfp";
         default: throw new IndexOutOfBoundsException(("Index "+index+" don't exists"));
      }
   }

   static boolean hasModifier(String declaration){
      String[] mod = stringValues();
      //System.out.println("Declaration: "+declaration);
      for(String s : mod){
         if(StringUtils.contains(declaration, s)){
            return true;
         }
      }
      return false;
   }

   @Override
   public String toString(){
      switch(value){
         case 1: return "public";
         case 2: return "private";
         case 3: return "protected";
         case 4: return "static";
         case 5: return "final";
         case 6: return "abstract";
         case 7: return "native";
         case 8: return "synchronized";
         case 9: return "transient";
         case 10: return "volatile";
         case 11: return "strictfp";
         default: return null;
      }
   }

   private static String[] stringValues(){
      String[] result = new String[Modifiers.values().length];
      for(int i = 0; i<Modifiers.values().length; i++){
         result[i] = Modifiers.values()[i].toString();
      }
      return result;
   }
}
