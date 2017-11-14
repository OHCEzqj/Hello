/*     */ package org.jacoco.core.internal.flow;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.jacoco.asm.Handle;
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ import org.jacoco.asm.tree.InsnList;
/*     */ import org.jacoco.asm.tree.MethodNode;
/*     */ import org.jacoco.asm.tree.TryCatchBlockNode;
/*     */ 
/*     */ public final class LabelFlowAnalyzer extends MethodVisitor
/*     */ {
/*  48 */   boolean successor = false;
/*     */ 
/*  54 */   boolean first = true;
/*     */ 
/*  59 */   Label lineStart = null;
/*     */ 
/*     */   public static void markLabels(MethodNode method)
/*     */   {
/*  37 */     MethodVisitor lfa = new LabelFlowAnalyzer();
/*  38 */     for (int i = method.tryCatchBlocks.size(); --i >= 0; ) {
/*  39 */       ((TryCatchBlockNode)method.tryCatchBlocks.get(i)).accept(lfa);
/*     */     }
/*  41 */     method.instructions.accept(lfa);
/*     */   }
/*     */ 
/*     */   public LabelFlowAnalyzer()
/*     */   {
/*  65 */     super(327680);
/*     */   }
/*     */ 
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type)
/*     */   {
/*  75 */     LabelInfo.setTarget(start);
/*     */ 
/*  78 */     LabelInfo.setTarget(handler);
/*     */   }
/*     */ 
/*     */   public void visitJumpInsn(int opcode, Label label)
/*     */   {
/*  83 */     LabelInfo.setTarget(label);
/*  84 */     if (opcode == 168) {
/*  85 */       throw new AssertionError("Subroutines not supported.");
/*     */     }
/*  87 */     this.successor = (opcode != 167);
/*  88 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitLabel(Label label)
/*     */   {
/*  93 */     if (this.first) {
/*  94 */       LabelInfo.setTarget(label);
/*     */     }
/*  96 */     if (this.successor)
/*  97 */       LabelInfo.setSuccessor(label);
/*     */   }
/*     */ 
/*     */   public void visitLineNumber(int line, Label start)
/*     */   {
/* 103 */     this.lineStart = start;
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
/*     */   {
/* 109 */     visitSwitchInsn(dflt, labels);
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
/*     */   {
/* 115 */     visitSwitchInsn(dflt, labels);
/*     */   }
/*     */ 
/*     */   private void visitSwitchInsn(Label dflt, Label[] labels) {
/* 119 */     LabelInfo.resetDone(dflt);
/* 120 */     LabelInfo.resetDone(labels);
/* 121 */     setTargetIfNotDone(dflt);
/* 122 */     for (Label l : labels) {
/* 123 */       setTargetIfNotDone(l);
/*     */     }
/* 125 */     this.successor = false;
/* 126 */     this.first = false;
/*     */   }
/*     */ 
/*     */   private static void setTargetIfNotDone(Label label) {
/* 130 */     if (!LabelInfo.isDone(label)) {
/* 131 */       LabelInfo.setTarget(label);
/* 132 */       LabelInfo.setDone(label);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitInsn(int opcode)
/*     */   {
/* 138 */     switch (opcode)
/*     */     {
/*     */     case 169:
/* 140 */       throw new AssertionError("Subroutines not supported.");
/*     */     case 172:
/*     */     case 173:
/*     */     case 174:
/*     */     case 175:
/*     */     case 176:
/*     */     case 177:
/*     */     case 191:
/* 148 */       this.successor = false;
/* 149 */       break;
/*     */     case 170:
/*     */     case 171:
/*     */     case 178:
/*     */     case 179:
/*     */     case 180:
/*     */     case 181:
/*     */     case 182:
/*     */     case 183:
/*     */     case 184:
/*     */     case 185:
/*     */     case 186:
/*     */     case 187:
/*     */     case 188:
/*     */     case 189:
/*     */     case 190:
/*     */     default:
/* 151 */       this.successor = true;
/*     */     }
/*     */ 
/* 154 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitIntInsn(int opcode, int operand)
/*     */   {
/* 159 */     this.successor = true;
/* 160 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitVarInsn(int opcode, int var)
/*     */   {
/* 165 */     this.successor = true;
/* 166 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitTypeInsn(int opcode, String type)
/*     */   {
/* 171 */     this.successor = true;
/* 172 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc)
/*     */   {
/* 178 */     this.successor = true;
/* 179 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
/*     */   {
/* 185 */     this.successor = true;
/* 186 */     this.first = false;
/* 187 */     markMethodInvocationLine();
/*     */   }
/*     */ 
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object[] bsmArgs)
/*     */   {
/* 193 */     this.successor = true;
/* 194 */     this.first = false;
/* 195 */     markMethodInvocationLine();
/*     */   }
/*     */ 
/*     */   private void markMethodInvocationLine() {
/* 199 */     if (this.lineStart != null)
/* 200 */       LabelInfo.setMethodInvocationLine(this.lineStart);
/*     */   }
/*     */ 
/*     */   public void visitLdcInsn(Object cst)
/*     */   {
/* 206 */     this.successor = true;
/* 207 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitIincInsn(int var, int increment)
/*     */   {
/* 212 */     this.successor = true;
/* 213 */     this.first = false;
/*     */   }
/*     */ 
/*     */   public void visitMultiANewArrayInsn(String desc, int dims)
/*     */   {
/* 218 */     this.successor = true;
/* 219 */     this.first = false;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.LabelFlowAnalyzer
 * JD-Core Version:    0.5.4
 */