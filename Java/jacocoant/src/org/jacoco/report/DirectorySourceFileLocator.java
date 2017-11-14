/*    */ package org.jacoco.report;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class DirectorySourceFileLocator extends InputStreamSourceFileLocator
/*    */ {
/*    */   private final File directory;
/*    */ 
/*    */   public DirectorySourceFileLocator(File directory, String encoding, int tabWidth)
/*    */   {
/* 42 */     super(encoding, tabWidth);
/* 43 */     this.directory = directory;
/*    */   }
/*    */ 
/*    */   protected InputStream getSourceStream(String path) throws IOException
/*    */   {
/* 48 */     File file = new File(this.directory, path);
/* 49 */     if (file.exists()) {
/* 50 */       return new FileInputStream(file);
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.DirectorySourceFileLocator
 * JD-Core Version:    0.5.4
 */