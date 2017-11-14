/*    */ package org.jacoco.report.csv;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.jacoco.core.data.ExecutionData;
/*    */ import org.jacoco.core.data.SessionInfo;
/*    */ import org.jacoco.report.ILanguageNames;
/*    */ import org.jacoco.report.IReportVisitor;
/*    */ import org.jacoco.report.JavaNames;
/*    */ 
/*    */ public class CSVFormatter
/*    */ {
/*    */   private ILanguageNames languageNames;
/*    */   private String outputEncoding;
/*    */ 
/*    */   public CSVFormatter()
/*    */   {
/* 32 */     this.languageNames = new JavaNames();
/*    */ 
/* 34 */     this.outputEncoding = "UTF-8";
/*    */   }
/*    */ 
/*    */   public void setLanguageNames(ILanguageNames languageNames)
/*    */   {
/* 44 */     this.languageNames = languageNames;
/*    */   }
/*    */ 
/*    */   public ILanguageNames getLanguageNames()
/*    */   {
/* 53 */     return this.languageNames;
/*    */   }
/*    */ 
/*    */   public void setOutputEncoding(String outputEncoding)
/*    */   {
/* 63 */     this.outputEncoding = outputEncoding;
/*    */   }
/*    */ 
/*    */   public IReportVisitor createVisitor(OutputStream output)
/*    */     throws IOException
/*    */   {
/* 77 */     DelimitedWriter writer = new DelimitedWriter(new OutputStreamWriter(output, this.outputEncoding));
/*    */ 
/* 79 */     ClassRowWriter rowWriter = new ClassRowWriter(writer, this.languageNames);
/*    */ 
/* 96 */     return new CSVGroupHandler(rowWriter, writer)
/*    */     {
/*    */       public void visitInfo(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData)
/*    */         throws IOException
/*    */       {
/*    */       }
/*    */ 
/*    */       public void visitEnd()
/*    */         throws IOException
/*    */       {
/* 93 */         this.val$writer.close();
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.csv.CSVFormatter
 * JD-Core Version:    0.5.4
 */