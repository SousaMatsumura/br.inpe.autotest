package model;

import java.util.Set;

public class Class {
   private String directory;
   private Set<String> imports;
   private String name;
   private String pack;
   private Integer kind;
   private String extend;
   private Set<String> implement;
   private Set<Method> methods;
   private Set<Integer> modifiers;
   private Set<Class> innerClasses;

   private Class(String directory, Set<String> imports, String name, String pack, Integer kind, String extend, Set<String> implement, Set<Method> methods, Set<Integer> modifiers, Set<Class> innerClasses) {
      this.directory = directory;
      this.imports = imports;
      this.name = name;
      this.pack = pack;
      this.kind = kind;
      this.extend = extend;
      this.implement = implement;
      this.methods = methods;
      this.modifiers = modifiers;
      this.innerClasses = innerClasses;
   }

   public static class Builder{
      private String directory;
      private Set<String> imports;
      private String name;
      private String pack;
      private Integer kind;
      private String extend;
      private Set<String> implement;
      private Set<Method> methods;
      private Set<Integer> modifiers;
      private Set<Class> innerClasses;

      public Builder(String directory, String name, Integer kind){
         this.directory = directory;
         this.name = name;
         this.kind = kind;
      }
      public Builder imports(Set imports){
         this.imports = imports;
         return this;
      }
      public Builder pack(String pack){
         this.pack = pack;
         return this;
      }
      public Builder extend(String extend){
         this.extend = extend;
         return this;
      }
      public Builder implement(Set implement){
         this.implement = implement;
         return this;
      }
      public Builder methods(Set methods){
         this.methods = methods;
         return this;
      }
      public Builder modifiers(Set modifiers){
         this.modifiers = modifiers;
         return this;
      }
      public Builder innerClasses(Set innerClass){
         this.innerClasses = innerClass;
         return this;
      }
      public Class build(){
         return new Class(directory, imports, name, pack, kind, extend, implement, methods, modifiers, innerClasses);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if(obj == null) return false;
      if(obj == this) return true;
      if(obj.getClass() != this.getClass()) return false;
      Class cla = (Class) obj;
      return directory != null && directory.equals(cla.directory);
   }

   @Override
   public int hashCode() {
      return directory!=null ? directory.hashCode() : 1;
   }

   public String getDirectory() {
      return directory;
   }

   public Set<String> getImports() {
      return imports;
   }

   public String getName() {
      return name;
   }

   public String getPack() {
      return pack;
   }

   public int getKind() {
      return kind;
   }

   public String getExtend() {
      return extend;
   }

   public Set<String> getImplement() {
      return implement;
   }

   public Set<Method> getMethods() {
      return methods;
   }

   public Set<Integer> getModifiers() {
      return modifiers;
   }

   public Set<Class> getInnerClasses() {
      return innerClasses;
   }
}
