/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class TypeInsnNode extends AbstractInsnNode
/*    */ {
/*    */   public String desc;
/*    */ 
/*    */   public TypeInsnNode(int opcode, String desc)
/*    */   {
/* 61 */     super(opcode);
/* 62 */     this.desc = desc;
/*    */   }
/*    */ 
/*    */   public void setOpcode(int opcode)
/*    */   {
/* 73 */     this.opcode = opcode;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 78 */     return 3;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor mv)
/*    */   {
/* 83 */     mv.visitTypeInsn(this.opcode, this.desc);
/* 84 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels)
/*    */   {
/* 89 */     return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.TypeInsnNode
 * JD-Core Version:    0.5.4
 */