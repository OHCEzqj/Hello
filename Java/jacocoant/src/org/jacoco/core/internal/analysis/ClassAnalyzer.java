/*    */ package org.jacoco.core.internal.analysis;
/*    */ 
/*    */ import org.jacoco.asm.FieldVisitor;
/*    */ import org.jacoco.core.analysis.ICounter;
/*    */ import org.jacoco.core.analysis.IMethodCoverage;
/*    */ import org.jacoco.core.internal.flow.ClassProbesVisitor;
/*    */ import org.jacoco.core.internal.flow.MethodProbesVisitor;
/*    */ import org.jacoco.core.internal.instr.InstrSupport;
/*    */ 
/*    */ public class ClassAnalyzer extends ClassProbesVisitor
/*    */ {
/*    */   private final ClassCoverageImpl coverage;
/*    */   private final boolean[] probes;
/*    */   private final StringPool stringPool;
/*    */ 
/*    */   public ClassAnalyzer(ClassCoverageImpl coverage, boolean[] probes, StringPool stringPool)
/*    */   {
/* 42 */     this.coverage = coverage;
/* 43 */     this.probes = probes;
/* 44 */     this.stringPool = stringPool;
/*    */   }
/*    */ 
/*    */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
/*    */   {
/* 51 */     this.coverage.setSignature(this.stringPool.get(signature));
/* 52 */     this.coverage.setSuperName(this.stringPool.get(superName));
/* 53 */     this.coverage.setInterfaces(this.stringPool.get(interfaces));
/*    */   }
/*    */ 
/*    */   public void visitSource(String source, String debug)
/*    */   {
/* 58 */     this.coverage.setSourceFileName(this.stringPool.get(source));
/*    */   }
/*    */ 
/*    */   public MethodProbesVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
/*    */   {
/* 65 */     InstrSupport.assertNotInstrumented(name, this.coverage.getName());
/*    */ 
/* 67 */     if (isMethodFiltered(access, name)) {
/* 68 */       return null;
/*    */     }
/*    */ 
/* 71 */     return new MethodAnalyzer(this.stringPool.get(name), this.stringPool.get(desc), this.stringPool.get(signature), this.probes)
/*    */     {
/*    */       public void visitEnd()
/*    */       {
/* 75 */         super.visitEnd();
/* 76 */         IMethodCoverage methodCoverage = getCoverage();
/* 77 */         if (methodCoverage.getInstructionCounter().getTotalCount() <= 0)
/*    */           return;
/* 79 */         ClassAnalyzer.this.coverage.addMethod(methodCoverage);
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   private boolean isMethodFiltered(int access, String name)
/*    */   {
/* 87 */     return ((access & 0x1000) != 0) && (!name.startsWith("lambda$"));
/*    */   }
/*    */ 
/*    */   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
/*    */   {
/* 94 */     InstrSupport.assertNotInstrumented(name, this.coverage.getName());
/* 95 */     return super.visitField(access, name, desc, signature, value);
/*    */   }
/*    */ 
/*    */   public void visitTotalProbeCount(int count)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.ClassAnalyzer
 * JD-Core Version:    0.5.4
 */