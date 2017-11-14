/*     */ package org.jacoco.core.internal.flow;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ import org.jacoco.asm.commons.AnalyzerAdapter;
/*     */ 
/*     */ public final class MethodProbesAdapter extends MethodVisitor
/*     */ {
/*     */   private final MethodProbesVisitor probesVisitor;
/*     */   private final IProbeIdGenerator idGenerator;
/*     */   private AnalyzerAdapter analyzer;
/*     */   private final Map<Label, Label> tryCatchProbeLabels;
/*     */ 
/*     */   public MethodProbesAdapter(MethodProbesVisitor probesVisitor, IProbeIdGenerator idGenerator)
/*     */   {
/*  47 */     super(327680, probesVisitor);
/*  48 */     this.probesVisitor = probesVisitor;
/*  49 */     this.idGenerator = idGenerator;
/*  50 */     this.tryCatchProbeLabels = new HashMap();
/*     */   }
/*     */ 
/*     */   public void setAnalyzer(AnalyzerAdapter analyzer)
/*     */   {
/*  61 */     this.analyzer = analyzer;
/*     */   }
/*     */ 
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type)
/*     */   {
/*  69 */     if (this.tryCatchProbeLabels.containsKey(start)) {
/*  70 */       start = (Label)this.tryCatchProbeLabels.get(start);
/*  71 */     } else if (LabelInfo.needsProbe(start)) {
/*  72 */       Label probeLabel = new Label();
/*  73 */       LabelInfo.setSuccessor(probeLabel);
/*  74 */       this.tryCatchProbeLabels.put(start, probeLabel);
/*  75 */       start = probeLabel;
/*     */     }
/*  77 */     this.probesVisitor.visitTryCatchBlock(start, end, handler, type);
/*     */   }
/*     */ 
/*     */   public void visitLabel(Label label)
/*     */   {
/*  82 */     if (LabelInfo.needsProbe(label)) {
/*  83 */       if (this.tryCatchProbeLabels.containsKey(label)) {
/*  84 */         this.probesVisitor.visitLabel((Label)this.tryCatchProbeLabels.get(label));
/*     */       }
/*  86 */       this.probesVisitor.visitProbe(this.idGenerator.nextId());
/*     */     }
/*  88 */     this.probesVisitor.visitLabel(label);
/*     */   }
/*     */ 
/*     */   public void visitInsn(int opcode)
/*     */   {
/*  93 */     switch (opcode)
/*     */     {
/*     */     case 172:
/*     */     case 173:
/*     */     case 174:
/*     */     case 175:
/*     */     case 176:
/*     */     case 177:
/*     */     case 191:
/* 101 */       this.probesVisitor.visitInsnWithProbe(opcode, this.idGenerator.nextId());
/* 102 */       break;
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
/* 104 */       this.probesVisitor.visitInsn(opcode);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitJumpInsn(int opcode, Label label)
/*     */   {
/* 111 */     if (LabelInfo.isMultiTarget(label)) {
/* 112 */       this.probesVisitor.visitJumpInsnWithProbe(opcode, label, this.idGenerator.nextId(), frame(jumpPopCount(opcode)));
/*     */     }
/*     */     else
/* 115 */       this.probesVisitor.visitJumpInsn(opcode, label);
/*     */   }
/*     */ 
/*     */   private int jumpPopCount(int opcode)
/*     */   {
/* 120 */     switch (opcode)
/*     */     {
/*     */     case 167:
/* 122 */       return 0;
/*     */     case 153:
/*     */     case 154:
/*     */     case 155:
/*     */     case 156:
/*     */     case 157:
/*     */     case 158:
/*     */     case 198:
/*     */     case 199:
/* 131 */       return 1;
/*     */     }
/* 133 */     return 2;
/*     */   }
/*     */ 
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
/*     */   {
/* 140 */     if (markLabels(dflt, labels)) {
/* 141 */       this.probesVisitor.visitLookupSwitchInsnWithProbes(dflt, keys, labels, frame(1));
/*     */     }
/*     */     else
/* 144 */       this.probesVisitor.visitLookupSwitchInsn(dflt, keys, labels);
/*     */   }
/*     */ 
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
/*     */   {
/* 151 */     if (markLabels(dflt, labels)) {
/* 152 */       this.probesVisitor.visitTableSwitchInsnWithProbes(min, max, dflt, labels, frame(1));
/*     */     }
/*     */     else
/* 155 */       this.probesVisitor.visitTableSwitchInsn(min, max, dflt, labels);
/*     */   }
/*     */ 
/*     */   private boolean markLabels(Label dflt, Label[] labels)
/*     */   {
/* 160 */     boolean probe = false;
/* 161 */     LabelInfo.resetDone(labels);
/* 162 */     if (LabelInfo.isMultiTarget(dflt)) {
/* 163 */       LabelInfo.setProbeId(dflt, this.idGenerator.nextId());
/* 164 */       probe = true;
/*     */     }
/* 166 */     LabelInfo.setDone(dflt);
/* 167 */     for (Label l : labels) {
/* 168 */       if ((LabelInfo.isMultiTarget(l)) && (!LabelInfo.isDone(l))) {
/* 169 */         LabelInfo.setProbeId(l, this.idGenerator.nextId());
/* 170 */         probe = true;
/*     */       }
/* 172 */       LabelInfo.setDone(l);
/*     */     }
/* 174 */     return probe;
/*     */   }
/*     */ 
/*     */   private IFrame frame(int popCount) {
/* 178 */     return FrameSnapshot.create(this.analyzer, popCount);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.MethodProbesAdapter
 * JD-Core Version:    0.5.4
 */