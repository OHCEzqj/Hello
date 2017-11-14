/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.table.ITableItem;
/*    */ 
/*    */ final class SourceFileItem
/*    */   implements ITableItem
/*    */ {
/*    */   private final ICoverageNode node;
/*    */ 
/*    */   SourceFileItem(ISourceFileCoverage node)
/*    */   {
/* 29 */     this.node = node;
/*    */   }
/*    */ 
/*    */   public String getLinkLabel() {
/* 33 */     return this.node.getName();
/*    */   }
/*    */ 
/*    */   public String getLinkStyle() {
/* 37 */     return "el_source";
/*    */   }
/*    */ 
/*    */   public String getLink(ReportOutputFolder base) {
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   public ICoverageNode getNode() {
/* 45 */     return this.node;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.SourceFileItem
 * JD-Core Version:    0.5.4
 */