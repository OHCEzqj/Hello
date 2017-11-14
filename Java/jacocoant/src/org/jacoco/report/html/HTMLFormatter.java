/*     */ package org.jacoco.report.html;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*     */ import org.jacoco.core.data.ExecutionData;
/*     */ import org.jacoco.core.data.SessionInfo;
/*     */ import org.jacoco.report.ILanguageNames;
/*     */ import org.jacoco.report.IMultiReportOutput;
/*     */ import org.jacoco.report.IReportGroupVisitor;
/*     */ import org.jacoco.report.IReportVisitor;
/*     */ import org.jacoco.report.ISourceFileLocator;
/*     */ import org.jacoco.report.JavaNames;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLGroupVisitor;
/*     */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*     */ import org.jacoco.report.internal.html.ILinkable;
/*     */ import org.jacoco.report.internal.html.index.ElementIndex;
/*     */ import org.jacoco.report.internal.html.index.IIndexUpdate;
/*     */ import org.jacoco.report.internal.html.page.BundlePage;
/*     */ import org.jacoco.report.internal.html.page.ReportPage;
/*     */ import org.jacoco.report.internal.html.page.SessionsPage;
/*     */ import org.jacoco.report.internal.html.resources.Resources;
/*     */ import org.jacoco.report.internal.html.table.BarColumn;
/*     */ import org.jacoco.report.internal.html.table.CounterColumn;
/*     */ import org.jacoco.report.internal.html.table.LabelColumn;
/*     */ import org.jacoco.report.internal.html.table.PercentageColumn;
/*     */ import org.jacoco.report.internal.html.table.Table;
/*     */ 
/*     */ public class HTMLFormatter
/*     */   implements IHTMLReportContext
/*     */ {
/*  51 */   private ILanguageNames languageNames = new JavaNames();
/*     */ 
/*  53 */   private Locale locale = Locale.getDefault();
/*     */ 
/*  55 */   private String footerText = "";
/*     */ 
/*  57 */   private String outputEncoding = "UTF-8";
/*     */   private Resources resources;
/*     */   private ElementIndex index;
/*     */   private SessionsPage sessionsPage;
/*     */   private Table table;
/*     */ 
/*     */   public void setLanguageNames(ILanguageNames languageNames)
/*     */   {
/*  81 */     this.languageNames = languageNames;
/*     */   }
/*     */ 
/*     */   public void setLocale(Locale locale)
/*     */   {
/*  92 */     this.locale = locale;
/*     */   }
/*     */ 
/*     */   public void setFooterText(String footerText)
/*     */   {
/* 102 */     this.footerText = footerText;
/*     */   }
/*     */ 
/*     */   public void setOutputEncoding(String outputEncoding)
/*     */   {
/* 112 */     this.outputEncoding = outputEncoding;
/*     */   }
/*     */ 
/*     */   public ILanguageNames getLanguageNames()
/*     */   {
/* 118 */     return this.languageNames;
/*     */   }
/*     */ 
/*     */   public Resources getResources() {
/* 122 */     return this.resources;
/*     */   }
/*     */ 
/*     */   public Table getTable() {
/* 126 */     if (this.table == null) {
/* 127 */       this.table = createTable();
/*     */     }
/* 129 */     return this.table;
/*     */   }
/*     */ 
/*     */   private Table createTable() {
/* 133 */     Table t = new Table();
/* 134 */     t.add("Element", null, new LabelColumn(), false);
/* 135 */     t.add("Missed Instructions", "bar", new BarColumn(ICoverageNode.CounterEntity.INSTRUCTION, this.locale), true);
/*     */ 
/* 137 */     t.add("Cov.", "ctr2", new PercentageColumn(ICoverageNode.CounterEntity.INSTRUCTION, this.locale), false);
/*     */ 
/* 139 */     t.add("Missed Branches", "bar", new BarColumn(ICoverageNode.CounterEntity.BRANCH, this.locale), false);
/*     */ 
/* 141 */     t.add("Cov.", "ctr2", new PercentageColumn(ICoverageNode.CounterEntity.BRANCH, this.locale), false);
/*     */ 
/* 143 */     addMissedTotalColumns(t, "Cxty", ICoverageNode.CounterEntity.COMPLEXITY);
/* 144 */     addMissedTotalColumns(t, "Lines", ICoverageNode.CounterEntity.LINE);
/* 145 */     addMissedTotalColumns(t, "Methods", ICoverageNode.CounterEntity.METHOD);
/* 146 */     addMissedTotalColumns(t, "Classes", ICoverageNode.CounterEntity.CLASS);
/* 147 */     return t;
/*     */   }
/*     */ 
/*     */   private void addMissedTotalColumns(Table table, String label, ICoverageNode.CounterEntity entity)
/*     */   {
/* 152 */     table.add("Missed", "ctr1", CounterColumn.newMissed(entity, this.locale), false);
/*     */ 
/* 154 */     table.add(label, "ctr2", CounterColumn.newTotal(entity, this.locale), false);
/*     */   }
/*     */ 
/*     */   public String getFooterText()
/*     */   {
/* 159 */     return this.footerText;
/*     */   }
/*     */ 
/*     */   public ILinkable getSessionsPage() {
/* 163 */     return this.sessionsPage;
/*     */   }
/*     */ 
/*     */   public String getOutputEncoding() {
/* 167 */     return this.outputEncoding;
/*     */   }
/*     */ 
/*     */   public IIndexUpdate getIndexUpdate() {
/* 171 */     return this.index;
/*     */   }
/*     */ 
/*     */   public Locale getLocale() {
/* 175 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public IReportVisitor createVisitor(IMultiReportOutput output)
/*     */     throws IOException
/*     */   {
/* 189 */     ReportOutputFolder root = new ReportOutputFolder(output);
/* 190 */     this.resources = new Resources(root);
/* 191 */     this.resources.copyResources();
/* 192 */     this.index = new ElementIndex(root);
/* 193 */     return new IReportVisitor(root, output)
/*     */     {
/*     */       private List<SessionInfo> sessionInfos;
/*     */       private Collection<ExecutionData> executionData;
/*     */       private HTMLGroupVisitor groupHandler;
/*     */ 
/*     */       public void visitInfo(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData)
/*     */         throws IOException
/*     */       {
/* 203 */         this.sessionInfos = sessionInfos;
/* 204 */         this.executionData = executionData;
/*     */       }
/*     */ 
/*     */       public void visitBundle(IBundleCoverage bundle, ISourceFileLocator locator) throws IOException
/*     */       {
/* 209 */         BundlePage page = new BundlePage(bundle, null, locator, this.val$root, HTMLFormatter.this);
/*     */ 
/* 211 */         createSessionsPage(page);
/* 212 */         page.render();
/*     */       }
/*     */ 
/*     */       public IReportGroupVisitor visitGroup(String name) throws IOException
/*     */       {
/* 217 */         this.groupHandler = new HTMLGroupVisitor(null, this.val$root, HTMLFormatter.this, name);
/*     */ 
/* 219 */         createSessionsPage(this.groupHandler.getPage());
/* 220 */         return this.groupHandler;
/*     */       }
/*     */ 
/*     */       private void createSessionsPage(ReportPage rootpage)
/*     */       {
/* 225 */         HTMLFormatter.access$002(HTMLFormatter.this, new SessionsPage(this.sessionInfos, this.executionData, HTMLFormatter.this.index, rootpage, this.val$root, HTMLFormatter.this));
/*     */       }
/*     */ 
/*     */       public void visitEnd() throws IOException
/*     */       {
/* 230 */         if (this.groupHandler != null) {
/* 231 */           this.groupHandler.visitEnd();
/*     */         }
/* 233 */         HTMLFormatter.this.sessionsPage.render();
/* 234 */         this.val$output.close();
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.html.HTMLFormatter
 * JD-Core Version:    0.5.4
 */