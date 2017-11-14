/*    */ package org.jacoco.core.internal.flow;
/*    */ 
/*    */ import org.jacoco.asm.Label;
/*    */ import org.jacoco.asm.MethodVisitor;
/*    */ 
/*    */ public abstract class MethodProbesVisitor extends MethodVisitor
/*    */ {
/*    */   public MethodProbesVisitor()
/*    */   {
/* 28 */     this(null);
/*    */   }
/*    */ 
/*    */   public MethodProbesVisitor(MethodVisitor mv)
/*    */   {
/* 38 */     super(327680, mv);
/*    */   }
/*    */ 
/*    */   public void visitProbe(int probeId)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void visitJumpInsnWithProbe(int opcode, Label label, int probeId, IFrame frame)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void visitInsnWithProbe(int opcode, int probeId)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void visitTableSwitchInsnWithProbes(int min, int max, Label dflt, Label[] labels, IFrame frame)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void visitLookupSwitchInsnWithProbes(Label dflt, int[] keys, Label[] labels, IFrame frame)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.MethodProbesVisitor
 * JD-Core Version:    0.5.4
 */