/*    */ package org.jacoco.core.analysis;
/*    */ 
/*    */ public abstract interface ICoverageNode
/*    */ {
/*    */   public abstract ElementType getElementType();
/*    */ 
/*    */   public abstract String getName();
/*    */ 
/*    */   public abstract ICounter getInstructionCounter();
/*    */ 
/*    */   public abstract ICounter getBranchCounter();
/*    */ 
/*    */   public abstract ICounter getLineCounter();
/*    */ 
/*    */   public abstract ICounter getComplexityCounter();
/*    */ 
/*    */   public abstract ICounter getMethodCounter();
/*    */ 
/*    */   public abstract ICounter getClassCounter();
/*    */ 
/*    */   public abstract ICounter getCounter(CounterEntity paramCounterEntity);
/*    */ 
/*    */   public abstract ICoverageNode getPlainCopy();
/*    */ 
/*    */   public static enum CounterEntity
/*    */   {
/* 51 */     INSTRUCTION, 
/*    */ 
/* 54 */     BRANCH, 
/*    */ 
/* 57 */     LINE, 
/*    */ 
/* 60 */     COMPLEXITY, 
/*    */ 
/* 63 */     METHOD, 
/*    */ 
/* 66 */     CLASS;
/*    */   }
/*    */ 
/*    */   public static enum ElementType
/*    */   {
/* 26 */     METHOD, 
/*    */ 
/* 29 */     CLASS, 
/*    */ 
/* 32 */     SOURCEFILE, 
/*    */ 
/* 35 */     PACKAGE, 
/*    */ 
/* 38 */     BUNDLE, 
/*    */ 
/* 41 */     GROUP;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.ICoverageNode
 * JD-Core Version:    0.5.4
 */