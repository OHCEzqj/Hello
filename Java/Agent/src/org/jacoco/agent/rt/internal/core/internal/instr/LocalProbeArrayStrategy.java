/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr;
/*    */ 
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.core.runtime.IExecutionDataAccessorGenerator;
/*    */ 
/*    */ class LocalProbeArrayStrategy
/*    */   implements IProbeArrayStrategy
/*    */ {
/*    */   private final String className;
/*    */   private final long classId;
/*    */   private final int probeCount;
/*    */   private final IExecutionDataAccessorGenerator accessorGenerator;
/*    */ 
/*    */   LocalProbeArrayStrategy(String className, long classId, int probeCount, IExecutionDataAccessorGenerator accessorGenerator)
/*    */   {
/* 35 */     this.className = className;
/* 36 */     this.classId = classId;
/* 37 */     this.probeCount = probeCount;
/* 38 */     this.accessorGenerator = accessorGenerator;
/*    */   }
/*    */ 
/*    */   public int storeInstance(MethodVisitor mv, boolean clinit, int variable)
/*    */   {
/* 43 */     int maxStack = this.accessorGenerator.generateDataAccessor(this.classId, this.className, this.probeCount, mv);
/*    */ 
/* 45 */     mv.visitVarInsn(58, variable);
/* 46 */     return maxStack;
/*    */   }
/*    */ 
/*    */   public void addMembers(ClassVisitor delegate, int probeCount)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.LocalProbeArrayStrategy
 * JD-Core Version:    0.5.4
 */