/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr;
/*     */ 
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.Label;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.runtime.IExecutionDataAccessorGenerator;
/*     */ 
/*     */ class ClassFieldProbeArrayStrategy
/*     */   implements IProbeArrayStrategy
/*     */ {
/*  30 */   private static final Object[] FRAME_STACK_ARRZ = { "[Z" };
/*     */ 
/*  35 */   private static final Object[] FRAME_LOCALS_EMPTY = new Object[0];
/*     */   private final String className;
/*     */   private final long classId;
/*     */   private final boolean withFrames;
/*     */   private final IExecutionDataAccessorGenerator accessorGenerator;
/*     */ 
/*     */   ClassFieldProbeArrayStrategy(String className, long classId, boolean withFrames, IExecutionDataAccessorGenerator accessorGenerator)
/*     */   {
/*  45 */     this.className = className;
/*  46 */     this.classId = classId;
/*  47 */     this.withFrames = withFrames;
/*  48 */     this.accessorGenerator = accessorGenerator;
/*     */   }
/*     */ 
/*     */   public int storeInstance(MethodVisitor mv, boolean clinit, int variable)
/*     */   {
/*  53 */     mv.visitMethodInsn(184, this.className, "$jacocoInit", "()[Z", false);
/*     */ 
/*  56 */     mv.visitVarInsn(58, variable);
/*  57 */     return 1;
/*     */   }
/*     */ 
/*     */   public void addMembers(ClassVisitor cv, int probeCount) {
/*  61 */     createDataField(cv);
/*  62 */     createInitMethod(cv, probeCount);
/*     */   }
/*     */ 
/*     */   private void createDataField(ClassVisitor cv) {
/*  66 */     cv.visitField(4234, "$jacocoData", "[Z", null, null);
/*     */   }
/*     */ 
/*     */   private void createInitMethod(ClassVisitor cv, int probeCount)
/*     */   {
/*  71 */     MethodVisitor mv = cv.visitMethod(4106, "$jacocoInit", "()[Z", null, null);
/*     */ 
/*  74 */     mv.visitCode();
/*     */ 
/*  77 */     mv.visitFieldInsn(178, this.className, "$jacocoData", "[Z");
/*     */ 
/*  79 */     mv.visitInsn(89);
/*     */ 
/*  85 */     Label alreadyInitialized = new Label();
/*  86 */     mv.visitJumpInsn(199, alreadyInitialized);
/*     */ 
/*  90 */     mv.visitInsn(87);
/*  91 */     int size = genInitializeDataField(mv, probeCount);
/*     */ 
/*  96 */     if (this.withFrames) {
/*  97 */       mv.visitFrame(-1, 0, FRAME_LOCALS_EMPTY, 1, FRAME_STACK_ARRZ);
/*     */     }
/*     */ 
/* 100 */     mv.visitLabel(alreadyInitialized);
/* 101 */     mv.visitInsn(176);
/*     */ 
/* 103 */     mv.visitMaxs(Math.max(size, 2), 0);
/* 104 */     mv.visitEnd();
/*     */   }
/*     */ 
/*     */   private int genInitializeDataField(MethodVisitor mv, int probeCount)
/*     */   {
/* 118 */     int size = this.accessorGenerator.generateDataAccessor(this.classId, this.className, probeCount, mv);
/*     */ 
/* 123 */     mv.visitInsn(89);
/*     */ 
/* 128 */     mv.visitFieldInsn(179, this.className, "$jacocoData", "[Z");
/*     */ 
/* 133 */     return Math.max(size, 2);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.ClassFieldProbeArrayStrategy
 * JD-Core Version:    0.5.4
 */