/*    */ package org.jacoco.report.internal.html.page;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IClassCoverage;
/*    */ import org.jacoco.core.analysis.IMethodCoverage;
/*    */ import org.jacoco.report.ILanguageNames;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.IHTMLReportContext;
/*    */ import org.jacoco.report.internal.html.ILinkable;
/*    */ import org.jacoco.report.internal.html.index.IIndexUpdate;
/*    */ 
/*    */ public class ClassPage extends TablePage<IClassCoverage>
/*    */ {
/*    */   private final ILinkable sourcePage;
/*    */ 
/*    */   public ClassPage(IClassCoverage classNode, ReportPage parent, ILinkable sourcePage, ReportOutputFolder folder, IHTMLReportContext context)
/*    */   {
/* 47 */     super(classNode, parent, folder, context);
/* 48 */     this.sourcePage = sourcePage;
/* 49 */     context.getIndexUpdate().addClass(this, classNode.getId());
/*    */   }
/*    */ 
/*    */   protected String getOnload()
/*    */   {
/* 54 */     return "initialSort(['breadcrumb'])";
/*    */   }
/*    */ 
/*    */   public void render() throws IOException
/*    */   {
/* 59 */     for (IMethodCoverage m : ((IClassCoverage)getNode()).getMethods()) {
/* 60 */       String label = this.context.getLanguageNames().getMethodName(((IClassCoverage)getNode()).getName(), m.getName(), m.getDesc(), m.getSignature());
/*    */ 
/* 63 */       addItem(new MethodItem(m, label, this.sourcePage));
/*    */     }
/* 65 */     super.render();
/*    */   }
/*    */ 
/*    */   protected String getFileName()
/*    */   {
/* 70 */     String vmname = ((IClassCoverage)getNode()).getName();
/* 71 */     int pos = vmname.lastIndexOf('/');
/* 72 */     String shortname = (pos == -1) ? vmname : vmname.substring(pos + 1);
/* 73 */     return shortname + ".html";
/*    */   }
/*    */ 
/*    */   public String getLinkLabel()
/*    */   {
/* 78 */     return this.context.getLanguageNames().getClassName(((IClassCoverage)getNode()).getName(), ((IClassCoverage)getNode()).getSignature(), ((IClassCoverage)getNode()).getSuperName(), ((IClassCoverage)getNode()).getInterfaceNames());
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.page.ClassPage
 * JD-Core Version:    0.5.4
 */