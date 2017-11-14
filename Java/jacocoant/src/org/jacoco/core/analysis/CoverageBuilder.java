/*     */ package org.jacoco.core.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jacoco.core.internal.analysis.BundleCoverageImpl;
/*     */ import org.jacoco.core.internal.analysis.SourceFileCoverageImpl;
/*     */ 
/*     */ public class CoverageBuilder
/*     */   implements ICoverageVisitor
/*     */ {
/*     */   private final Map<String, IClassCoverage> classes;
/*     */   private final Map<String, ISourceFileCoverage> sourcefiles;
/*     */ 
/*     */   public CoverageBuilder()
/*     */   {
/*  48 */     this.classes = new HashMap();
/*  49 */     this.sourcefiles = new HashMap();
/*     */   }
/*     */ 
/*     */   public Collection<IClassCoverage> getClasses()
/*     */   {
/*  58 */     return Collections.unmodifiableCollection(this.classes.values());
/*     */   }
/*     */ 
/*     */   public Collection<ISourceFileCoverage> getSourceFiles()
/*     */   {
/*  67 */     return Collections.unmodifiableCollection(this.sourcefiles.values());
/*     */   }
/*     */ 
/*     */   public IBundleCoverage getBundle(String name)
/*     */   {
/*  78 */     return new BundleCoverageImpl(name, this.classes.values(), this.sourcefiles.values());
/*     */   }
/*     */ 
/*     */   public Collection<IClassCoverage> getNoMatchClasses()
/*     */   {
/*  89 */     Collection result = new ArrayList();
/*  90 */     for (IClassCoverage c : this.classes.values()) {
/*  91 */       if (c.isNoMatch()) {
/*  92 */         result.add(c);
/*     */       }
/*     */     }
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */   public void visitCoverage(IClassCoverage coverage)
/*     */   {
/* 102 */     if (coverage.getInstructionCounter().getTotalCount() > 0) {
/* 103 */       String name = coverage.getName();
/* 104 */       IClassCoverage dup = (IClassCoverage)this.classes.put(name, coverage);
/* 105 */       if (dup != null) {
/* 106 */         if (dup.getId() != coverage.getId()) {
/* 107 */           throw new IllegalStateException("Can't add different class with same name: " + name);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 112 */         String source = coverage.getSourceFileName();
/* 113 */         if (source != null) {
/* 114 */           SourceFileCoverageImpl sourceFile = getSourceFile(source, coverage.getPackageName());
/*     */ 
/* 116 */           sourceFile.increment(coverage);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private SourceFileCoverageImpl getSourceFile(String filename, String packagename)
/*     */   {
/* 124 */     String key = packagename + '/' + filename;
/* 125 */     SourceFileCoverageImpl sourcefile = (SourceFileCoverageImpl)this.sourcefiles.get(key);
/*     */ 
/* 127 */     if (sourcefile == null) {
/* 128 */       sourcefile = new SourceFileCoverageImpl(filename, packagename);
/* 129 */       this.sourcefiles.put(key, sourcefile);
/*     */     }
/* 131 */     return sourcefile;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.CoverageBuilder
 * JD-Core Version:    0.5.4
 */