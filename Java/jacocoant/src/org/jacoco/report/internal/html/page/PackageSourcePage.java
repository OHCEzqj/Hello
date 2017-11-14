/*     */ package org.jacoco.report.internal.html.page;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jacoco.core.analysis.IPackageCoverage;
/*     */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*     */ import org.jacoco.report.ILanguageNames;
/*     */ import org.jacoco.report.ISourceFileLocator;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*     */ import org.jacoco.report.internal.html.ILinkable;
/*     */ 
/*     */ public class PackageSourcePage extends TablePage<IPackageCoverage>
/*     */ {
/*     */   private final ISourceFileLocator locator;
/*     */   private final Map<String, ILinkable> sourceFilePages;
/*     */   private final ILinkable packagePage;
/*     */ 
/*     */   public PackageSourcePage(IPackageCoverage node, ReportPage parent, ISourceFileLocator locator, ReportOutputFolder folder, IHTMLReportContext context, ILinkable packagePage)
/*     */   {
/*  58 */     super(node, parent, folder, context);
/*  59 */     this.locator = locator;
/*  60 */     this.packagePage = packagePage;
/*  61 */     this.sourceFilePages = new HashMap();
/*     */   }
/*     */ 
/*     */   public void render() throws IOException
/*     */   {
/*  66 */     renderSourceFilePages();
/*  67 */     super.render();
/*     */   }
/*     */ 
/*     */   ILinkable getSourceFilePage(String name)
/*     */   {
/*  76 */     return (ILinkable)this.sourceFilePages.get(name);
/*     */   }
/*     */ 
/*     */   private final void renderSourceFilePages() throws IOException {
/*  80 */     String packagename = ((IPackageCoverage)getNode()).getName();
/*  81 */     for (ISourceFileCoverage s : ((IPackageCoverage)getNode()).getSourceFiles()) {
/*  82 */       String sourcename = s.getName();
/*  83 */       Reader reader = this.locator.getSourceFile(packagename, sourcename);
/*     */ 
/*  85 */       if (reader == null) {
/*  86 */         addItem(new SourceFileItem(s));
/*     */       } else {
/*  88 */         SourceFilePage sourcePage = new SourceFilePage(s, reader, this.locator.getTabWidth(), this, this.folder, this.context);
/*     */ 
/*  90 */         sourcePage.render();
/*  91 */         this.sourceFilePages.put(sourcename, sourcePage);
/*  92 */         addItem(sourcePage);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getOnload()
/*     */   {
/* 100 */     return "initialSort(['breadcrumb', 'coveragetable'])";
/*     */   }
/*     */ 
/*     */   protected String getFileName()
/*     */   {
/* 105 */     return "index.source.html";
/*     */   }
/*     */ 
/*     */   public String getLinkLabel()
/*     */   {
/* 110 */     return this.context.getLanguageNames().getPackageName(((IPackageCoverage)getNode()).getName());
/*     */   }
/*     */ 
/*     */   protected void infoLinks(HTMLElement span) throws IOException
/*     */   {
/* 115 */     String link = this.packagePage.getLink(this.folder);
/* 116 */     span.a(link, "el_class").text("Classes");
/* 117 */     super.infoLinks(span);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.PackageSourcePage
 * JD-Core Version:    0.5.4
 */