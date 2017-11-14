/*     */ package org.jacoco.report.internal.html.page;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Locale;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ILine;
/*     */ import org.jacoco.core.analysis.ISourceNode;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ 
/*     */ final class SourceHighlighter
/*     */ {
/*     */   private final Locale locale;
/*     */   private String lang;
/*     */ 
/*     */   public SourceHighlighter(Locale locale)
/*     */   {
/*  41 */     this.locale = locale;
/*  42 */     this.lang = "java";
/*     */   }
/*     */ 
/*     */   public void setLanguage(String lang)
/*     */   {
/*  53 */     this.lang = lang;
/*     */   }
/*     */ 
/*     */   public void render(HTMLElement parent, ISourceNode source, Reader contents)
/*     */     throws IOException
/*     */   {
/*  70 */     HTMLElement pre = parent.pre("source lang-" + this.lang + " linenums");
/*     */ 
/*  72 */     BufferedReader lineBuffer = new BufferedReader(contents);
/*     */ 
/*  74 */     int nr = 0;
/*  75 */     while ((line = lineBuffer.readLine()) != null)
/*     */     {
/*     */       String line;
/*  76 */       ++nr;
/*  77 */       renderCodeLine(pre, line, source.getLine(nr), nr);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void renderCodeLine(HTMLElement pre, String linesrc, ILine line, int lineNr) throws IOException
/*     */   {
/*  83 */     highlight(pre, line, lineNr).text(linesrc);
/*  84 */     pre.text("\n");
/*     */   }
/*     */ 
/*     */   HTMLElement highlight(HTMLElement pre, ILine line, int lineNr)
/*     */     throws IOException
/*     */   {
/*     */     String style;
/*  90 */     switch (line.getStatus())
/*     */     {
/*     */     case 1:
/*  92 */       style = "nc";
/*  93 */       break;
/*     */     case 2:
/*  95 */       style = "fc";
/*  96 */       break;
/*     */     case 3:
/*  98 */       style = "pc";
/*  99 */       break;
/*     */     default:
/* 101 */       return pre;
/*     */     }
/*     */ 
/* 104 */     String lineId = "L" + Integer.toString(lineNr);
/* 105 */     ICounter branches = line.getBranchCounter();
/* 106 */     switch (branches.getStatus())
/*     */     {
/*     */     case 1:
/* 108 */       return span(pre, lineId, style, "bnc", "All %2$d branches missed.", branches);
/*     */     case 2:
/* 111 */       return span(pre, lineId, style, "bfc", "All %2$d branches covered.", branches);
/*     */     case 3:
/* 114 */       return span(pre, lineId, style, "bpc", "%1$d of %2$d branches missed.", branches);
/*     */     }
/*     */ 
/* 117 */     return pre.span(style, lineId);
/*     */   }
/*     */ 
/*     */   private HTMLElement span(HTMLElement parent, String id, String style1, String style2, String title, ICounter branches)
/*     */     throws IOException
/*     */   {
/* 124 */     HTMLElement span = parent.span(style1 + " " + style2, id);
/* 125 */     Integer missed = Integer.valueOf(branches.getMissedCount());
/* 126 */     Integer total = Integer.valueOf(branches.getTotalCount());
/* 127 */     span.attr("title", String.format(this.locale, title, new Object[] { missed, total }));
/* 128 */     return span;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.SourceHighlighter
 * JD-Core Version:    0.5.4
 */