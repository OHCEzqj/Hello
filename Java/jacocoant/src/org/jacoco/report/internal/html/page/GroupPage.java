/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ 
/*    */ public class GroupPage extends TablePage<ICoverageNode>
/*    */ {
/*    */   public GroupPage(ICoverageNode node, ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 38 */     super(node, parent, folder, context);
/*    */   }
/*    */ 
/*    */   protected String getOnload()
/*    */   {
/* 43 */     return "initialSort(['breadcrumb', 'coveragetable'])";
/*    */   }
/*    */ 
/*    */   protected String getFileName()
/*    */   {
/* 48 */     return "index.html";
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.GroupPage
 * JD-Core Version:    0.5.4
 */