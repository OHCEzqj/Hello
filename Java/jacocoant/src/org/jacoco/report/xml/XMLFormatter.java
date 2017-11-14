/*     */ package org.jacoco.report.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.data.ExecutionData;
/*     */ import org.jacoco.core.data.SessionInfo;
/*     */ import org.jacoco.report.IReportVisitor;
/*     */ import org.jacoco.report.ISourceFileLocator;
/*     */ import org.jacoco.report.internal.AbstractGroupVisitor;
/*     */ import org.jacoco.report.internal.xml.XMLCoverageWriter;
/*     */ import org.jacoco.report.internal.xml.XMLDocument;
/*     */ import org.jacoco.report.internal.xml.XMLElement;
/*     */ import org.jacoco.report.internal.xml.XMLGroupVisitor;
/*     */ 
/*     */ public class XMLFormatter
/*     */ {
/*     */   private static final String PUBID = "-//JACOCO//DTD Report 1.0//EN";
/*     */   private static final String SYSTEM = "report.dtd";
/*     */   private String outputEncoding;
/*     */ 
/*     */   public XMLFormatter()
/*     */   {
/*  39 */     this.outputEncoding = "UTF-8";
/*     */   }
/*     */ 
/*     */   public void setOutputEncoding(String outputEncoding)
/*     */   {
/*  48 */     this.outputEncoding = outputEncoding;
/*     */   }
/*     */ 
/*     */   public IReportVisitor createVisitor(OutputStream output)
/*     */     throws IOException
/*     */   {
/*  62 */     XMLElement root = new XMLDocument("report", "-//JACOCO//DTD Report 1.0//EN", "report.dtd", this.outputEncoding, true, output);
/*     */ 
/* 107 */     return new XMLGroupVisitor(root, root)
/*     */     {
/*     */       private List<SessionInfo> sessionInfos;
/*     */ 
/*     */       public void visitInfo(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData)
/*     */         throws IOException
/*     */       {
/*  75 */         this.sessionInfos = sessionInfos;
/*     */       }
/*     */ 
/*     */       protected void handleBundle(IBundleCoverage bundle, ISourceFileLocator locator)
/*     */         throws IOException
/*     */       {
/*  81 */         writeHeader(bundle.getName());
/*  82 */         XMLCoverageWriter.writeBundle(bundle, this.element);
/*     */       }
/*     */ 
/*     */       protected AbstractGroupVisitor handleGroup(String name)
/*     */         throws IOException
/*     */       {
/*  88 */         writeHeader(name);
/*  89 */         return new XMLGroupVisitor(this.element, name);
/*     */       }
/*     */ 
/*     */       private void writeHeader(String name) throws IOException {
/*  93 */         this.element.attr("name", name);
/*  94 */         for (SessionInfo i : this.sessionInfos) {
/*  95 */           XMLElement sessioninfo = this.val$root.element("sessioninfo");
/*  96 */           sessioninfo.attr("id", i.getId());
/*  97 */           sessioninfo.attr("start", i.getStartTimeStamp());
/*  98 */           sessioninfo.attr("dump", i.getDumpTimeStamp());
/*     */         }
/*     */       }
/*     */ 
/*     */       protected void handleEnd() throws IOException
/*     */       {
/* 104 */         this.element.close();
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.xml.XMLFormatter
 * JD-Core Version:    0.5.4
 */