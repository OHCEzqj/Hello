/*    */ package org.jacoco.core.internal.analysis;
/*    */ 
/*    */ import org.jacoco.core.analysis.ICounter;
/*    */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*    */ import org.jacoco.core.analysis.IMethodCoverage;
/*    */ 
/*    */ public class MethodCoverageImpl extends SourceNodeImpl
/*    */   implements IMethodCoverage
/*    */ {
/*    */   private final String desc;
/*    */   private final String signature;
/*    */ 
/*    */   public MethodCoverageImpl(String name, String desc, String signature)
/*    */   {
/* 39 */     super(ICoverageNode.ElementType.METHOD, name);
/* 40 */     this.desc = desc;
/* 41 */     this.signature = signature;
/*    */   }
/*    */ 
/*    */   public void increment(ICounter instructions, ICounter branches, int line)
/*    */   {
/* 47 */     super.increment(instructions, branches, line);
/*    */ 
/* 49 */     if (branches.getTotalCount() > 1) {
/* 50 */       int c = Math.max(0, branches.getCoveredCount() - 1);
/* 51 */       int m = Math.max(0, branches.getTotalCount() - c - 1);
/* 52 */       this.complexityCounter = this.complexityCounter.increment(m, c);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void incrementMethodCounter()
/*    */   {
/* 61 */     ICounter base = (this.instructionCounter.getCoveredCount() == 0) ? CounterImpl.COUNTER_1_0 : CounterImpl.COUNTER_0_1;
/*    */ 
/* 63 */     this.methodCounter = this.methodCounter.increment(base);
/* 64 */     this.complexityCounter = this.complexityCounter.increment(base);
/*    */   }
/*    */ 
/*    */   public String getDesc()
/*    */   {
/* 70 */     return this.desc;
/*    */   }
/*    */ 
/*    */   public String getSignature() {
/* 74 */     return this.signature;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.MethodCoverageImpl
 * JD-Core Version:    0.5.4
 */