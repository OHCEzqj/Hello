/*     */ package org.jacoco.core.internal.instr;
/*     */ 
/*     */ import org.jacoco.asm.Handle;
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ 
/*     */ class DuplicateFrameEliminator extends MethodVisitor
/*     */ {
/*     */   private boolean instruction;
/*     */ 
/*     */   public DuplicateFrameEliminator(MethodVisitor mv)
/*     */   {
/*  29 */     super(327680, mv);
/*  30 */     this.instruction = true;
/*     */   }
/*     */ 
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack)
/*     */   {
/*  36 */     if (this.instruction) {
/*  37 */       this.instruction = false;
/*  38 */       this.mv.visitFrame(type, nLocal, local, nStack, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitInsn(int opcode)
/*     */   {
/*  44 */     this.instruction = true;
/*  45 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */ 
/*     */   public void visitIntInsn(int opcode, int operand)
/*     */   {
/*  50 */     this.instruction = true;
/*  51 */     this.mv.visitIntInsn(opcode, operand);
/*     */   }
/*     */ 
/*     */   public void visitVarInsn(int opcode, int var)
/*     */   {
/*  56 */     this.instruction = true;
/*  57 */     this.mv.visitVarInsn(opcode, var);
/*     */   }
/*     */ 
/*     */   public void visitTypeInsn(int opcode, String type)
/*     */   {
/*  62 */     this.instruction = true;
/*  63 */     this.mv.visitTypeInsn(opcode, type);
/*     */   }
/*     */ 
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc)
/*     */   {
/*  69 */     this.instruction = true;
/*  70 */     this.mv.visitFieldInsn(opcode, owner, name, desc);
/*     */   }
/*     */ 
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
/*     */   {
/*  76 */     this.instruction = true;
/*  77 */     this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */   }
/*     */ 
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object[] bsmArgs)
/*     */   {
/*  83 */     this.instruction = true;
/*  84 */     this.mv.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
/*     */   }
/*     */ 
/*     */   public void visitJumpInsn(int opcode, Label label)
/*     */   {
/*  89 */     this.instruction = true;
/*  90 */     this.mv.visitJumpInsn(opcode, label);
/*     */   }
/*     */ 
/*     */   public void visitLdcInsn(Object cst)
/*     */   {
/*  95 */     this.instruction = true;
/*  96 */     this.mv.visitLdcInsn(cst);
/*     */   }
/*     */ 
/*     */   public void visitIincInsn(int var, int increment)
/*     */   {
/* 101 */     this.instruction = true;
/* 102 */     this.mv.visitIincInsn(var, increment);
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
/*     */   {
/* 108 */     this.instruction = true;
/* 109 */     this.mv.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
/*     */   {
/* 115 */     this.instruction = true;
/* 116 */     this.mv.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */ 
/*     */   public void visitMultiANewArrayInsn(String desc, int dims)
/*     */   {
/* 121 */     this.instruction = true;
/* 122 */     this.mv.visitMultiANewArrayInsn(desc, dims);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.DuplicateFrameEliminator
 * JD-Core Version:    0.5.4
 */