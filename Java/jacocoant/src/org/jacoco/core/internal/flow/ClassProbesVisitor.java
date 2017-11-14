/*    */ package org.jacoco.core.internal.flow;
/*    */ 
/*    */ import org.jacoco.asm.ClassVisitor;
/*    */ 
/*    */ public abstract class ClassProbesVisitor extends ClassVisitor
/*    */ {
/*    */   public ClassProbesVisitor()
/*    */   {
/* 27 */     this(null);
/*    */   }
/*    */ 
/*    */   public ClassProbesVisitor(ClassVisitor cv)
/*    */   {
/* 37 */     super(327680, cv);
/*    */   }
/*    */ 
/*    */   public abstract MethodProbesVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);
/*    */ 
/*    */   public abstract void visitTotalProbeCount(int paramInt);
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.ClassProbesVisitor
 * JD-Core Version:    0.5.4
 */