/*    */ package org.jacoco.report;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public abstract class InputStreamSourceFileLocator
/*    */   implements ISourceFileLocator
/*    */ {
/*    */   private final String encoding;
/*    */   private final int tabWidth;
/*    */ 
/*    */   protected InputStreamSourceFileLocator(String encoding, int tabWidth)
/*    */   {
/* 41 */     this.encoding = encoding;
/* 42 */     this.tabWidth = tabWidth;
/*    */   }
/*    */ 
/*    */   public Reader getSourceFile(String packageName, String fileName)
/*    */     throws IOException
/*    */   {
/*    */     InputStream in;
/*    */     InputStream in;
/* 48 */     if (packageName.length() > 0)
/* 49 */       in = getSourceStream(packageName + "/" + fileName);
/*    */     else {
/* 51 */       in = getSourceStream(fileName);
/*    */     }
/*    */ 
/* 54 */     if (in == null) {
/* 55 */       return null;
/*    */     }
/*    */ 
/* 58 */     if (this.encoding == null) {
/* 59 */       return new InputStreamReader(in);
/*    */     }
/* 61 */     return new InputStreamReader(in, this.encoding);
/*    */   }
/*    */ 
/*    */   public int getTabWidth()
/*    */   {
/* 66 */     return this.tabWidth;
/*    */   }
/*    */ 
/*    */   protected abstract InputStream getSourceStream(String paramString)
/*    */     throws IOException;
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.InputStreamSourceFileLocator
 * JD-Core Version:    0.5.4
 */