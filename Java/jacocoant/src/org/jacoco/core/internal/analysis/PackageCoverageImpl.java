/*    */ package org.jacoco.core.internal.analysis;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.jacoco.core.analysis.CoverageNodeImpl;
/*    */ import org.jacoco.core.analysis.IClassCoverage;
/*    */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*    */ import org.jacoco.core.analysis.IPackageCoverage;
/*    */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*    */ 
/*    */ public class PackageCoverageImpl extends CoverageNodeImpl
/*    */   implements IPackageCoverage
/*    */ {
/*    */   private final Collection<IClassCoverage> classes;
/*    */   private final Collection<ISourceFileCoverage> sourceFiles;
/*    */ 
/*    */   public PackageCoverageImpl(String name, Collection<IClassCoverage> classes, Collection<ISourceFileCoverage> sourceFiles)
/*    */   {
/* 44 */     super(ICoverageNode.ElementType.PACKAGE, name);
/* 45 */     this.classes = classes;
/* 46 */     this.sourceFiles = sourceFiles;
/* 47 */     increment(sourceFiles);
/* 48 */     for (IClassCoverage c : classes)
/*    */     {
/* 52 */       if (c.getSourceFileName() == null)
/* 53 */         increment(c);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Collection<IClassCoverage> getClasses()
/*    */   {
/* 61 */     return this.classes;
/*    */   }
/*    */ 
/*    */   public Collection<ISourceFileCoverage> getSourceFiles() {
/* 65 */     return this.sourceFiles;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.PackageCoverageImpl
 * JD-Core Version:    0.5.4
 */