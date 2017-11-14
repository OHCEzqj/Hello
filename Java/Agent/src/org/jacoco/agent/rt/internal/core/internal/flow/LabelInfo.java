/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow;
/*     */ 
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.Label;
/*     */ 
/*     */ public final class LabelInfo
/*     */ {
/*     */   public static final int NO_PROBE = -1;
/*  28 */   private boolean target = false;
/*     */ 
/*  30 */   private boolean multiTarget = false;
/*     */ 
/*  32 */   private boolean successor = false;
/*     */ 
/*  34 */   private boolean methodInvocationLine = false;
/*     */ 
/*  36 */   private boolean done = false;
/*     */ 
/*  38 */   private int probeid = -1;
/*     */ 
/*  40 */   private Label intermediate = null;
/*     */ 
/*  42 */   private Instruction instruction = null;
/*     */ 
/*     */   public static void setTarget(Label label)
/*     */   {
/*  55 */     LabelInfo info = create(label);
/*  56 */     if ((info.target) || (info.successor))
/*  57 */       info.multiTarget = true;
/*     */     else
/*  59 */       info.target = true;
/*     */   }
/*     */ 
/*     */   public static void setSuccessor(Label label)
/*     */   {
/*  71 */     LabelInfo info = create(label);
/*  72 */     info.successor = true;
/*  73 */     if (info.target)
/*  74 */       info.multiTarget = true;
/*     */   }
/*     */ 
/*     */   public static boolean isMultiTarget(Label label)
/*     */   {
/*  90 */     LabelInfo info = get(label);
/*  91 */     return (info == null) ? false : info.multiTarget;
/*     */   }
/*     */ 
/*     */   public static boolean isSuccessor(Label label)
/*     */   {
/* 105 */     LabelInfo info = get(label);
/* 106 */     return (info == null) ? false : info.successor;
/*     */   }
/*     */ 
/*     */   public static void setMethodInvocationLine(Label label)
/*     */   {
/* 116 */     create(label).methodInvocationLine = true;
/*     */   }
/*     */ 
/*     */   public static boolean isMethodInvocationLine(Label label)
/*     */   {
/* 129 */     LabelInfo info = get(label);
/* 130 */     return (info == null) ? false : info.methodInvocationLine;
/*     */   }
/*     */ 
/*     */   public static boolean needsProbe(Label label)
/*     */   {
/* 141 */     LabelInfo info = get(label);
/* 142 */     return (info != null) && (info.successor) && (((info.multiTarget) || (info.methodInvocationLine)));
/*     */   }
/*     */ 
/*     */   public static void setDone(Label label)
/*     */   {
/* 153 */     create(label).done = true;
/*     */   }
/*     */ 
/*     */   public static void resetDone(Label label)
/*     */   {
/* 163 */     LabelInfo info = get(label);
/* 164 */     if (info != null)
/* 165 */       info.done = false;
/*     */   }
/*     */ 
/*     */   public static void resetDone(Label[] labels)
/*     */   {
/* 176 */     for (Label label : labels)
/* 177 */       resetDone(label);
/*     */   }
/*     */ 
/*     */   public static boolean isDone(Label label)
/*     */   {
/* 189 */     LabelInfo info = get(label);
/* 190 */     return (info == null) ? false : info.done;
/*     */   }
/*     */ 
/*     */   public static void setProbeId(Label label, int id)
/*     */   {
/* 202 */     create(label).probeid = id;
/*     */   }
/*     */ 
/*     */   public static int getProbeId(Label label)
/*     */   {
/* 214 */     LabelInfo info = get(label);
/* 215 */     return (info == null) ? -1 : info.probeid;
/*     */   }
/*     */ 
/*     */   public static void setIntermediateLabel(Label label, Label intermediate)
/*     */   {
/* 229 */     create(label).intermediate = intermediate;
/*     */   }
/*     */ 
/*     */   public static Label getIntermediateLabel(Label label)
/*     */   {
/* 241 */     LabelInfo info = get(label);
/* 242 */     return (info == null) ? null : info.intermediate;
/*     */   }
/*     */ 
/*     */   public static void setInstruction(Label label, Instruction instruction)
/*     */   {
/* 255 */     create(label).instruction = instruction;
/*     */   }
/*     */ 
/*     */   public static Instruction getInstruction(Label label)
/*     */   {
/* 267 */     LabelInfo info = get(label);
/* 268 */     return (info == null) ? null : info.instruction;
/*     */   }
/*     */ 
/*     */   private static LabelInfo get(Label label) {
/* 272 */     Object info = label.info;
/* 273 */     return (info instanceof LabelInfo) ? (LabelInfo)info : null;
/*     */   }
/*     */ 
/*     */   private static LabelInfo create(Label label) {
/* 277 */     LabelInfo info = get(label);
/* 278 */     if (info == null) {
/* 279 */       info = new LabelInfo();
/* 280 */       label.info = info;
/*     */     }
/* 282 */     return info;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow.LabelInfo
 * JD-Core Version:    0.5.4
 */