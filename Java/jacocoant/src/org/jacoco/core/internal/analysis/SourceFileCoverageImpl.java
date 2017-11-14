/*    */ package org.jacoco.core.internal.analysis;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*    */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*    */ 
/*    */ public class SourceFileCoverageImpl extends SourceNodeImpl
/*    */   implements ISourceFileCoverage
/*    */ {
/*    */   private final String packagename;
/*    */ 
/*    */   public SourceFileCoverageImpl(String name, String packagename)
/*    */   {
/* 33 */     super(ICoverageNode.ElementType.SOURCEFILE, name);
/* 34 */     this.packagename = packagename;
/*    */   }
/*    */ 
/*    */   public String getPackageName()
/*    */   {
/* 40 */     return this.packagename;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.SourceFileCoverageImpl
 * JD-Core Version:    0.5.4
 */