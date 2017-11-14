/*    */ package org.jacoco.core.internal.analysis;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class StringPool
/*    */ {
/* 28 */   private static final String[] EMPTY_ARRAY = new String[0];
/*    */   private final Map<String, String> pool;
/*    */ 
/*    */   public StringPool()
/*    */   {
/* 30 */     this.pool = new HashMap(1024);
/*    */   }
/*    */ 
/*    */   public String get(String s)
/*    */   {
/* 40 */     if (s == null) {
/* 41 */       return null;
/*    */     }
/* 43 */     String norm = (String)this.pool.get(s);
/* 44 */     if (norm == null) {
/* 45 */       this.pool.put(s, s);
/* 46 */       return s;
/*    */     }
/* 48 */     return norm;
/*    */   }
/*    */ 
/*    */   public String[] get(String[] arr)
/*    */   {
/* 61 */     if (arr == null) {
/* 62 */       return null;
/*    */     }
/* 64 */     if (arr.length == 0) {
/* 65 */       return EMPTY_ARRAY;
/*    */     }
/* 67 */     for (int i = 0; i < arr.length; ++i) {
/* 68 */       arr[i] = get(arr[i]);
/*    */     }
/* 70 */     return arr;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.StringPool
 * JD-Core Version:    0.5.4
 */