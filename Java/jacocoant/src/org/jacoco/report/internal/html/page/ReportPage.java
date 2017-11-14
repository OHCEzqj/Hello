/*     */ package org.jacoco.report.internal.html.page;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import org.jacoco.core.JaCoCo;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLDocument;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*     */ import org.jacoco.report.internal.html.ILinkable;
/*     */ import org.jacoco.report.internal.html.resources.Resources;
/*     */ import org.jacoco.report.internal.xml.XMLElement;
/*     */ 
/*     */ public abstract class ReportPage
/*     */   implements ILinkable
/*     */ {
/*     */   private final ReportPage parent;
/*     */   protected final ReportOutputFolder folder;
/*     */   protected final IHTMLReportContext context;
/*     */ 
/*     */   protected ReportPage(ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*     */   {
/*  52 */     this.parent = parent;
/*  53 */     this.context = context;
/*  54 */     this.folder = folder;
/*     */   }
/*     */ 
/*     */   protected final boolean isRootPage()
/*     */   {
/*  63 */     return this.parent == null;
/*     */   }
/*     */ 
/*     */   public void render()
/*     */     throws IOException
/*     */   {
/*  74 */     HTMLDocument doc = new HTMLDocument(this.folder.createFile(getFileName()), this.context.getOutputEncoding());
/*     */ 
/*  76 */     doc.attr("lang", this.context.getLocale().getLanguage());
/*  77 */     head(doc.head());
/*  78 */     body(doc.body());
/*  79 */     doc.close();
/*     */   }
/*     */ 
/*     */   protected void head(HTMLElement head)
/*     */     throws IOException
/*     */   {
/*  91 */     head.meta("Content-Type", "text/html;charset=UTF-8");
/*  92 */     head.link("stylesheet", this.context.getResources().getLink(this.folder, "report.css"), "text/css");
/*     */ 
/*  95 */     head.link("shortcut icon", this.context.getResources().getLink(this.folder, "report.gif"), "image/gif");
/*     */ 
/*  98 */     head.title().text(getLinkLabel());
/*     */   }
/*     */ 
/*     */   private void body(HTMLElement body) throws IOException {
/* 102 */     body.attr("onload", getOnload());
/* 103 */     HTMLElement navigation = body.div("breadcrumb");
/* 104 */     navigation.attr("id", "breadcrumb");
/* 105 */     infoLinks(navigation.span("info"));
/* 106 */     breadcrumb(navigation, this.folder);
/* 107 */     body.h1().text(getLinkLabel());
/* 108 */     content(body);
/* 109 */     footer(body);
/*     */   }
/*     */ 
/*     */   protected String getOnload()
/*     */   {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   protected void infoLinks(HTMLElement span)
/*     */     throws IOException
/*     */   {
/* 130 */     span.a(this.context.getSessionsPage(), this.folder);
/*     */   }
/*     */ 
/*     */   private void breadcrumb(HTMLElement div, ReportOutputFolder base) throws IOException
/*     */   {
/* 135 */     breadcrumbParent(this.parent, div, base);
/* 136 */     div.span(getLinkStyle()).text(getLinkLabel());
/*     */   }
/*     */ 
/*     */   private static void breadcrumbParent(ReportPage page, HTMLElement div, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 142 */     if (page != null) {
/* 143 */       breadcrumbParent(page.parent, div, base);
/* 144 */       div.a(page, base);
/* 145 */       div.text(" > ");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void footer(HTMLElement body) throws IOException {
/* 150 */     HTMLElement footer = body.div("footer");
/* 151 */     HTMLElement versioninfo = footer.span("right");
/* 152 */     versioninfo.text("Created with ");
/* 153 */     versioninfo.a(JaCoCo.HOMEURL).text("JaCoCo");
/* 154 */     versioninfo.text(" ").text(JaCoCo.VERSION);
/* 155 */     footer.text(this.context.getFooterText());
/*     */   }
/*     */ 
/*     */   protected abstract String getFileName();
/*     */ 
/*     */   protected abstract void content(HTMLElement paramHTMLElement)
/*     */     throws IOException;
/*     */ 
/*     */   public final String getLink(ReportOutputFolder base)
/*     */   {
/* 178 */     return this.folder.getLink(base, getFileName());
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.ReportPage
 * JD-Core Version:    0.5.4
 */