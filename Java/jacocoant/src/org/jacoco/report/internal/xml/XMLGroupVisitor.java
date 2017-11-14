/*    */ package org.jacoco.report.internal.xml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IBundleCoverage;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ import org.jacoco.report.internal.AbstractGroupVisitor;
/*    */ 
/*    */ public class XMLGroupVisitor extends AbstractGroupVisitor
/*    */ {
/*    */   protected final XMLElement element;
/*    */ 
/*    */   public XMLGroupVisitor(XMLElement element, String name)
/*    */     throws IOException
/*    */   {
/* 43 */     super(name);
/* 44 */     this.element = element;
/*    */   }
/*    */ 
/*    */   protected void handleBundle(IBundleCoverage bundle, ISourceFileLocator locator)
/*    */     throws IOException
/*    */   {
/* 50 */     XMLElement child = createChild(bundle.getName());
/* 51 */     XMLCoverageWriter.writeBundle(bundle, child);
/*    */   }
/*    */ 
/*    */   protected AbstractGroupVisitor handleGroup(String name)
/*    */     throws IOException
/*    */   {
/* 57 */     XMLElement child = createChild(name);
/* 58 */     return new XMLGroupVisitor(child, name);
/*    */   }
/*    */ 
/*    */   protected void handleEnd() throws IOException
/*    */   {
/* 63 */     XMLCoverageWriter.writeCounters(this.total, this.element);
/*    */   }
/*    */ 
/*    */   private XMLElement createChild(String name) throws IOException {
/* 67 */     return XMLCoverageWriter.createChild(this.element, "group", name);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.xml.XMLGroupVisitor
 * JD-Core Version:    0.5.4
 */