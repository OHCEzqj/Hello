/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow;
/*     */ 
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.commons.AnalyzerAdapter;
/*     */ 
/*     */ public class ClassProbesAdapter extends ClassVisitor
/*     */   implements IProbeIdGenerator
/*     */ {
/*  26 */   private static final MethodProbesVisitor EMPTY_METHOD_PROBES_VISITOR = new MethodProbesVisitor() { } ;
/*     */   private final ClassProbesVisitor cv;
/*     */   private final boolean trackFrames;
/*  33 */   private int counter = 0;
/*     */   private String name;
/*     */ 
/*     */   public ClassProbesAdapter(ClassProbesVisitor cv, boolean trackFrames)
/*     */   {
/*  47 */     super(327680, cv);
/*  48 */     this.cv = cv;
/*  49 */     this.trackFrames = trackFrames;
/*     */   }
/*     */ 
/*     */   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
/*     */   {
/*  56 */     this.name = name;
/*  57 */     super.visit(version, access, name, signature, superName, interfaces);
/*     */   }
/*     */ 
/*     */   public final MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
/*     */   {
/*  64 */     MethodProbesVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
/*     */     MethodProbesVisitor methodProbes;
/*     */     MethodProbesVisitor methodProbes;
/*  66 */     if (mv == null)
/*     */     {
/*  69 */       methodProbes = EMPTY_METHOD_PROBES_VISITOR;
/*     */     }
/*  71 */     else methodProbes = mv;
/*     */ 
/*  73 */     return new MethodSanitizer(null, access, name, desc, signature, exceptions, methodProbes)
/*     */     {
/*     */       public void visitEnd()
/*     */       {
/*  78 */         super.visitEnd();
/*  79 */         LabelFlowAnalyzer.markLabels(this);
/*  80 */         MethodProbesAdapter probesAdapter = new MethodProbesAdapter(this.val$methodProbes, ClassProbesAdapter.this);
/*     */ 
/*  82 */         if (ClassProbesAdapter.this.trackFrames) {
/*  83 */           AnalyzerAdapter analyzer = new AnalyzerAdapter(ClassProbesAdapter.this.name, this.access, this.name, this.desc, probesAdapter);
/*     */ 
/*  86 */           probesAdapter.setAnalyzer(analyzer);
/*  87 */           accept(analyzer);
/*     */         } else {
/*  89 */           accept(probesAdapter);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void visitEnd()
/*     */   {
/*  97 */     this.cv.visitTotalProbeCount(this.counter);
/*  98 */     super.visitEnd();
/*     */   }
/*     */ 
/*     */   public int nextId()
/*     */   {
/* 104 */     return this.counter++;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow.ClassProbesAdapter
 * JD-Core Version:    0.5.4
 */