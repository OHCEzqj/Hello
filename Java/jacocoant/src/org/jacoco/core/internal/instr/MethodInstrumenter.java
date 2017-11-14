/*     */ package org.jacoco.core.internal.instr;
/*     */ 
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ import org.jacoco.core.internal.flow.IFrame;
/*     */ import org.jacoco.core.internal.flow.LabelInfo;
/*     */ import org.jacoco.core.internal.flow.MethodProbesVisitor;
/*     */ 
/*     */ class MethodInstrumenter extends MethodProbesVisitor
/*     */ {
/*     */   private final IProbeInserter probeInserter;
/*     */ 
/*     */   public MethodInstrumenter(MethodVisitor mv, IProbeInserter probeInserter)
/*     */   {
/*  39 */     super(mv);
/*  40 */     this.probeInserter = probeInserter;
/*     */   }
/*     */ 
/*     */   public void visitProbe(int probeId)
/*     */   {
/*  47 */     this.probeInserter.insertProbe(probeId);
/*     */   }
/*     */ 
/*     */   public void visitInsnWithProbe(int opcode, int probeId)
/*     */   {
/*  52 */     this.probeInserter.insertProbe(probeId);
/*  53 */     this.mv.visitInsn(opcode);
/*     */   }
/*     */ 
/*     */   public void visitJumpInsnWithProbe(int opcode, Label label, int probeId, IFrame frame)
/*     */   {
/*  59 */     if (opcode == 167) {
/*  60 */       this.probeInserter.insertProbe(probeId);
/*  61 */       this.mv.visitJumpInsn(167, label);
/*     */     } else {
/*  63 */       Label intermediate = new Label();
/*  64 */       this.mv.visitJumpInsn(getInverted(opcode), intermediate);
/*  65 */       this.probeInserter.insertProbe(probeId);
/*  66 */       this.mv.visitJumpInsn(167, label);
/*  67 */       this.mv.visitLabel(intermediate);
/*  68 */       frame.accept(this.mv);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getInverted(int opcode) {
/*  73 */     switch (opcode) { case 153:
/*  75 */       return 154;
/*     */     case 154:
/*  77 */       return 153;
/*     */     case 155:
/*  79 */       return 156;
/*     */     case 156:
/*  81 */       return 155;
/*     */     case 157:
/*  83 */       return 158;
/*     */     case 158:
/*  85 */       return 157;
/*     */     case 159:
/*  87 */       return 160;
/*     */     case 160:
/*  89 */       return 159;
/*     */     case 161:
/*  91 */       return 162;
/*     */     case 162:
/*  93 */       return 161;
/*     */     case 163:
/*  95 */       return 164;
/*     */     case 164:
/*  97 */       return 163;
/*     */     case 165:
/*  99 */       return 166;
/*     */     case 166:
/* 101 */       return 165;
/*     */     case 198:
/* 103 */       return 199;
/*     */     case 199:
/* 105 */       return 198;
/*     */     case 167:
/*     */     case 168:
/*     */     case 169:
/*     */     case 170:
/*     */     case 171:
/*     */     case 172:
/*     */     case 173:
/*     */     case 174:
/*     */     case 175:
/*     */     case 176:
/*     */     case 177:
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
/*     */     case 191:
/*     */     case 192:
/*     */     case 193:
/*     */     case 194:
/*     */     case 195:
/*     */     case 196:
/*     */     case 197: } throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsnWithProbes(int min, int max, Label dflt, Label[] labels, IFrame frame)
/*     */   {
/* 114 */     LabelInfo.resetDone(dflt);
/* 115 */     LabelInfo.resetDone(labels);
/* 116 */     Label newDflt = createIntermediate(dflt);
/* 117 */     Label[] newLabels = createIntermediates(labels);
/* 118 */     this.mv.visitTableSwitchInsn(min, max, newDflt, newLabels);
/*     */ 
/* 121 */     insertIntermediateProbes(dflt, labels, frame);
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsnWithProbes(Label dflt, int[] keys, Label[] labels, IFrame frame)
/*     */   {
/* 128 */     LabelInfo.resetDone(dflt);
/* 129 */     LabelInfo.resetDone(labels);
/* 130 */     Label newDflt = createIntermediate(dflt);
/* 131 */     Label[] newLabels = createIntermediates(labels);
/* 132 */     this.mv.visitLookupSwitchInsn(newDflt, keys, newLabels);
/*     */ 
/* 135 */     insertIntermediateProbes(dflt, labels, frame);
/*     */   }
/*     */ 
/*     */   private Label[] createIntermediates(Label[] labels) {
/* 139 */     Label[] intermediates = new Label[labels.length];
/* 140 */     for (int i = 0; i < labels.length; ++i) {
/* 141 */       intermediates[i] = createIntermediate(labels[i]);
/*     */     }
/* 143 */     return intermediates;
/*     */   }
/*     */ 
/*     */   private Label createIntermediate(Label label)
/*     */   {
/*     */     Label intermediate;
/*     */     Label intermediate;
/* 148 */     if (LabelInfo.getProbeId(label) == -1) {
/* 149 */       intermediate = label;
/*     */     }
/*     */     else
/*     */     {
/*     */       Label intermediate;
/* 151 */       if (LabelInfo.isDone(label)) {
/* 152 */         intermediate = LabelInfo.getIntermediateLabel(label);
/*     */       } else {
/* 154 */         intermediate = new Label();
/* 155 */         LabelInfo.setIntermediateLabel(label, intermediate);
/* 156 */         LabelInfo.setDone(label);
/*     */       }
/*     */     }
/* 159 */     return intermediate;
/*     */   }
/*     */ 
/*     */   private void insertIntermediateProbe(Label label, IFrame frame) {
/* 163 */     int probeId = LabelInfo.getProbeId(label);
/* 164 */     if ((probeId != -1) && (!LabelInfo.isDone(label))) {
/* 165 */       this.mv.visitLabel(LabelInfo.getIntermediateLabel(label));
/* 166 */       frame.accept(this.mv);
/* 167 */       this.probeInserter.insertProbe(probeId);
/* 168 */       this.mv.visitJumpInsn(167, label);
/* 169 */       LabelInfo.setDone(label);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insertIntermediateProbes(Label dflt, Label[] labels, IFrame frame)
/*     */   {
/* 175 */     LabelInfo.resetDone(dflt);
/* 176 */     LabelInfo.resetDone(labels);
/* 177 */     insertIntermediateProbe(dflt, frame);
/* 178 */     for (Label l : labels)
/* 179 */       insertIntermediateProbe(l, frame);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.MethodInstrumenter
 * JD-Core Version:    0.5.4
 */