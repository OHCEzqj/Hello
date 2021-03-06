/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm.tree;
/*    */ 
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ 
/*    */ public class ParameterNode
/*    */ {
/*    */   public String name;
/*    */   public int access;
/*    */ 
/*    */   public ParameterNode(String name, int access)
/*    */   {
/* 63 */     this.name = name;
/* 64 */     this.access = access;
/*    */   }
/*    */ 
/*    */   public void accept(MethodVisitor mv)
/*    */   {
/* 74 */     mv.visitParameter(this.name, this.access);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.tree.ParameterNode
 * JD-Core Version:    0.5.4
 */