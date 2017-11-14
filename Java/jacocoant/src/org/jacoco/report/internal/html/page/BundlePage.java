/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IBundleCoverage;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.core.analysis.IPackageCoverage;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ 
/*    */ public class BundlePage extends TablePage<ICoverageNode>
/*    */ {
/*    */   private final ISourceFileLocator locator;
/*    */   private IBundleCoverage bundle;
/*    */ 
/*    */   public BundlePage(IBundleCoverage bundle, ReportPage parent, ISourceFileLocator locator, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 50 */     super(bundle.getPlainCopy(), parent, folder, context);
/* 51 */     this.bundle = bundle;
/* 52 */     this.locator = locator;
/*    */   }
/*    */ 
/*    */   public void render() throws IOException
/*    */   {
/* 57 */     renderPackages();
/* 58 */     super.render();
/*    */ 
/* 60 */     this.bundle = null;
/*    */   }
/*    */ 
/*    */   private void renderPackages() throws IOException {
/* 64 */     for (IPackageCoverage p : this.bundle.getPackages()) {
/* 65 */       String packagename = p.getName();
/* 66 */       String foldername = (packagename.length() == 0) ? "default" : packagename.replace('/', '.');
/*    */ 
/* 68 */       PackagePage page = new PackagePage(p, this, this.locator, this.folder.subFolder(foldername), this.context);
/*    */ 
/* 70 */       page.render();
/* 71 */       addItem(page);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected String getOnload()
/*    */   {
/* 77 */     return "initialSort(['breadcrumb', 'coveragetable'])";
/*    */   }
/*    */ 
/*    */   protected String getFileName()
/*    */   {
/* 82 */     return "index.html";
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.BundlePage
 * JD-Core Version:    0.5.4
 */