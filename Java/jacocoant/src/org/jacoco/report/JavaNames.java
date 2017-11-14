/*     */ package org.jacoco.report;
/*     */ 
/*     */ import org.jacoco.asm.Type;
/*     */ 
/*     */ public class JavaNames
/*     */   implements ILanguageNames
/*     */ {
/*     */   public String getPackageName(String vmname)
/*     */   {
/*  22 */     if (vmname.length() == 0) {
/*  23 */       return "default";
/*     */     }
/*  25 */     return vmname.replace('/', '.');
/*     */   }
/*     */ 
/*     */   private String getClassName(String vmname) {
/*  29 */     int pos = vmname.lastIndexOf('/');
/*  30 */     String name = (pos == -1) ? vmname : vmname.substring(pos + 1);
/*  31 */     return name.replace('$', '.');
/*     */   }
/*     */ 
/*     */   private boolean isAnonymous(String vmname) {
/*  35 */     int dollarPosition = vmname.lastIndexOf('$');
/*  36 */     if (dollarPosition == -1) {
/*  37 */       return false;
/*     */     }
/*  39 */     int internalPosition = dollarPosition + 1;
/*  40 */     if (internalPosition == vmname.length())
/*     */     {
/*  42 */       return false;
/*     */     }
/*     */ 
/*  45 */     char start = vmname.charAt(internalPosition);
/*  46 */     return !Character.isJavaIdentifierStart(start);
/*     */   }
/*     */ 
/*     */   public String getClassName(String vmname, String vmsignature, String vmsuperclass, String[] vminterfaces)
/*     */   {
/*  51 */     if (isAnonymous(vmname))
/*     */     {
/*     */       String vmsupertype;
/*     */       String vmsupertype;
/*  53 */       if ((vminterfaces != null) && (vminterfaces.length > 0)) {
/*  54 */         vmsupertype = vminterfaces[0];
/*     */       }
/*     */       else
/*     */       {
/*     */         String vmsupertype;
/*  55 */         if (vmsuperclass != null)
/*  56 */           vmsupertype = vmsuperclass;
/*     */         else {
/*  58 */           vmsupertype = null;
/*     */         }
/*     */       }
/*  61 */       if (vmsupertype != null) {
/*  62 */         StringBuilder builder = new StringBuilder();
/*  63 */         String vmenclosing = vmname.substring(0, vmname.lastIndexOf('$'));
/*     */ 
/*  65 */         builder.append(getClassName(vmenclosing)).append(".new ").append(getClassName(vmsupertype)).append("() {...}");
/*     */ 
/*  67 */         return builder.toString();
/*     */       }
/*     */     }
/*  70 */     return getClassName(vmname);
/*     */   }
/*     */ 
/*     */   public String getQualifiedClassName(String vmname) {
/*  74 */     return vmname.replace('/', '.').replace('$', '.');
/*     */   }
/*     */ 
/*     */   public String getMethodName(String vmclassname, String vmmethodname, String vmdesc, String vmsignature)
/*     */   {
/*  80 */     return getMethodName(vmclassname, vmmethodname, vmdesc, false);
/*     */   }
/*     */ 
/*     */   public String getQualifiedMethodName(String vmclassname, String vmmethodname, String vmdesc, String vmsignature)
/*     */   {
/*  86 */     return getQualifiedClassName(vmclassname) + "." + getMethodName(vmclassname, vmmethodname, vmdesc, true);
/*     */   }
/*     */ 
/*     */   private String getMethodName(String vmclassname, String vmmethodname, String vmdesc, boolean qualifiedParams)
/*     */   {
/*  93 */     if ("<clinit>".equals(vmmethodname)) {
/*  94 */       return "static {...}";
/*     */     }
/*  96 */     StringBuilder result = new StringBuilder();
/*  97 */     if ("<init>".equals(vmmethodname)) {
/*  98 */       if (isAnonymous(vmclassname)) {
/*  99 */         return "{...}";
/*     */       }
/* 101 */       result.append(getClassName(vmclassname));
/*     */     }
/*     */     else {
/* 104 */       result.append(vmmethodname);
/*     */     }
/* 106 */     result.append('(');
/* 107 */     Type[] arguments = Type.getArgumentTypes(vmdesc);
/* 108 */     boolean comma = false;
/* 109 */     for (Type arg : arguments) {
/* 110 */       if (comma)
/* 111 */         result.append(", ");
/*     */       else {
/* 113 */         comma = true;
/*     */       }
/* 115 */       if (qualifiedParams)
/* 116 */         result.append(getQualifiedClassName(arg.getClassName()));
/*     */       else {
/* 118 */         result.append(getShortTypeName(arg));
/*     */       }
/*     */     }
/* 121 */     result.append(')');
/* 122 */     return result.toString();
/*     */   }
/*     */ 
/*     */   private String getShortTypeName(Type type) {
/* 126 */     String name = type.getClassName();
/* 127 */     int pos = name.lastIndexOf('.');
/* 128 */     String shortName = (pos == -1) ? name : name.substring(pos + 1);
/* 129 */     return shortName.replace('$', '.');
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.JavaNames
 * JD-Core Version:    0.5.4
 */