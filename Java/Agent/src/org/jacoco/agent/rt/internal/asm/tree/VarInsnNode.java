/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class VarInsnNode extends AbstractInsnNode
/*    */ {
/*    */   public int var;
/*    */ 
/*    */   public VarInsnNode(int opcode, int var)
/*    */   {
/* 63 */     super(opcode);
/* 64 */     this.var = var;
/*    */   }
/*    */ 
/*    */   public void setOpcode(int opcode)
/*    */   {
/* 76 */     this.opcode = opcode;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 81 */     return 2;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor mv)
/*    */   {
/* 86 */     mv.visitVarInsn(this.opcode, this.var);
/* 87 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels)
/*    */   {
/* 92 */     return new VarInsnNode(this.opcode, this.var).cloneAnnotations(this);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.VarInsnNode
 * JD-Core Version:    0.5.4
 */