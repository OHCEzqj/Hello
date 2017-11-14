/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow;
/*    */ 
/*    */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
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

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow.ClassProbesVisitor
 * JD-Core Version:    0.5.4
 */