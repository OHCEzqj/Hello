/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.HTMLElement;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ import org.jacoco.report.internal.html.resources.Resources;
/*    */ import org.jacoco.report.internal.html.table.ITableItem;
/*    */ import org.jacoco.report.internal.html.table.Table;
/*    */ 
/*    */ public abstract class TablePage<NodeType extends ICoverageNode> extends NodePage<NodeType>
/*    */ {
/* 34 */   private final List<ITableItem> items = new ArrayList();
/*    */ 
/*    */   protected TablePage(NodeType node, ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 50 */     super(node, parent, folder, context);
/*    */   }
/*    */ 
/*    */   public void addItem(ITableItem item)
/*    */   {
/* 61 */     this.items.add(item);
/*    */   }
/*    */ 
/*    */   protected void head(HTMLElement head) throws IOException
/*    */   {
/* 66 */     super.head(head);
/* 67 */     head.script("text/javascript", this.context.getResources().getLink(this.folder, "sort.js"));
/*    */   }
/*    */ 
/*    */   protected void content(HTMLElement body)
/*    */     throws IOException
/*    */   {
/* 73 */     this.context.getTable().render(body, this.items, getNode(), this.context.getResources(), this.folder);
/*    */ 
/* 76 */     this.items.clear();
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.TablePage
 * JD-Core Version:    0.5.4
 */