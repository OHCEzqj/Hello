/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ import org.jacoco.report.internal.html.resources.Resources;
/*    */ import org.jacoco.report.internal.html.table.ITableItem;
/*    */ 
/*    */ public abstract class NodePage<NodeType extends ICoverageNode> extends ReportPage
/*    */   implements ITableItem
/*    */ {
/*    */   private final NodeType node;
/*    */ 
/*    */   protected NodePage(NodeType node, ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 46 */     super(parent, folder, context);
/* 47 */     this.node = node;
/*    */   }
/*    */ 
/*    */   public String getLinkStyle()
/*    */   {
/* 53 */     if (isRootPage()) {
/* 54 */       return "el_report";
/*    */     }
/* 56 */     return Resources.getElementStyle(this.node.getElementType());
/*    */   }
/*    */ 
/*    */   public String getLinkLabel()
/*    */   {
/* 61 */     return this.node.getName();
/*    */   }
/*    */ 
/*    */   public NodeType getNode()
/*    */   {
/* 67 */     return this.node;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.NodePage
 * JD-Core Version:    0.5.4
 */