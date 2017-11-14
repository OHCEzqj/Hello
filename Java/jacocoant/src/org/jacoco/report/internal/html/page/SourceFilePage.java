/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import org.jacoco.core.analysis.ISourceNode;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.HTMLElement;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ import org.jacoco.report.internal.html.resources.Resources;
/*    */ 
/*    */ public class SourceFilePage extends NodePage<ISourceNode>
/*    */ {
/*    */   private final Reader sourceReader;
/*    */   private final int tabWidth;
/*    */ 
/*    */   public SourceFilePage(ISourceNode sourceFileNode, Reader sourceReader, int tabWidth, ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 55 */     super(sourceFileNode, parent, folder, context);
/* 56 */     this.sourceReader = sourceReader;
/* 57 */     this.tabWidth = tabWidth;
/*    */   }
/*    */ 
/*    */   protected void content(HTMLElement body) throws IOException
/*    */   {
/* 62 */     SourceHighlighter hl = new SourceHighlighter(this.context.getLocale());
/* 63 */     hl.render(body, (ISourceNode)getNode(), this.sourceReader);
/* 64 */     this.sourceReader.close();
/*    */   }
/*    */ 
/*    */   protected void head(HTMLElement head) throws IOException
/*    */   {
/* 69 */     super.head(head);
/* 70 */     head.link("stylesheet", this.context.getResources().getLink(this.folder, "prettify.css"), "text/css");
/*    */ 
/* 74 */     head.script("text/javascript", this.context.getResources().getLink(this.folder, "prettify.js"));
/*    */   }
/*    */ 
/*    */   protected String getOnload()
/*    */   {
/* 82 */     return String.format("window['PR_TAB_WIDTH']=%d;prettyPrint()", new Object[] { Integer.valueOf(this.tabWidth) });
/*    */   }
/*    */ 
/*    */   protected String getFileName()
/*    */   {
/* 88 */     return ((ISourceNode)getNode()).getName() + ".html";
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.SourceFilePage
 * JD-Core Version:    0.5.4
 */