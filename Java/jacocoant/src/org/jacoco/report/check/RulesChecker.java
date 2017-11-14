/*    */ package org.jacoco.report.check;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.jacoco.core.analysis.IBundleCoverage;
/*    */ import org.jacoco.core.data.ExecutionData;
/*    */ import org.jacoco.core.data.SessionInfo;
/*    */ import org.jacoco.report.ILanguageNames;
/*    */ import org.jacoco.report.IReportGroupVisitor;
/*    */ import org.jacoco.report.IReportVisitor;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ import org.jacoco.report.JavaNames;
/*    */ 
/*    */ public class RulesChecker
/*    */ {
/*    */   private List<Rule> rules;
/*    */   private ILanguageNames languageNames;
/*    */ 
/*    */   public RulesChecker()
/*    */   {
/* 41 */     this.rules = new ArrayList();
/* 42 */     setLanguageNames(new JavaNames());
/*    */   }
/*    */ 
/*    */   public void setRules(List<Rule> rules)
/*    */   {
/* 52 */     this.rules = rules;
/*    */   }
/*    */ 
/*    */   public void setLanguageNames(ILanguageNames languageNames)
/*    */   {
/* 63 */     this.languageNames = languageNames;
/*    */   }
/*    */ 
/*    */   public IReportVisitor createVisitor(IViolationsOutput output)
/*    */   {
/* 74 */     BundleChecker bundleChecker = new BundleChecker(this.rules, this.languageNames, output);
/*    */ 
/* 76 */     return new IReportVisitor(bundleChecker)
/*    */     {
/*    */       public IReportGroupVisitor visitGroup(String name) throws IOException
/*    */       {
/* 80 */         return this;
/*    */       }
/*    */ 
/*    */       public void visitBundle(IBundleCoverage bundle, ISourceFileLocator locator) throws IOException
/*    */       {
/* 85 */         this.val$bundleChecker.checkBundle(bundle);
/*    */       }
/*    */ 
/*    */       public void visitInfo(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData)
/*    */         throws IOException
/*    */       {
/*    */       }
/*    */ 
/*    */       public void visitEnd()
/*    */         throws IOException
/*    */       {
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.check.RulesChecker
 * JD-Core Version:    0.5.4
 */