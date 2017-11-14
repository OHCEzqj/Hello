/*    */ package org.jacoco.agent.rt.internal_8ff85ea.asm;
/*    */ 
/*    */ class CurrentFrame extends Frame
/*    */ {
/*    */   void execute(int opcode, int arg, ClassWriter cw, Item item)
/*    */   {
/* 50 */     super.execute(opcode, arg, cw, item);
/* 51 */     Frame successor = new Frame();
/* 52 */     merge(cw, successor, 0);
/* 53 */     set(successor);
/* 54 */     this.owner.inputStackTop = 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.asm.CurrentFrame
 * JD-Core Version:    0.5.4
 */