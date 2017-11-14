/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.Label;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class LabelNode extends AbstractInsnNode
/*    */ {
/*    */   private Label label;
/*    */ 
/*    */   public LabelNode()
/*    */   {
/* 45 */     super(-1);
/*    */   }
/*    */ 
/*    */   public LabelNode(Label label) {
/* 49 */     super(-1);
/* 50 */     this.label = label;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 55 */     return 8;
/*    */   }
/*    */ 
/*    */   public Label getLabel() {
/* 59 */     if (this.label == null) {
/* 60 */       this.label = new Label();
/*    */     }
/* 62 */     return this.label;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor cv)
/*    */   {
/* 67 */     cv.visitLabel(getLabel());
/*    */   }
/*    */ 
/*    */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels)
/*    */   {
/* 72 */     return (AbstractInsnNode)labels.get(this);
/*    */   }
/*    */ 
/*    */   public void resetLabel() {
/* 76 */     this.label = null;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.LabelNode
 * JD-Core Version:    0.5.4
 */