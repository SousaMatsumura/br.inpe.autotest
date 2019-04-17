package model;

import java.util.Set;

public class Variable {
   private String name;
   private String dataKind;
   private String value;
   private Set<Integer> modifiers;

   private Variable(String name, String dataKind, String value, Set<Integer> modifiers) {
      this.name = name;
      this.dataKind = dataKind;
      this.value = value;
      this.modifiers = modifiers;
   }

   public static class Builder{
      private String name;
      private String dataKind;
      private String value;
      private Set<Integer> modifiers;

      public Builder(String dataKind, String name){
         this.name = name;
         this.dataKind = dataKind;
      }
      public Builder value(String value){
         this.value = value;
         return this;
      }
      public Builder modifiers(Set modifiers){
         this.modifiers = modifiers;
         return this;
      }
      public Variable build(){
         return new Variable(name, dataKind, value, modifiers);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(obj.getClass() != this.getClass()) return false;
      Variable var = (Variable) obj;
      return !getID().equals("") && getID().equals(var.getID());
   }

   @Override
   public int hashCode() {
      return getID().equals("") ? getID().hashCode() : 1;
   }

   public String getID(){
      final StringBuilder result = new StringBuilder(dataKind);
      result.append(' ').append(name);
      if(value != null) result.append(' ').append(value);
      return result.toString();
   }

   public String getName() {
      return name;
   }

   public String getDataKind() {
      return dataKind;
   }

   public String getValue() {
      return value;
   }

   public Set<Integer> getModifiers() {
      return modifiers;
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("\n~~~~~ Variable Datails ~~~~~\n");

      result.append("   Modifiers:\n");
      if (modifiers != null){
         for (Integer i : modifiers) result.append("      ").append(Modifiers.getValue(i)).append("\n");
      }
      result.append("   Data Type:\n      ").append(dataKind).append("\n");
      result.append("   Variable Name:\n").append("      ").append(name).append("\n");
      result.append("   Value:\n      ").append(value).append("\n");
      return result.toString();
   }

}
