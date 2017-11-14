/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow;
/*    */ 
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.Label;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.commons.JSRInlinerAdapter;
/*    */ 
/*    */ class MethodSanitizer extends JSRInlinerAdapter
/*    */ {
/*    */   MethodSanitizer(MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions)
/*    */   {
/* 36 */     super(327680, mv, access, name, desc, signature, exceptions);
/*    */   }
/*    */ 
/*    */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index)
/*    */   {
/* 47 */     if ((start.info != null) && (end.info != null))
/* 48 */       super.visitLocalVariable(name, desc, signature, start, end, index);
/*    */   }
/*    */ 
/*    */   public void visitLineNumber(int line, Label start)
/*    */   {
/* 57 */     if (start.info != null)
/* 58 */       super.visitLineNumber(line, start);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow.MethodSanitizer
 * JD-Core Version:    0.5.4
 */