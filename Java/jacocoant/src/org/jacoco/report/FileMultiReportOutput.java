/*    */ package org.jacoco.report;
/*    */ 
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class FileMultiReportOutput
/*    */   implements IMultiReportOutput
/*    */ {
/*    */   private final File basedir;
/*    */ 
/*    */   public FileMultiReportOutput(File basedir)
/*    */   {
/* 37 */     this.basedir = basedir;
/*    */   }
/*    */ 
/*    */   public OutputStream createFile(String path) throws IOException {
/* 41 */     File file = new File(this.basedir, path);
/* 42 */     File parent = file.getParentFile();
/* 43 */     parent.mkdirs();
/* 44 */     if (!parent.isDirectory()) {
/* 45 */       throw new IOException(String.format("Can't create directory %s.", new Object[] { parent }));
/*    */     }
/* 47 */     return new BufferedOutputStream(new FileOutputStream(file));
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.FileMultiReportOutput
 * JD-Core Version:    0.5.4
 */