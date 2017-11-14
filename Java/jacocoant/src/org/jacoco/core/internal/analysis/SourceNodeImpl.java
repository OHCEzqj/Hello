/*     */ package org.jacoco.core.internal.analysis;
/*     */ 
/*     */ import org.jacoco.core.analysis.CoverageNodeImpl;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.core.analysis.ILine;
/*     */ import org.jacoco.core.analysis.ISourceNode;
/*     */ 
/*     */ public class SourceNodeImpl extends CoverageNodeImpl
/*     */   implements ISourceNode
/*     */ {
/*     */   private LineImpl[] lines;
/*     */   private int offset;
/*     */ 
/*     */   public SourceNodeImpl(ICoverageNode.ElementType elementType, String name)
/*     */   {
/*  38 */     super(elementType, name);
/*  39 */     this.lines = null;
/*  40 */     this.offset = -1;
/*     */   }
/*     */ 
/*     */   public void ensureCapacity(int first, int last)
/*     */   {
/*  54 */     if ((first == -1) || (last == -1)) {
/*  55 */       return;
/*     */     }
/*  57 */     if (this.lines == null) {
/*  58 */       this.offset = first;
/*  59 */       this.lines = new LineImpl[last - first + 1];
/*     */     } else {
/*  61 */       int newFirst = Math.min(getFirstLine(), first);
/*  62 */       int newLast = Math.max(getLastLine(), last);
/*  63 */       int newLength = newLast - newFirst + 1;
/*  64 */       if (newLength > this.lines.length) {
/*  65 */         LineImpl[] newLines = new LineImpl[newLength];
/*  66 */         System.arraycopy(this.lines, 0, newLines, this.offset - newFirst, this.lines.length);
/*     */ 
/*  68 */         this.offset = newFirst;
/*  69 */         this.lines = newLines;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void increment(ISourceNode child)
/*     */   {
/*  83 */     this.instructionCounter = this.instructionCounter.increment(child.getInstructionCounter());
/*     */ 
/*  85 */     this.branchCounter = this.branchCounter.increment(child.getBranchCounter());
/*  86 */     this.complexityCounter = this.complexityCounter.increment(child.getComplexityCounter());
/*     */ 
/*  88 */     this.methodCounter = this.methodCounter.increment(child.getMethodCounter());
/*  89 */     this.classCounter = this.classCounter.increment(child.getClassCounter());
/*  90 */     int firstLine = child.getFirstLine();
/*  91 */     if (firstLine != -1) {
/*  92 */       int lastLine = child.getLastLine();
/*  93 */       ensureCapacity(firstLine, lastLine);
/*  94 */       for (int i = firstLine; i <= lastLine; ++i) {
/*  95 */         ILine line = child.getLine(i);
/*  96 */         incrementLine(line.getInstructionCounter(), line.getBranchCounter(), i);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void increment(ICounter instructions, ICounter branches, int line)
/*     */   {
/* 116 */     if (line != -1) {
/* 117 */       incrementLine(instructions, branches, line);
/*     */     }
/* 119 */     this.instructionCounter = this.instructionCounter.increment(instructions);
/* 120 */     this.branchCounter = this.branchCounter.increment(branches);
/*     */   }
/*     */ 
/*     */   private void incrementLine(ICounter instructions, ICounter branches, int line)
/*     */   {
/* 125 */     ensureCapacity(line, line);
/* 126 */     LineImpl l = getLine(line);
/* 127 */     int oldTotal = l.getInstructionCounter().getTotalCount();
/* 128 */     int oldCovered = l.getInstructionCounter().getCoveredCount();
/* 129 */     this.lines[(line - this.offset)] = l.increment(instructions, branches);
/*     */ 
/* 132 */     if (instructions.getTotalCount() > 0)
/* 133 */       if (instructions.getCoveredCount() == 0) {
/* 134 */         if (oldTotal == 0) {
/* 135 */           this.lineCounter = this.lineCounter.increment(CounterImpl.COUNTER_1_0);
/*     */         }
/*     */ 
/*     */       }
/* 139 */       else if (oldTotal == 0) {
/* 140 */         this.lineCounter = this.lineCounter.increment(CounterImpl.COUNTER_0_1);
/*     */       }
/* 143 */       else if (oldCovered == 0)
/* 144 */         this.lineCounter = this.lineCounter.increment(-1, 1);
/*     */   }
/*     */ 
/*     */   public int getFirstLine()
/*     */   {
/* 154 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public int getLastLine() {
/* 158 */     return (this.lines == null) ? -1 : this.offset + this.lines.length - 1;
/*     */   }
/*     */ 
/*     */   public LineImpl getLine(int nr) {
/* 162 */     if ((this.lines == null) || (nr < getFirstLine()) || (nr > getLastLine())) {
/* 163 */       return LineImpl.EMPTY;
/*     */     }
/* 165 */     LineImpl line = this.lines[(nr - this.offset)];
/* 166 */     return (line == null) ? LineImpl.EMPTY : line;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.SourceNodeImpl
 * JD-Core Version:    0.5.4
 */