/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.core.analysis.IMethodCoverage;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.ILinkable;
/*    */ import org.jacoco.report.internal.html.table.ITableItem;
/*    */ 
/*    */ final class MethodItem
/*    */   implements ITableItem
/*    */ {
/*    */   private final IMethodCoverage node;
/*    */   private final String label;
/*    */   private final ILinkable sourcePage;
/*    */ 
/*    */   MethodItem(IMethodCoverage node, String label, ILinkable sourcePage)
/*    */   {
/* 35 */     this.node = node;
/* 36 */     this.label = label;
/* 37 */     this.sourcePage = sourcePage;
/*    */   }
/*    */ 
/*    */   public String getLinkLabel() {
/* 41 */     return this.label;
/*    */   }
/*    */ 
/*    */   public String getLinkStyle() {
/* 45 */     return "el_method";
/*    */   }
/*    */ 
/*    */   public String getLink(ReportOutputFolder base) {
/* 49 */     if (this.sourcePage == null) {
/* 50 */       return null;
/*    */     }
/* 52 */     String link = this.sourcePage.getLink(base);
/* 53 */     int first = this.node.getFirstLine();
/* 54 */     return (first != -1) ? link + "#L" + first : link;
/*    */   }
/*    */ 
/*    */   public ICoverageNode getNode() {
/* 58 */     return this.node;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.MethodItem
 * JD-Core Version:    0.5.4
 */