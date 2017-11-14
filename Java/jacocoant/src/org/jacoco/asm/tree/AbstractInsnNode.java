/*     */ package org.jacoco.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ 
/*     */ public abstract class AbstractInsnNode
/*     */ {
/*     */   public static final int INSN = 0;
/*     */   public static final int INT_INSN = 1;
/*     */   public static final int VAR_INSN = 2;
/*     */   public static final int TYPE_INSN = 3;
/*     */   public static final int FIELD_INSN = 4;
/*     */   public static final int METHOD_INSN = 5;
/*     */   public static final int INVOKE_DYNAMIC_INSN = 6;
/*     */   public static final int JUMP_INSN = 7;
/*     */   public static final int LABEL = 8;
/*     */   public static final int LDC_INSN = 9;
/*     */   public static final int IINC_INSN = 10;
/*     */   public static final int TABLESWITCH_INSN = 11;
/*     */   public static final int LOOKUPSWITCH_INSN = 12;
/*     */   public static final int MULTIANEWARRAY_INSN = 13;
/*     */   public static final int FRAME = 14;
/*     */   public static final int LINE = 15;
/*     */   protected int opcode;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   AbstractInsnNode prev;
/*     */   AbstractInsnNode next;
/*     */   int index;
/*     */ 
/*     */   protected AbstractInsnNode(int opcode)
/*     */   {
/* 178 */     this.opcode = opcode;
/* 179 */     this.index = -1;
/*     */   }
/*     */ 
/*     */   public int getOpcode()
/*     */   {
/* 188 */     return this.opcode;
/*     */   }
/*     */ 
/*     */   public abstract int getType();
/*     */ 
/*     */   public AbstractInsnNode getPrevious()
/*     */   {
/* 207 */     return this.prev;
/*     */   }
/*     */ 
/*     */   public AbstractInsnNode getNext()
/*     */   {
/* 218 */     return this.next;
/*     */   }
/*     */ 
/*     */   public abstract void accept(MethodVisitor paramMethodVisitor);
/*     */ 
/*     */   protected final void acceptAnnotations(MethodVisitor mv)
/*     */   {
/* 237 */     int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations
/* 237 */       .size();
/* 238 */     for (int i = 0; i < n; ++i) {
/* 239 */       TypeAnnotationNode an = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
/* 240 */       an.accept(mv.visitInsnAnnotation(an.typeRef, an.typePath, an.desc, true));
/*     */     }
/*     */ 
/* 244 */     n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations
/* 244 */       .size();
/* 245 */     for (int i = 0; i < n; ++i) {
/* 246 */       TypeAnnotationNode an = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
/* 247 */       an.accept(mv.visitInsnAnnotation(an.typeRef, an.typePath, an.desc, false));
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract AbstractInsnNode clone(Map<LabelNode, LabelNode> paramMap);
/*     */ 
/*     */   static LabelNode clone(LabelNode label, Map<LabelNode, LabelNode> map)
/*     */   {
/* 274 */     return (LabelNode)map.get(label);
/*     */   }
/*     */ 
/*     */   static LabelNode[] clone(List<LabelNode> labels, Map<LabelNode, LabelNode> map)
/*     */   {
/* 288 */     LabelNode[] clones = new LabelNode[labels.size()];
/* 289 */     for (int i = 0; i < clones.length; ++i) {
/* 290 */       clones[i] = ((LabelNode)map.get(labels.get(i)));
/*     */     }
/* 292 */     return clones;
/*     */   }
/*     */ 
/*     */   protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode insn)
/*     */   {
/* 304 */     if (insn.visibleTypeAnnotations != null) {
/* 305 */       this.visibleTypeAnnotations = new ArrayList();
/* 306 */       for (int i = 0; i < insn.visibleTypeAnnotations.size(); ++i) {
/* 307 */         TypeAnnotationNode src = (TypeAnnotationNode)insn.visibleTypeAnnotations.get(i);
/* 308 */         TypeAnnotationNode ann = new TypeAnnotationNode(src.typeRef, src.typePath, src.desc);
/*     */ 
/* 310 */         src.accept(ann);
/* 311 */         this.visibleTypeAnnotations.add(ann);
/*     */       }
/*     */     }
/* 314 */     if (insn.invisibleTypeAnnotations != null) {
/* 315 */       this.invisibleTypeAnnotations = new ArrayList();
/* 316 */       for (int i = 0; i < insn.invisibleTypeAnnotations.size(); ++i) {
/* 317 */         TypeAnnotationNode src = (TypeAnnotationNode)insn.invisibleTypeAnnotations.get(i);
/* 318 */         TypeAnnotationNode ann = new TypeAnnotationNode(src.typeRef, src.typePath, src.desc);
/*     */ 
/* 320 */         src.accept(ann);
/* 321 */         this.invisibleTypeAnnotations.add(ann);
/*     */       }
/*     */     }
/* 324 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.asm.tree.AbstractInsnNode
 * JD-Core Version:    0.5.4
 */