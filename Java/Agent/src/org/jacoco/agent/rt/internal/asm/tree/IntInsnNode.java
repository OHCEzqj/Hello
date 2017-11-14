/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class IntInsnNode extends AbstractInsnNode
/*    */ {
/*    */   public int operand;
/*    */ 
/*    */   public IntInsnNode(int opcode, int operand)
/*    */   {
/* 58 */     super(opcode);
/* 59 */     this.operand = operand;
/*    */   }
/*    */ 
/*    */   public void setOpcode(int opcode)
/*    */   {
/* 70 */     this.opcode = opcode;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 75 */     return 1;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor mv)
/*    */   {
/* 80 */     mv.visitIntInsn(this.opcode, this.operand);
/* 81 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels)
/*    */   {
/* 86 */     return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.IntInsnNode
 * JD-Core Version:    0.5.4
 */