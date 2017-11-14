/*    */ package org.jacoco.report.internal;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ class NormalizedFileNames
/*    */ {
/* 38 */   private static final BitSet LEGAL_CHARS = new BitSet();
/*    */   private final Map<String, String> mapping;
/*    */   private final Set<String> usedNames;
/*    */ 
/*    */   NormalizedFileNames()
/*    */   {
/* 48 */     this.mapping = new HashMap();
/*    */ 
/* 50 */     this.usedNames = new HashSet();
/*    */   }
/*    */   public String getFileName(String id) {
/* 53 */     String name = (String)this.mapping.get(id);
/* 54 */     if (name != null) {
/* 55 */       return name;
/*    */     }
/* 57 */     name = replaceIllegalChars(id);
/* 58 */     name = ensureUniqueness(name);
/* 59 */     this.mapping.put(id, name);
/* 60 */     return name;
/*    */   }
/*    */ 
/*    */   private String replaceIllegalChars(String s) {
/* 64 */     StringBuilder sb = new StringBuilder(s.length());
/* 65 */     boolean modified = false;
/* 66 */     for (int i = 0; i < s.length(); ++i) {
/* 67 */       char c = s.charAt(i);
/* 68 */       if (LEGAL_CHARS.get(c)) {
/* 69 */         sb.append(c);
/*    */       } else {
/* 71 */         sb.append('_');
/* 72 */         modified = true;
/*    */       }
/*    */     }
/* 75 */     return (modified) ? sb.toString() : s;
/*    */   }
/*    */ 
/*    */   private String ensureUniqueness(String s) {
/* 79 */     String unique = s;
/* 80 */     String lower = unique.toLowerCase(Locale.ENGLISH);
/* 81 */     int idx = 1;
/* 82 */     while (this.usedNames.contains(lower)) {
/* 83 */       unique = s + '~' + idx++;
/* 84 */       lower = unique.toLowerCase(Locale.ENGLISH);
/*    */     }
/* 86 */     this.usedNames.add(lower);
/* 87 */     return unique;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 41 */     String legal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYXZ0123456789$-._";
/*    */ 
/* 43 */     for (char c : "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYXZ0123456789$-._".toCharArray())
/* 44 */       LEGAL_CHARS.set(c);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.NormalizedFileNames
 * JD-Core Version:    0.5.4
 */