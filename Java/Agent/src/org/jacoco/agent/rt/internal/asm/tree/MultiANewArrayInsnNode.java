/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class MultiANewArrayInsnNode extends AbstractInsnNode
/*    */ {
/*    */   public String desc;
/*    */   public int dims;
/*    */ 
/*    */   public MultiANewArrayInsnNode(String desc, int dims)
/*    */   {
/* 63 */     super(197);
/* 64 */     this.desc = desc;
/* 65 */     this.dims = dims;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 70 */     return 13;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor mv)
/*    */   {
/* 75 */     mv.visitMultiANewArrayInsn(this.desc, this.dims);
/* 76 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels)
/*    */   {
/* 81 */     return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.MultiANewArrayInsnNode
 * JD-Core Version:    0.5.4
 */