/*    */ package org.jacoco.core.internal.flow;
/*    */ 
/*    */ import org.jacoco.asm.Label;
/*    */ import org.jacoco.asm.MethodVisitor;
/*    */ import org.jacoco.asm.commons.JSRInlinerAdapter;
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

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.MethodSanitizer
 * JD-Core Version:    0.5.4
 */