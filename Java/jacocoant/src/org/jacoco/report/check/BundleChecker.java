/*     */ package org.jacoco.report.check;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.IClassCoverage;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.core.analysis.IMethodCoverage;
/*     */ import org.jacoco.core.analysis.IPackageCoverage;
/*     */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*     */ import org.jacoco.report.ILanguageNames;
/*     */ 
/*     */ class BundleChecker
/*     */ {
/*     */   private final ILanguageNames names;
/*     */   private final IViolationsOutput output;
/*     */   private final Collection<Rule> bundleRules;
/*     */   private final Collection<Rule> packageRules;
/*     */   private final Collection<Rule> classRules;
/*     */   private final Collection<Rule> sourceFileRules;
/*     */   private final Collection<Rule> methodRules;
/*     */   private final boolean traversePackages;
/*     */   private final boolean traverseClasses;
/*     */   private final boolean traverseSourceFiles;
/*     */   private final boolean traverseMethods;
/*     */ 
/*     */   public BundleChecker(Collection<Rule> rules, ILanguageNames names, IViolationsOutput output)
/*     */   {
/*  47 */     this.names = names;
/*  48 */     this.output = output;
/*  49 */     this.bundleRules = new ArrayList();
/*  50 */     this.packageRules = new ArrayList();
/*  51 */     this.classRules = new ArrayList();
/*  52 */     this.sourceFileRules = new ArrayList();
/*  53 */     this.methodRules = new ArrayList();
/*  54 */     for (Rule rule : rules) {
/*  55 */       switch (1.$SwitchMap$org$jacoco$core$analysis$ICoverageNode$ElementType[rule.getElement().ordinal()])
/*     */       {
/*     */       case 1:
/*  57 */         this.bundleRules.add(rule);
/*  58 */         break;
/*     */       case 2:
/*  60 */         this.packageRules.add(rule);
/*  61 */         break;
/*     */       case 3:
/*  63 */         this.classRules.add(rule);
/*  64 */         break;
/*     */       case 4:
/*  66 */         this.sourceFileRules.add(rule);
/*  67 */         break;
/*     */       case 5:
/*  69 */         this.methodRules.add(rule);
/*     */       }
/*     */     }
/*     */ 
/*  73 */     this.traverseMethods = (!this.methodRules.isEmpty());
/*  74 */     this.traverseClasses = ((!this.classRules.isEmpty()) || (this.traverseMethods));
/*  75 */     this.traverseSourceFiles = (!this.sourceFileRules.isEmpty());
/*  76 */     this.traversePackages = ((!this.packageRules.isEmpty()) || (this.traverseClasses) || (this.traverseSourceFiles));
/*     */   }
/*     */ 
/*     */   public void checkBundle(IBundleCoverage bundleCoverage)
/*     */   {
/*  81 */     String name = bundleCoverage.getName();
/*  82 */     checkRules(bundleCoverage, this.bundleRules, "bundle", name);
/*  83 */     if (this.traversePackages)
/*  84 */       for (IPackageCoverage p : bundleCoverage.getPackages())
/*  85 */         check(p);
/*     */   }
/*     */ 
/*     */   private void check(IPackageCoverage packageCoverage)
/*     */   {
/*  91 */     String name = this.names.getPackageName(packageCoverage.getName());
/*  92 */     checkRules(packageCoverage, this.packageRules, "package", name);
/*  93 */     if (this.traverseClasses) {
/*  94 */       for (IClassCoverage c : packageCoverage.getClasses()) {
/*  95 */         check(c);
/*     */       }
/*     */     }
/*  98 */     if (this.traverseSourceFiles)
/*  99 */       for (ISourceFileCoverage s : packageCoverage.getSourceFiles())
/* 100 */         check(s);
/*     */   }
/*     */ 
/*     */   private void check(IClassCoverage classCoverage)
/*     */   {
/* 106 */     String name = this.names.getQualifiedClassName(classCoverage.getName());
/*     */ 
/* 108 */     checkRules(classCoverage, this.classRules, "class", name);
/* 109 */     if (this.traverseMethods)
/* 110 */       for (IMethodCoverage m : classCoverage.getMethods())
/* 111 */         check(m, classCoverage.getName());
/*     */   }
/*     */ 
/*     */   private void check(ISourceFileCoverage sourceFile)
/*     */   {
/* 117 */     String name = sourceFile.getPackageName() + "/" + sourceFile.getName();
/*     */ 
/* 119 */     checkRules(sourceFile, this.sourceFileRules, "source file", name);
/*     */   }
/*     */ 
/*     */   private void check(IMethodCoverage method, String className) {
/* 123 */     String name = this.names.getQualifiedMethodName(className, method.getName(), method.getDesc(), method.getSignature());
/*     */ 
/* 125 */     checkRules(method, this.methodRules, "method", name);
/*     */   }
/*     */ 
/*     */   private void checkRules(ICoverageNode node, Collection<Rule> rules, String typename, String elementname)
/*     */   {
/* 131 */     for (Iterator i$ = rules.iterator(); i$.hasNext(); ) { rule = (Rule)i$.next();
/* 132 */       if (rule.matches(elementname))
/* 133 */         for (Limit limit : rule.getLimits())
/* 134 */           checkLimit(node, typename, elementname, rule, limit); }
/*     */ 
/*     */     Rule rule;
/*     */   }
/*     */ 
/*     */   private void checkLimit(ICoverageNode node, String elementtype, String typename, Rule rule, Limit limit)
/*     */   {
/* 142 */     String message = limit.check(node);
/* 143 */     if (message != null)
/* 144 */       this.output.onViolation(node, rule, limit, String.format("Rule violated for %s %s: %s", new Object[] { elementtype, typename, message }));
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.check.BundleChecker
 * JD-Core Version:    0.5.4
 */