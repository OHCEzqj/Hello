/*    */ package org.jacoco.core.analysis;
/*    */ 
/*    */ public abstract interface ICounter
/*    */ {
/*    */   public static final int EMPTY = 0;
/*    */   public static final int NOT_COVERED = 1;
/*    */   public static final int FULLY_COVERED = 2;
/*    */   public static final int PARTLY_COVERED = 3;
/*    */ 
/*    */   public abstract double getValue(CounterValue paramCounterValue);
/*    */ 
/*    */   public abstract int getTotalCount();
/*    */ 
/*    */   public abstract int getCoveredCount();
/*    */ 
/*    */   public abstract int getMissedCount();
/*    */ 
/*    */   public abstract double getCoveredRatio();
/*    */ 
/*    */   public abstract double getMissedRatio();
/*    */ 
/*    */   public abstract int getStatus();
/*    */ 
/*    */   public static enum CounterValue
/*    */   {
/* 26 */     TOTALCOUNT, 
/*    */ 
/* 29 */     MISSEDCOUNT, 
/*    */ 
/* 32 */     COVEREDCOUNT, 
/*    */ 
/* 35 */     MISSEDRATIO, 
/*    */ 
/* 38 */     COVEREDRATIO;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.ICounter
 * JD-Core Version:    0.5.4
 */