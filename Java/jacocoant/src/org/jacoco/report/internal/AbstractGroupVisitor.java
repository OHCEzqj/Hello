/*     */ package org.jacoco.report.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.jacoco.core.analysis.CoverageNodeImpl;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.report.IReportGroupVisitor;
/*     */ import org.jacoco.report.ISourceFileLocator;
/*     */ 
/*     */ public abstract class AbstractGroupVisitor
/*     */   implements IReportGroupVisitor
/*     */ {
/*     */   protected final CoverageNodeImpl total;
/*     */   private AbstractGroupVisitor lastChild;
/*     */ 
/*     */   protected AbstractGroupVisitor(String name)
/*     */   {
/*  40 */     this.total = new CoverageNodeImpl(ICoverageNode.ElementType.GROUP, name);
/*     */   }
/*     */ 
/*     */   public final void visitBundle(IBundleCoverage bundle, ISourceFileLocator locator) throws IOException
/*     */   {
/*  45 */     finalizeLastChild();
/*  46 */     this.total.increment(bundle);
/*  47 */     handleBundle(bundle, locator);
/*     */   }
/*     */ 
/*     */   protected abstract void handleBundle(IBundleCoverage paramIBundleCoverage, ISourceFileLocator paramISourceFileLocator)
/*     */     throws IOException;
/*     */ 
/*     */   public final IReportGroupVisitor visitGroup(String name)
/*     */     throws IOException
/*     */   {
/*  65 */     finalizeLastChild();
/*  66 */     this.lastChild = handleGroup(name);
/*  67 */     return this.lastChild;
/*     */   }
/*     */ 
/*     */   protected abstract AbstractGroupVisitor handleGroup(String paramString)
/*     */     throws IOException;
/*     */ 
/*     */   public final void visitEnd()
/*     */     throws IOException
/*     */   {
/*  89 */     finalizeLastChild();
/*  90 */     handleEnd();
/*     */   }
/*     */ 
/*     */   protected abstract void handleEnd()
/*     */     throws IOException;
/*     */ 
/*     */   private void finalizeLastChild()
/*     */     throws IOException
/*     */   {
/* 102 */     if (this.lastChild != null) {
/* 103 */       this.lastChild.visitEnd();
/* 104 */       this.total.increment(this.lastChild.total);
/* 105 */       this.lastChild = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.AbstractGroupVisitor
 * JD-Core Version:    0.5.4
 */