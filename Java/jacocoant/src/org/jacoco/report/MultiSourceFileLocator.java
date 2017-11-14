/*    */ package org.jacoco.report;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MultiSourceFileLocator
/*    */   implements ISourceFileLocator
/*    */ {
/*    */   private final int tabWidth;
/*    */   private final List<ISourceFileLocator> delegates;
/*    */ 
/*    */   public MultiSourceFileLocator(int tabWidth)
/*    */   {
/* 38 */     this.tabWidth = tabWidth;
/* 39 */     this.delegates = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void add(ISourceFileLocator locator)
/*    */   {
/* 50 */     this.delegates.add(locator);
/*    */   }
/*    */ 
/*    */   public Reader getSourceFile(String packageName, String fileName) throws IOException
/*    */   {
/* 55 */     for (ISourceFileLocator d : this.delegates) {
/* 56 */       Reader reader = d.getSourceFile(packageName, fileName);
/* 57 */       if (reader != null) {
/* 58 */         return reader;
/*    */       }
/*    */     }
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */   public int getTabWidth() {
/* 65 */     return this.tabWidth;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.MultiSourceFileLocator
 * JD-Core Version:    0.5.4
 */