/*    */ package org.jacoco.report.internal.html;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IBundleCoverage;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ import org.jacoco.report.internal.AbstractGroupVisitor;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.page.BundlePage;
/*    */ import org.jacoco.report.internal.html.page.GroupPage;
/*    */ import org.jacoco.report.internal.html.page.NodePage;
/*    */ import org.jacoco.report.internal.html.page.ReportPage;
/*    */ 
/*    */ public class HTMLGroupVisitor extends AbstractGroupVisitor
/*    */ {
/*    */   private final ReportOutputFolder folder;
/*    */   private final IHTMLReportContext context;
/*    */   private final GroupPage page;
/*    */ 
/*    */   public HTMLGroupVisitor(ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context, String name)
/*    */   {
/* 52 */     super(name);
/* 53 */     this.folder = folder;
/* 54 */     this.context = context;
/* 55 */     this.page = new GroupPage(this.total, parent, folder, context);
/*    */   }
/*    */ 
/*    */   public NodePage<ICoverageNode> getPage()
/*    */   {
/* 64 */     return this.page;
/*    */   }
/*    */ 
/*    */   protected void handleBundle(IBundleCoverage bundle, ISourceFileLocator locator)
/*    */     throws IOException
/*    */   {
/* 70 */     BundlePage bundlepage = new BundlePage(bundle, this.page, locator, this.folder.subFolder(bundle.getName()), this.context);
/*    */ 
/* 72 */     bundlepage.render();
/* 73 */     this.page.addItem(bundlepage);
/*    */   }
/*    */ 
/*    */   protected AbstractGroupVisitor handleGroup(String name)
/*    */     throws IOException
/*    */   {
/* 79 */     HTMLGroupVisitor handler = new HTMLGroupVisitor(this.page, this.folder.subFolder(name), this.context, name);
/*    */ 
/* 81 */     this.page.addItem(handler.getPage());
/* 82 */     return handler;
/*    */   }
/*    */ 
/*    */   protected void handleEnd() throws IOException
/*    */   {
/* 87 */     this.page.render();
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.HTMLGroupVisitor
 * JD-Core Version:    0.5.4
 */