/*     */ package org.jacoco.report.internal.html.page;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.jacoco.core.data.ExecutionData;
/*     */ import org.jacoco.core.data.SessionInfo;
/*     */ import org.jacoco.report.ILanguageNames;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*     */ import org.jacoco.report.internal.html.index.ElementIndex;
/*     */ 
/*     */ public class SessionsPage extends ReportPage
/*     */ {
/*     */   private static final String MSG_SESSIONS = "This coverage report is based on execution data from the following sessions:";
/*     */   private static final String MSG_NO_SESSIONS = "No session information available.";
/*     */   private static final String MSG_EXECDATA = "Execution data for the following classes is considered in this report:";
/*     */   private static final String MSG_NO_EXECDATA = "No execution data available.";
/*     */   private final List<SessionInfo> sessionInfos;
/*     */   private final DateFormat dateFormat;
/*     */   private final List<ExecutionData> executionData;
/*     */   private final ElementIndex index;
/*     */ 
/*     */   public SessionsPage(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData, ElementIndex index, ReportPage parent, ReportOutputFolder folder, IHTMLReportContext context)
/*     */   {
/*  75 */     super(parent, folder, context);
/*  76 */     this.sessionInfos = sessionInfos;
/*  77 */     this.executionData = new ArrayList(executionData);
/*  78 */     this.index = index;
/*  79 */     this.dateFormat = DateFormat.getDateTimeInstance(2, 2, context.getLocale());
/*     */ 
/*  81 */     ILanguageNames names = context.getLanguageNames();
/*  82 */     Collections.sort(this.executionData, new Comparator(names) {
/*     */       public int compare(ExecutionData e1, ExecutionData e2) {
/*  84 */         return this.val$names.getQualifiedClassName(e1.getName()).compareTo(this.val$names.getQualifiedClassName(e2.getName()));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void content(HTMLElement body)
/*     */     throws IOException
/*     */   {
/*  92 */     if (this.sessionInfos.isEmpty()) {
/*  93 */       body.p().text("No session information available.");
/*     */     } else {
/*  95 */       body.p().text("This coverage report is based on execution data from the following sessions:");
/*  96 */       sessionTable(body);
/*     */     }
/*  98 */     if (this.executionData.isEmpty()) {
/*  99 */       body.p().text("No execution data available.");
/*     */     } else {
/* 101 */       body.p().text("Execution data for the following classes is considered in this report:");
/* 102 */       executionDataTable(body);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sessionTable(HTMLElement body) throws IOException {
/* 107 */     HTMLElement table = body.table("coverage");
/*     */ 
/* 109 */     HTMLElement tr = table.thead().tr();
/* 110 */     tr.td().text("Session");
/* 111 */     tr.td().text("Start Time");
/* 112 */     tr.td().text("Dump Time");
/*     */ 
/* 114 */     HTMLElement tbody = table.tbody();
/* 115 */     for (SessionInfo i : this.sessionInfos) {
/* 116 */       HTMLElement tr = tbody.tr();
/* 117 */       tr.td().span("el_session").text(i.getId());
/* 118 */       tr.td().text(this.dateFormat.format(new Date(i.getStartTimeStamp())));
/* 119 */       tr.td().text(this.dateFormat.format(new Date(i.getDumpTimeStamp())));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void executionDataTable(HTMLElement body) throws IOException {
/* 124 */     HTMLElement table = body.table("coverage");
/*     */ 
/* 126 */     HTMLElement tr = table.thead().tr();
/* 127 */     tr.td().text("Class");
/* 128 */     tr.td().text("Id");
/*     */ 
/* 130 */     HTMLElement tbody = table.tbody();
/* 131 */     ILanguageNames names = this.context.getLanguageNames();
/* 132 */     for (ExecutionData e : this.executionData) {
/* 133 */       HTMLElement tr = tbody.tr();
/* 134 */       String link = this.index.getLinkToClass(e.getId());
/* 135 */       String qualifiedName = names.getQualifiedClassName(e.getName());
/*     */ 
/* 137 */       if (link == null)
/* 138 */         tr.td().span("el_class").text(qualifiedName);
/*     */       else {
/* 140 */         tr.td().a(link, "el_class").text(qualifiedName);
/*     */       }
/* 142 */       String id = String.format("%016x", new Object[] { Long.valueOf(e.getId()) });
/* 143 */       tr.td().code().text(id);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getFileName()
/*     */   {
/* 149 */     return "jacoco-sessions.html";
/*     */   }
/*     */ 
/*     */   public String getLinkStyle() {
/* 153 */     return "el_session";
/*     */   }
/*     */ 
/*     */   public String getLinkLabel() {
/* 157 */     return "Sessions";
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.SessionsPage
 * JD-Core Version:    0.5.4
 */