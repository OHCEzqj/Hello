/*     */ package org.jacoco.asm.commons;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.jacoco.asm.Label;
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ import org.jacoco.asm.Opcodes;
/*     */ import org.jacoco.asm.tree.AbstractInsnNode;
/*     */ import org.jacoco.asm.tree.InsnList;
/*     */ import org.jacoco.asm.tree.InsnNode;
/*     */ import org.jacoco.asm.tree.JumpInsnNode;
/*     */ import org.jacoco.asm.tree.LabelNode;
/*     */ import org.jacoco.asm.tree.LocalVariableNode;
/*     */ import org.jacoco.asm.tree.LookupSwitchInsnNode;
/*     */ import org.jacoco.asm.tree.MethodNode;
/*     */ import org.jacoco.asm.tree.TableSwitchInsnNode;
/*     */ import org.jacoco.asm.tree.TryCatchBlockNode;
/*     */ 
/*     */ public class JSRInlinerAdapter extends MethodNode
/*     */   implements Opcodes
/*     */ {
/*     */   private static final boolean LOGGING = false;
/*  72 */   private final Map<LabelNode, BitSet> subroutineHeads = new HashMap();
/*     */ 
/*  79 */   private final BitSet mainSubroutine = new BitSet();
/*     */ 
/*  85 */   final BitSet dualCitizens = new BitSet();
/*     */ 
/*     */   public JSRInlinerAdapter(MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions)
/*     */   {
/* 116 */     this(327680, mv, access, name, desc, signature, exceptions);
/* 117 */     if (super.getClass() != JSRInlinerAdapter.class)
/* 118 */       throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   protected JSRInlinerAdapter(int api, MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions)
/*     */   {
/* 149 */     super(api, access, name, desc, signature, exceptions);
/* 150 */     this.mv = mv;
/*     */   }
/*     */ 
/*     */   public void visitJumpInsn(int opcode, Label lbl)
/*     */   {
/* 159 */     super.visitJumpInsn(opcode, lbl);
/* 160 */     LabelNode ln = ((JumpInsnNode)this.instructions.getLast()).label;
/* 161 */     if ((opcode == 168) && (!this.subroutineHeads.containsKey(ln)))
/* 162 */       this.subroutineHeads.put(ln, new BitSet());
/*     */   }
/*     */ 
/*     */   public void visitEnd()
/*     */   {
/* 172 */     if (!this.subroutineHeads.isEmpty()) {
/* 173 */       markSubroutines();
/*     */ 
/* 182 */       emitCode();
/*     */     }
/*     */ 
/* 186 */     if (this.mv != null)
/* 187 */       accept(this.mv);
/*     */   }
/*     */ 
/*     */   private void markSubroutines()
/*     */   {
/* 196 */     BitSet anyvisited = new BitSet();
/*     */ 
/* 200 */     markSubroutineWalk(this.mainSubroutine, 0, anyvisited);
/*     */ 
/* 205 */     for (Iterator it = this.subroutineHeads
/* 205 */       .entrySet().iterator(); it.hasNext(); ) {
/* 206 */       Map.Entry entry = (Map.Entry)it.next();
/* 207 */       LabelNode lab = (LabelNode)entry.getKey();
/* 208 */       BitSet sub = (BitSet)entry.getValue();
/* 209 */       int index = this.instructions.indexOf(lab);
/* 210 */       markSubroutineWalk(sub, index, anyvisited);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void markSubroutineWalk(BitSet sub, int index, BitSet anyvisited)
/*     */   {
/* 237 */     markSubroutineWalkDFS(sub, index, anyvisited);
/*     */ 
/* 240 */     boolean loop = true;
/* 241 */     while (loop) {
/* 242 */       loop = false;
/* 243 */       Iterator it = this.tryCatchBlocks.iterator();
/* 244 */       while (it.hasNext()) {
/* 245 */         TryCatchBlockNode trycatch = (TryCatchBlockNode)it.next();
/*     */ 
/* 253 */         int handlerindex = this.instructions.indexOf(trycatch.handler);
/* 254 */         if (sub.get(handlerindex)) {
/*     */           continue;
/*     */         }
/*     */ 
/* 258 */         int startindex = this.instructions.indexOf(trycatch.start);
/* 259 */         int endindex = this.instructions.indexOf(trycatch.end);
/* 260 */         int nextbit = sub.nextSetBit(startindex);
/* 261 */         if ((nextbit != -1) && (nextbit < endindex))
/*     */         {
/* 267 */           markSubroutineWalkDFS(sub, handlerindex, anyvisited);
/* 268 */           loop = true;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void markSubroutineWalkDFS(BitSet sub, int index, BitSet anyvisited)
/*     */   {
/*     */     while (true)
/*     */     {
/* 290 */       AbstractInsnNode node = this.instructions.get(index);
/*     */ 
/* 293 */       if (sub.get(index)) {
/* 294 */         return;
/*     */       }
/* 296 */       sub.set(index);
/*     */ 
/* 299 */       if (anyvisited.get(index)) {
/* 300 */         this.dualCitizens.set(index);
/*     */       }
/*     */ 
/* 305 */       anyvisited.set(index);
/*     */ 
/* 307 */       if ((node.getType() == 7) && 
/* 308 */         (node
/* 308 */         .getOpcode() != 168))
/*     */       {
/* 311 */         JumpInsnNode jnode = (JumpInsnNode)node;
/* 312 */         int destidx = this.instructions.indexOf(jnode.label);
/* 313 */         markSubroutineWalkDFS(sub, destidx, anyvisited);
/*     */       }
/* 315 */       if (node.getType() == 11) {
/* 316 */         TableSwitchInsnNode tsnode = (TableSwitchInsnNode)node;
/* 317 */         int destidx = this.instructions.indexOf(tsnode.dflt);
/* 318 */         markSubroutineWalkDFS(sub, destidx, anyvisited);
/* 319 */         for (int i = tsnode.labels.size() - 1; i >= 0; --i) {
/* 320 */           LabelNode l = (LabelNode)tsnode.labels.get(i);
/* 321 */           destidx = this.instructions.indexOf(l);
/* 322 */           markSubroutineWalkDFS(sub, destidx, anyvisited);
/*     */         }
/*     */       }
/* 325 */       if (node.getType() == 12) {
/* 326 */         LookupSwitchInsnNode lsnode = (LookupSwitchInsnNode)node;
/* 327 */         int destidx = this.instructions.indexOf(lsnode.dflt);
/* 328 */         markSubroutineWalkDFS(sub, destidx, anyvisited);
/* 329 */         for (int i = lsnode.labels.size() - 1; i >= 0; --i) {
/* 330 */           LabelNode l = (LabelNode)lsnode.labels.get(i);
/* 331 */           destidx = this.instructions.indexOf(l);
/* 332 */           markSubroutineWalkDFS(sub, destidx, anyvisited);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 338 */       switch (this.instructions.get(index).getOpcode()) { case 167:
/*     */       case 169:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/*     */       case 177:
/*     */       case 191:
/* 354 */         return;
/*     */       case 168:
/*     */       case 178:
/*     */       case 179:
/*     */       case 180:
/*     */       case 181:
/*     */       case 182:
/*     */       case 183:
/*     */       case 184:
/*     */       case 185:
/*     */       case 186:
/*     */       case 187:
/*     */       case 188:
/*     */       case 189:
/*     */       case 190: } ++index;
/*     */ 
/* 368 */       if (index >= this.instructions.size())
/* 369 */         return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void emitCode()
/*     */   {
/* 379 */     LinkedList worklist = new LinkedList();
/*     */ 
/* 382 */     worklist.add(new Instantiation(null, this.mainSubroutine));
/*     */ 
/* 386 */     InsnList newInstructions = new InsnList();
/* 387 */     List newTryCatchBlocks = new ArrayList();
/* 388 */     List newLocalVariables = new ArrayList();
/* 389 */     while (!worklist.isEmpty()) {
/* 390 */       Instantiation inst = (Instantiation)worklist.removeFirst();
/* 391 */       emitSubroutine(inst, worklist, newInstructions, newTryCatchBlocks, newLocalVariables);
/*     */     }
/*     */ 
/* 394 */     this.instructions = newInstructions;
/* 395 */     this.tryCatchBlocks = newTryCatchBlocks;
/* 396 */     this.localVariables = newLocalVariables;
/*     */   }
/*     */ 
/*     */   private void emitSubroutine(Instantiation instant, List<Instantiation> worklist, InsnList newInstructions, List<TryCatchBlockNode> newTryCatchBlocks, List<LocalVariableNode> newLocalVariables)
/*     */   {
/* 420 */     LabelNode duplbl = null;
/*     */ 
/* 429 */     int i = 0; for (int c = this.instructions.size(); i < c; ++i) {
/* 430 */       AbstractInsnNode insn = this.instructions.get(i);
/* 431 */       Instantiation owner = instant.findOwner(i);
/*     */ 
/* 434 */       if (insn.getType() == 8)
/*     */       {
/* 439 */         LabelNode ilbl = (LabelNode)insn;
/* 440 */         LabelNode remap = instant.rangeLabel(ilbl);
/*     */ 
/* 445 */         if (remap != duplbl) {
/* 446 */           newInstructions.add(remap);
/* 447 */           duplbl = remap;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 457 */         if (owner != instant)
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 465 */         if (insn.getOpcode() == 169)
/*     */         {
/* 474 */           LabelNode retlabel = null;
/* 475 */           for (Instantiation p = instant; p != null; p = p.previous) {
/* 476 */             if (p.subroutine.get(i)) {
/* 477 */               retlabel = p.returnLabel;
/*     */             }
/*     */           }
/* 480 */           if (retlabel == null)
/*     */           {
/* 484 */             throw new RuntimeException("Instruction #" + i + " is a RET not owned by any subroutine");
/*     */           }
/*     */ 
/* 487 */           newInstructions.add(new JumpInsnNode(167, retlabel));
/* 488 */         } else if (insn.getOpcode() == 168) {
/* 489 */           LabelNode lbl = ((JumpInsnNode)insn).label;
/* 490 */           BitSet sub = (BitSet)this.subroutineHeads.get(lbl);
/* 491 */           Instantiation newinst = new Instantiation(instant, sub);
/* 492 */           LabelNode startlbl = newinst.gotoLabel(lbl);
/*     */ 
/* 503 */           newInstructions.add(new InsnNode(1));
/* 504 */           newInstructions.add(new JumpInsnNode(167, startlbl));
/* 505 */           newInstructions.add(newinst.returnLabel);
/*     */ 
/* 509 */           worklist.add(newinst);
/*     */         } else {
/* 511 */           newInstructions.add(insn.clone(instant));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 516 */     Iterator it = this.tryCatchBlocks.iterator();
/* 517 */     while (it.hasNext()) {
/* 518 */       TryCatchBlockNode trycatch = (TryCatchBlockNode)it.next();
/*     */ 
/* 526 */       LabelNode start = instant.rangeLabel(trycatch.start);
/* 527 */       LabelNode end = instant.rangeLabel(trycatch.end);
/*     */ 
/* 530 */       if (start == end)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 537 */       LabelNode handler = instant.gotoLabel(trycatch.handler);
/*     */ 
/* 545 */       if ((start == null) || (end == null) || (handler == null)) {
/* 546 */         throw new RuntimeException("Internal error!");
/*     */       }
/*     */ 
/* 549 */       newTryCatchBlocks.add(new TryCatchBlockNode(start, end, handler, trycatch.type));
/*     */     }
/*     */ 
/* 553 */     Iterator it = this.localVariables.iterator();
/* 554 */     while (it.hasNext()) {
/* 555 */       LocalVariableNode lvnode = (LocalVariableNode)it.next();
/*     */ 
/* 559 */       LabelNode start = instant.rangeLabel(lvnode.start);
/* 560 */       LabelNode end = instant.rangeLabel(lvnode.end);
/* 561 */       if (start == end)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 567 */       newLocalVariables.add(new LocalVariableNode(lvnode.name, lvnode.desc, lvnode.signature, start, end, lvnode.index));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void log(String str)
/*     */   {
/* 573 */     System.err.println(str);
/*     */   }
/*     */ 
/*     */   private class Instantiation extends AbstractMap<LabelNode, LabelNode>
/*     */   {
/*     */     final Instantiation previous;
/*     */     public final BitSet subroutine;
/* 611 */     public final Map<LabelNode, LabelNode> rangeTable = new HashMap();
/*     */     public final LabelNode returnLabel;
/*     */ 
/*     */     Instantiation(Instantiation prev, BitSet sub)
/*     */     {
/* 619 */       this.previous = prev;
/* 620 */       this.subroutine = sub;
/* 621 */       for (Instantiation p = prev; p != null; p = p.previous) {
/* 622 */         if (p.subroutine == sub) {
/* 623 */           throw new RuntimeException("Recursive invocation of " + sub);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 629 */       if (prev != null)
/* 630 */         this.returnLabel = new LabelNode();
/*     */       else {
/* 632 */         this.returnLabel = null;
/*     */       }
/*     */ 
/* 641 */       LabelNode duplbl = null;
/* 642 */       int i = 0; for (int c = JSRInlinerAdapter.this.instructions.size(); i < c; ++i) {
/* 643 */         AbstractInsnNode insn = JSRInlinerAdapter.this.instructions.get(i);
/*     */ 
/* 645 */         if (insn.getType() == 8) {
/* 646 */           LabelNode ilbl = (LabelNode)insn;
/*     */ 
/* 648 */           if (duplbl == null)
/*     */           {
/* 651 */             duplbl = new LabelNode();
/*     */           }
/*     */ 
/* 657 */           this.rangeTable.put(ilbl, duplbl); } else {
/* 658 */           if (findOwner(i) != this) {
/*     */             continue;
/*     */           }
/*     */ 
/* 662 */           duplbl = null;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public Instantiation findOwner(int i)
/*     */     {
/* 691 */       if (!this.subroutine.get(i)) {
/* 692 */         return null;
/*     */       }
/* 694 */       if (!JSRInlinerAdapter.this.dualCitizens.get(i)) {
/* 695 */         return this;
/*     */       }
/* 697 */       Instantiation own = this;
/* 698 */       for (Instantiation p = this.previous; p != null; p = p.previous) {
/* 699 */         if (p.subroutine.get(i)) {
/* 700 */           own = p;
/*     */         }
/*     */       }
/* 703 */       return own;
/*     */     }
/*     */ 
/*     */     public LabelNode gotoLabel(LabelNode l)
/*     */     {
/* 720 */       Instantiation owner = findOwner(JSRInlinerAdapter.this.instructions.indexOf(l));
/* 721 */       return (LabelNode)owner.rangeTable.get(l);
/*     */     }
/*     */ 
/*     */     public LabelNode rangeLabel(LabelNode l)
/*     */     {
/* 737 */       return (LabelNode)this.rangeTable.get(l);
/*     */     }
/*     */ 
/*     */     public Set<Map.Entry<LabelNode, LabelNode>> entrySet()
/*     */     {
/* 744 */       return null;
/*     */     }
/*     */ 
/*     */     public LabelNode get(Object o)
/*     */     {
/* 749 */       return gotoLabel((LabelNode)o);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.asm.commons.JSRInlinerAdapter
 * JD-Core Version:    0.5.4
 */