/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr;
/*    */ 
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ class NoneProbeArrayStrategy
/*    */   implements IProbeArrayStrategy
/*    */ {
/*    */   public int storeInstance(MethodVisitor mv, boolean clinit, int variable)
/*    */   {
/* 25 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public void addMembers(ClassVisitor delegate, int probeCount)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.NoneProbeArrayStrategy
 * JD-Core Version:    0.5.4
 */