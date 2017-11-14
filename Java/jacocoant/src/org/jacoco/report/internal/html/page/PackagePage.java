/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.jacoco.core.analysis.IClassCoverage;
/*    */ import org.jacoco.core.analysis.IPackageCoverage;
/*    */ import org.jacoco.report.ILanguageNames;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.HTMLElement;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ import org.jacoco.report.internal.html.ILinkable;
/*    */ 
/*    */ public class PackagePage extends TablePage<IPackageCoverage>
/*    */ {
/*    */   private final PackageSourcePage packageSourcePage;
/*    */   private final boolean sourceCoverageExists;
/*    */ 
/*    */   public PackagePage(IPackageCoverage node, ReportPage parent, ISourceFileLocator locator, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 51 */     super(node, parent, folder, context);
/* 52 */     this.packageSourcePage = new PackageSourcePage(node, parent, locator, folder, context, this);
/*    */ 
/* 54 */     this.sourceCoverageExists = (!node.getSourceFiles().isEmpty());
/*    */   }
/*    */ 
/*    */   public void render() throws IOException
/*    */   {
/* 59 */     if (this.sourceCoverageExists) {
/* 60 */       this.packageSourcePage.render();
/*    */     }
/* 62 */     renderClasses();
/* 63 */     super.render();
/*    */   }
/*    */ 
/*    */   private void renderClasses() throws IOException {
/* 67 */     for (IClassCoverage c : ((IPackageCoverage)getNode()).getClasses()) {
/* 68 */       ILinkable sourceFilePage = this.packageSourcePage.getSourceFilePage(c.getSourceFileName());
/*    */ 
/* 70 */       ClassPage page = new ClassPage(c, this, sourceFilePage, this.folder, this.context);
/*    */ 
/* 72 */       page.render();
/* 73 */       addItem(page);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected String getOnload()
/*    */   {
/* 79 */     return "initialSort(['breadcrumb', 'coveragetable'])";
/*    */   }
/*    */ 
/*    */   protected String getFileName()
/*    */   {
/* 84 */     return "index.html";
/*    */   }
/*    */ 
/*    */   public String getLinkLabel()
/*    */   {
/* 89 */     return this.context.getLanguageNames().getPackageName(((IPackageCoverage)getNode()).getName());
/*    */   }
/*    */ 
/*    */   protected void infoLinks(HTMLElement span) throws IOException
/*    */   {
/* 94 */     if (this.sourceCoverageExists) {
/* 95 */       String link = this.packageSourcePage.getLink(this.folder);
/* 96 */       span.a(link, "el_source").text("Source Files");
/*    */     }
/* 98 */     super.infoLinks(span);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.PackagePage
 * JD-Core Version:    0.5.4
 */