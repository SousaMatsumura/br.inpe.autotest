package model;

import java.util.Set;

public class Method {
   private String name;
   private String returns;
   private Set<Variable> parameters;
   private Set<Integer> modifiers;
   private Set<String> exceptions;

   private Method(String name, String returns, Set<Variable> parameters, Set<Integer> modifiers, Set<String> exceptions) {
      this.name = name;
      this.returns = returns;
      this.parameters = parameters;
      this.modifiers = modifiers;
      this.exceptions = exceptions;
   }

   public static class Builder{
      private String name;
      private String returns;
      private Set<Variable> parameters;
      private Set<Integer> modifiers;
      private Set<String> exceptions;

      public Builder(String name){
         this.name = name;
      }
      public Builder returns(String returns){
         this.returns = returns;
         return this;
      }
      public Builder parameters(Set parameters){
         this.parameters = parameters;
         return this;
      }
      public Builder modifiers(Set modifiers){
         this.modifiers = modifiers;
         return this;
      }
      public Builder exceptions(Set exceptions){
         this.exceptions = exceptions;
         return this;
      }
      public Method build(){
         return new Method(name, returns, parameters, modifiers, exceptions);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(obj.getClass() != this.getClass()) return false;
      Method met = (Method) obj;
      return getMethodSignature() != null && getMethodSignature().equals(met.getMethodSignature());
   }

   @Override
   public int hashCode() {
      return getMethodSignature()!=null ? getMethodSignature().hashCode() : 1;
   }

   public String getMethodSignature(){
      if(name==null) return null;
      final StringBuilder result = new StringBuilder(name);
      result.append('(');
      for(Variable p : parameters) result.append(p.getDataKind()).append(',');
      return result.deleteCharAt(result.length()-1).append(')').toString();
   }

   public String getName() {
      return name;
   }

   public String getReturns() {
      return returns;
   }

   public Set<Variable> getParameters() {
      return parameters;
   }

   public Set<Integer> getModifiers() {
      return modifiers;
   }

   @Override
   public String toString(){
      StringBuilder result = new StringBuilder();
      result.append("\n~~~~~ Method Details ~~~~~\n");
      result.append("   Method Name:\n      ").append(name).append("\n");
      if(returns != null){
         result.append("   Return:\n      ").append(returns).append("\n");
      }
      if (modifiers != null){
         result.append("   Method Modifiers:\n");
         for (Integer i : modifiers) result.append("      ").append(Modifiers.getValue(i)).append("\n");
      }
      if (parameters.size() > 0 ){
         int i = 1;
         for (Variable param : parameters){
            result.append("   Parameter ").append(i).append(":\n");
            result.append("      Data Type:\n         ").append(param.getDataKind()).append("\n");
            result.append("      Variable Name:\n         ").append(param.getName()).append("\n");
            if (param.getModifiers().size() > 0){
               result.append("      Parameter Modifiers:\n");
               for (Integer j : param.getModifiers()) result.append("            ").append(Modifiers.getValue(j)).append("\n");
            }
            i++;
            if(param.getValue() != null) result.append("      Value:\n      ").append(param.getValue()).append("\n");
         }
      }
      if (exceptions.size() > 0){
         result.append("   Exceptions:\n");
         for(String s : exceptions) result.append("      ").append(s).append("\n");
      }
      return result.toString();
   }
}
