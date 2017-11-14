/*     */ package org.jacoco.core.internal.instr;
/*     */ 
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ import org.jacoco.asm.Opcodes;
/*     */ import org.jacoco.asm.Type;
/*     */ 
/*     */ class ProbeInserter extends MethodVisitor
/*     */   implements IProbeInserter
/*     */ {
/*     */   private final IProbeArrayStrategy arrayStrategy;
/*     */   private final boolean clinit;
/*     */   private final int variable;
/*     */   private int accessorStackSize;
/*     */ 
/*     */   ProbeInserter(int access, String name, String desc, MethodVisitor mv, IProbeArrayStrategy arrayStrategy)
/*     */   {
/*  58 */     super(327680, mv);
/*  59 */     this.clinit = "<clinit>".equals(name);
/*  60 */     this.arrayStrategy = arrayStrategy;
/*  61 */     int pos = ((0x8 & access) == 0) ? 1 : 0;
/*  62 */     for (Type t : Type.getArgumentTypes(desc)) {
/*  63 */       pos += t.getSize();
/*     */     }
/*  65 */     this.variable = pos;
/*     */   }
/*     */ 
/*     */   public void insertProbe(int id)
/*     */   {
/*  73 */     this.mv.visitVarInsn(25, this.variable);
/*     */ 
/*  77 */     InstrSupport.push(this.mv, id);
/*     */ 
/*  82 */     this.mv.visitInsn(4);
/*     */ 
/*  88 */     this.mv.visitInsn(84);
/*     */   }
/*     */ 
/*     */   public void visitCode()
/*     */   {
/*  93 */     this.accessorStackSize = this.arrayStrategy.storeInstance(this.mv, this.clinit, this.variable);
/*  94 */     this.mv.visitCode();
/*     */   }
/*     */ 
/*     */   public final void visitVarInsn(int opcode, int var)
/*     */   {
/*  99 */     this.mv.visitVarInsn(opcode, map(var));
/*     */   }
/*     */ 
/*     */   public final void visitIincInsn(int var, int increment)
/*     */   {
/* 104 */     this.mv.visitIincInsn(map(var), increment);
/*     */   }
/*     */ 
/*     */   public final void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index)
/*     */   {
/* 111 */     this.mv.visitLocalVariable(name, desc, signature, start, end, map(index));
/*     */   }
/*     */ 
/*     */   public void visitMaxs(int maxStack, int maxLocals)
/*     */   {
/* 120 */     int increasedStack = Math.max(maxStack + 3, this.accessorStackSize);
/* 121 */     this.mv.visitMaxs(increasedStack, maxLocals + 1);
/*     */   }
/*     */ 
/*     */   private int map(int var) {
/* 125 */     if (var < this.variable) {
/* 126 */       return var;
/*     */     }
/* 128 */     return var + 1;
/*     */   }
/*     */ 
/*     */   public final void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack)
/*     */   {
/* 136 */     if (type != -1) {
/* 137 */       throw new IllegalArgumentException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
/*     */     }
/*     */ 
/* 141 */     Object[] newLocal = new Object[Math.max(nLocal, this.variable) + 1];
/* 142 */     int idx = 0;
/* 143 */     int newIdx = 0;
/* 144 */     int pos = 0;
/* 145 */     while ((idx < nLocal) || (pos <= this.variable)) {
/* 146 */       if (pos == this.variable) {
/* 147 */         newLocal[(newIdx++)] = "[Z";
/* 148 */         ++pos;
/*     */       }
/* 150 */       if (idx < nLocal) {
/* 151 */         Object t = local[(idx++)];
/* 152 */         newLocal[(newIdx++)] = t;
/* 153 */         ++pos;
/* 154 */         if ((t == Opcodes.LONG) || (t == Opcodes.DOUBLE)) {
/* 155 */           ++pos;
/*     */         }
/*     */       }
/*     */ 
/* 159 */       newLocal[(newIdx++)] = Opcodes.TOP;
/* 160 */       ++pos;
/*     */     }
/*     */ 
/* 164 */     this.mv.visitFrame(type, newIdx, newLocal, nStack, stack);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.ProbeInserter
 * JD-Core Version:    0.5.4
 */