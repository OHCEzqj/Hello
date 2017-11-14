/*     */ package org.jacoco.core.internal.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jacoco.asm.Handle;
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.IMethodCoverage;
/*     */ import org.jacoco.core.internal.flow.IFrame;
/*     */ import org.jacoco.core.internal.flow.Instruction;
/*     */ import org.jacoco.core.internal.flow.LabelInfo;
/*     */ import org.jacoco.core.internal.flow.MethodProbesVisitor;
/*     */ 
/*     */ public class MethodAnalyzer extends MethodProbesVisitor
/*     */ {
/*     */   private final boolean[] probes;
/*     */   private final MethodCoverageImpl coverage;
/*  37 */   private int currentLine = -1;
/*     */ 
/*  39 */   private int firstLine = -1;
/*     */ 
/*  41 */   private int lastLine = -1;
/*     */ 
/*  44 */   private final List<Label> currentLabel = new ArrayList(2);
/*     */ 
/*  47 */   private final List<Instruction> instructions = new ArrayList();
/*     */ 
/*  50 */   private final List<Instruction> coveredProbes = new ArrayList();
/*     */ 
/*  53 */   private final List<Jump> jumps = new ArrayList();
/*     */   private Instruction lastInsn;
/*     */ 
/*     */   public MethodAnalyzer(String name, String desc, String signature, boolean[] probes)
/*     */   {
/*  75 */     this.probes = probes;
/*  76 */     this.coverage = new MethodCoverageImpl(name, desc, signature);
/*     */   }
/*     */ 
/*     */   public IMethodCoverage getCoverage()
/*     */   {
/*  86 */     return this.coverage;
/*     */   }
/*     */ 
/*     */   public void visitLabel(Label label)
/*     */   {
/*  91 */     this.currentLabel.add(label);
/*  92 */     if (!LabelInfo.isSuccessor(label))
/*  93 */       this.lastInsn = null;
/*     */   }
/*     */ 
/*     */   public void visitLineNumber(int line, Label start)
/*     */   {
/*  99 */     this.currentLine = line;
/* 100 */     if ((this.firstLine > line) || (this.lastLine == -1)) {
/* 101 */       this.firstLine = line;
/*     */     }
/* 103 */     if (this.lastLine < line)
/* 104 */       this.lastLine = line;
/*     */   }
/*     */ 
/*     */   private void visitInsn()
/*     */   {
/* 109 */     Instruction insn = new Instruction(this.currentLine);
/* 110 */     this.instructions.add(insn);
/* 111 */     if (this.lastInsn != null) {
/* 112 */       insn.setPredecessor(this.lastInsn);
/*     */     }
/* 114 */     int labelCount = this.currentLabel.size();
/* 115 */     if (labelCount > 0) {
/* 116 */       for (int i = labelCount; --i >= 0; ) {
/* 117 */         LabelInfo.setInstruction((Label)this.currentLabel.get(i), insn);
/*     */       }
/* 119 */       this.currentLabel.clear();
/*     */     }
/* 121 */     this.lastInsn = insn;
/*     */   }
/*     */ 
/*     */   public void visitInsn(int opcode)
/*     */   {
/* 126 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitIntInsn(int opcode, int operand)
/*     */   {
/* 131 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitVarInsn(int opcode, int var)
/*     */   {
/* 136 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitTypeInsn(int opcode, String type)
/*     */   {
/* 141 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc)
/*     */   {
/* 147 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
/*     */   {
/* 153 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object[] bsmArgs)
/*     */   {
/* 159 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitJumpInsn(int opcode, Label label)
/*     */   {
/* 164 */     visitInsn();
/* 165 */     this.jumps.add(new Jump(this.lastInsn, label));
/*     */   }
/*     */ 
/*     */   public void visitLdcInsn(Object cst)
/*     */   {
/* 170 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitIincInsn(int var, int increment)
/*     */   {
/* 175 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
/*     */   {
/* 181 */     visitSwitchInsn(dflt, labels);
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
/*     */   {
/* 187 */     visitSwitchInsn(dflt, labels);
/*     */   }
/*     */ 
/*     */   private void visitSwitchInsn(Label dflt, Label[] labels) {
/* 191 */     visitInsn();
/* 192 */     LabelInfo.resetDone(labels);
/* 193 */     this.jumps.add(new Jump(this.lastInsn, dflt));
/* 194 */     LabelInfo.setDone(dflt);
/* 195 */     for (Label l : labels)
/* 196 */       if (!LabelInfo.isDone(l)) {
/* 197 */         this.jumps.add(new Jump(this.lastInsn, l));
/* 198 */         LabelInfo.setDone(l);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void visitMultiANewArrayInsn(String desc, int dims)
/*     */   {
/* 205 */     visitInsn();
/*     */   }
/*     */ 
/*     */   public void visitProbe(int probeId)
/*     */   {
/* 210 */     addProbe(probeId);
/* 211 */     this.lastInsn = null;
/*     */   }
/*     */ 
/*     */   public void visitJumpInsnWithProbe(int opcode, Label label, int probeId, IFrame frame)
/*     */   {
/* 217 */     visitInsn();
/* 218 */     addProbe(probeId);
/*     */   }
/*     */ 
/*     */   public void visitInsnWithProbe(int opcode, int probeId)
/*     */   {
/* 223 */     visitInsn();
/* 224 */     addProbe(probeId);
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsnWithProbes(int min, int max, Label dflt, Label[] labels, IFrame frame)
/*     */   {
/* 230 */     visitSwitchInsnWithProbes(dflt, labels);
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsnWithProbes(Label dflt, int[] keys, Label[] labels, IFrame frame)
/*     */   {
/* 236 */     visitSwitchInsnWithProbes(dflt, labels);
/*     */   }
/*     */ 
/*     */   private void visitSwitchInsnWithProbes(Label dflt, Label[] labels)
/*     */   {
/* 241 */     visitInsn();
/* 242 */     LabelInfo.resetDone(dflt);
/* 243 */     LabelInfo.resetDone(labels);
/* 244 */     visitSwitchTarget(dflt);
/* 245 */     for (Label l : labels)
/* 246 */       visitSwitchTarget(l);
/*     */   }
/*     */ 
/*     */   private void visitSwitchTarget(Label label)
/*     */   {
/* 251 */     int id = LabelInfo.getProbeId(label);
/* 252 */     if (!LabelInfo.isDone(label)) {
/* 253 */       if (id == -1)
/* 254 */         this.jumps.add(new Jump(this.lastInsn, label));
/*     */       else {
/* 256 */         addProbe(id);
/*     */       }
/* 258 */       LabelInfo.setDone(label);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitEnd()
/*     */   {
/* 265 */     for (Jump j : this.jumps) {
/* 266 */       LabelInfo.getInstruction(j.target).setPredecessor(j.source);
/*     */     }
/*     */ 
/* 269 */     for (Instruction p : this.coveredProbes) {
/* 270 */       p.setCovered();
/*     */     }
/*     */ 
/* 273 */     this.coverage.ensureCapacity(this.firstLine, this.lastLine);
/* 274 */     for (Instruction i : this.instructions) {
/* 275 */       int total = i.getBranches();
/* 276 */       int covered = i.getCoveredBranches();
/* 277 */       ICounter instrCounter = (covered == 0) ? CounterImpl.COUNTER_1_0 : CounterImpl.COUNTER_0_1;
/*     */ 
/* 279 */       ICounter branchCounter = (total > 1) ? CounterImpl.getInstance(total - covered, covered) : CounterImpl.COUNTER_0_0;
/*     */ 
/* 281 */       this.coverage.increment(instrCounter, branchCounter, i.getLine());
/*     */     }
/* 283 */     this.coverage.incrementMethodCounter();
/*     */   }
/*     */ 
/*     */   private void addProbe(int probeId) {
/* 287 */     this.lastInsn.addBranch();
/* 288 */     if ((this.probes != null) && (this.probes[probeId] != 0))
/* 289 */       this.coveredProbes.add(this.lastInsn);
/*     */   }
/*     */ 
/*     */   private static class Jump
/*     */   {
/*     */     final Instruction source;
/*     */     final Label target;
/*     */ 
/*     */     Jump(Instruction source, Label target)
/*     */     {
/* 299 */       this.source = source;
/* 300 */       this.target = target;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.MethodAnalyzer
 * JD-Core Version:    0.5.4
 */