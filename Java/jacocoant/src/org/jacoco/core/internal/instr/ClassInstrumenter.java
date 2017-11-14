/*    */ package org.jacoco.core.internal.instr;
/*    */ 
/*    */ import org.jacoco.asm.ClassVisitor;
/*    */ import org.jacoco.asm.FieldVisitor;
/*    */ import org.jacoco.asm.MethodVisitor;
/*    */ import org.jacoco.core.internal.flow.ClassProbesVisitor;
/*    */ import org.jacoco.core.internal.flow.MethodProbesVisitor;
/*    */ 
/*    */ public class ClassInstrumenter extends ClassProbesVisitor
/*    */ {
/*    */   private final IProbeArrayStrategy probeArrayStrategy;
/*    */   private String className;
/*    */ 
/*    */   public ClassInstrumenter(IProbeArrayStrategy probeArrayStrategy, ClassVisitor cv)
/*    */   {
/* 40 */     super(cv);
/* 41 */     this.probeArrayStrategy = probeArrayStrategy;
/*    */   }
/*    */ 
/*    */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
/*    */   {
/* 48 */     this.className = name;
/* 49 */     super.visit(version, access, name, signature, superName, interfaces);
/*    */   }
/*    */ 
/*    */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
/*    */   {
/* 55 */     InstrSupport.assertNotInstrumented(name, this.className);
/* 56 */     return super.visitField(access, name, desc, signature, value);
/*    */   }
/*    */ 
/*    */   public MethodProbesVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
/*    */   {
/* 63 */     InstrSupport.assertNotInstrumented(name, this.className);
/*    */ 
/* 65 */     MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
/*    */ 
/* 68 */     if (mv == null) {
/* 69 */       return null;
/*    */     }
/* 71 */     MethodVisitor frameEliminator = new DuplicateFrameEliminator(mv);
/* 72 */     ProbeInserter probeVariableInserter = new ProbeInserter(access, name, desc, frameEliminator, this.probeArrayStrategy);
/*    */ 
/* 74 */     return new MethodInstrumenter(probeVariableInserter, probeVariableInserter);
/*    */   }
/*    */ 
/*    */   public void visitTotalProbeCount(int count)
/*    */   {
/* 80 */     this.probeArrayStrategy.addMembers(this.cv, count);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.ClassInstrumenter
 * JD-Core Version:    0.5.4
 */