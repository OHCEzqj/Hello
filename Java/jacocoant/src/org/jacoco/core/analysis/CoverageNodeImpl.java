/*     */ package org.jacoco.core.analysis;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.jacoco.core.internal.analysis.CounterImpl;
/*     */ 
/*     */ public class CoverageNodeImpl
/*     */   implements ICoverageNode
/*     */ {
/*     */   private final ICoverageNode.ElementType elementType;
/*     */   private final String name;
/*     */   protected CounterImpl branchCounter;
/*     */   protected CounterImpl instructionCounter;
/*     */   protected CounterImpl lineCounter;
/*     */   protected CounterImpl complexityCounter;
/*     */   protected CounterImpl methodCounter;
/*     */   protected CounterImpl classCounter;
/*     */ 
/*     */   public CoverageNodeImpl(ICoverageNode.ElementType elementType, String name)
/*     */   {
/*  54 */     this.elementType = elementType;
/*  55 */     this.name = name;
/*  56 */     this.branchCounter = CounterImpl.COUNTER_0_0;
/*  57 */     this.instructionCounter = CounterImpl.COUNTER_0_0;
/*  58 */     this.complexityCounter = CounterImpl.COUNTER_0_0;
/*  59 */     this.methodCounter = CounterImpl.COUNTER_0_0;
/*  60 */     this.classCounter = CounterImpl.COUNTER_0_0;
/*  61 */     this.lineCounter = CounterImpl.COUNTER_0_0;
/*     */   }
/*     */ 
/*     */   public void increment(ICoverageNode child)
/*     */   {
/*  71 */     this.instructionCounter = this.instructionCounter.increment(child.getInstructionCounter());
/*     */ 
/*  73 */     this.branchCounter = this.branchCounter.increment(child.getBranchCounter());
/*  74 */     this.lineCounter = this.lineCounter.increment(child.getLineCounter());
/*  75 */     this.complexityCounter = this.complexityCounter.increment(child.getComplexityCounter());
/*     */ 
/*  77 */     this.methodCounter = this.methodCounter.increment(child.getMethodCounter());
/*  78 */     this.classCounter = this.classCounter.increment(child.getClassCounter());
/*     */   }
/*     */ 
/*     */   public void increment(Collection<? extends ICoverageNode> children)
/*     */   {
/*  89 */     for (ICoverageNode child : children)
/*  90 */       increment(child);
/*     */   }
/*     */ 
/*     */   public ICoverageNode.ElementType getElementType()
/*     */   {
/*  97 */     return this.elementType;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 101 */     return this.name;
/*     */   }
/*     */ 
/*     */   public ICounter getInstructionCounter() {
/* 105 */     return this.instructionCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getBranchCounter() {
/* 109 */     return this.branchCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getLineCounter() {
/* 113 */     return this.lineCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getComplexityCounter() {
/* 117 */     return this.complexityCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getMethodCounter() {
/* 121 */     return this.methodCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getClassCounter() {
/* 125 */     return this.classCounter;
/*     */   }
/*     */ 
/*     */   public ICounter getCounter(ICoverageNode.CounterEntity entity) {
/* 129 */     switch (1.$SwitchMap$org$jacoco$core$analysis$ICoverageNode$CounterEntity[entity.ordinal()])
/*     */     {
/*     */     case 1:
/* 131 */       return getInstructionCounter();
/*     */     case 2:
/* 133 */       return getBranchCounter();
/*     */     case 3:
/* 135 */       return getLineCounter();
/*     */     case 4:
/* 137 */       return getComplexityCounter();
/*     */     case 5:
/* 139 */       return getMethodCounter();
/*     */     case 6:
/* 141 */       return getClassCounter();
/*     */     }
/* 143 */     throw new AssertionError(entity);
/*     */   }
/*     */ 
/*     */   public ICoverageNode getPlainCopy() {
/* 147 */     CoverageNodeImpl copy = new CoverageNodeImpl(this.elementType, this.name);
/* 148 */     copy.instructionCounter = CounterImpl.getInstance(this.instructionCounter);
/* 149 */     copy.branchCounter = CounterImpl.getInstance(this.branchCounter);
/* 150 */     copy.lineCounter = CounterImpl.getInstance(this.lineCounter);
/* 151 */     copy.complexityCounter = CounterImpl.getInstance(this.complexityCounter);
/* 152 */     copy.methodCounter = CounterImpl.getInstance(this.methodCounter);
/* 153 */     copy.classCounter = CounterImpl.getInstance(this.classCounter);
/* 154 */     return copy;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 159 */     StringBuilder sb = new StringBuilder();
/* 160 */     sb.append(this.name).append(" [").append(this.elementType).append("]");
/* 161 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.CoverageNodeImpl
 * JD-Core Version:    0.5.4
 */