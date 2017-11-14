/*    */ package org.jacoco.core.internal.flow;
/*    */ 
/*    */ public class Instruction
/*    */ {
/*    */   private final int line;
/*    */   private int branches;
/*    */   private int coveredBranches;
/*    */   private Instruction predecessor;
/*    */ 
/*    */   public Instruction(int line)
/*    */   {
/* 35 */     this.line = line;
/* 36 */     this.branches = 0;
/* 37 */     this.coveredBranches = 0;
/*    */   }
/*    */ 
/*    */   public void addBranch()
/*    */   {
/* 44 */     this.branches += 1;
/*    */   }
/*    */ 
/*    */   public void setPredecessor(Instruction predecessor)
/*    */   {
/* 56 */     this.predecessor = predecessor;
/* 57 */     predecessor.addBranch();
/*    */   }
/*    */ 
/*    */   public void setCovered()
/*    */   {
/* 66 */     Instruction i = this;
/* 67 */     while (i != null) { if (i.coveredBranches++ != 0) return;
/* 68 */       i = i.predecessor; }
/*    */ 
/*    */   }
/*    */ 
/*    */   public int getLine()
/*    */   {
/* 78 */     return this.line;
/*    */   }
/*    */ 
/*    */   public int getBranches()
/*    */   {
/* 87 */     return this.branches;
/*    */   }
/*    */ 
/*    */   public int getCoveredBranches()
/*    */   {
/* 96 */     return this.coveredBranches;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.Instruction
 * JD-Core Version:    0.5.4
 */