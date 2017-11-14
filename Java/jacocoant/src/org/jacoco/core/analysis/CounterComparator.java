/*    */ package org.jacoco.core.analysis;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class CounterComparator
/*    */   implements Comparator<ICounter>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3777463066252746748L;
/* 31 */   public static final CounterComparator TOTALITEMS = new CounterComparator(ICounter.CounterValue.TOTALCOUNT);
/*    */ 
/* 37 */   public static final CounterComparator COVEREDITEMS = new CounterComparator(ICounter.CounterValue.COVEREDCOUNT);
/*    */ 
/* 43 */   public static final CounterComparator MISSEDITEMS = new CounterComparator(ICounter.CounterValue.MISSEDCOUNT);
/*    */ 
/* 49 */   public static final CounterComparator COVEREDRATIO = new CounterComparator(ICounter.CounterValue.COVEREDRATIO);
/*    */ 
/* 55 */   public static final CounterComparator MISSEDRATIO = new CounterComparator(ICounter.CounterValue.MISSEDRATIO);
/*    */   private final ICounter.CounterValue value;
/*    */   private final boolean reverse;
/*    */ 
/*    */   private CounterComparator(ICounter.CounterValue value)
/*    */   {
/* 62 */     this(value, false);
/*    */   }
/*    */ 
/*    */   private CounterComparator(ICounter.CounterValue value, boolean reverse) {
/* 66 */     this.value = value;
/* 67 */     this.reverse = reverse;
/*    */   }
/*    */ 
/*    */   public int compare(ICounter c1, ICounter c2) {
/* 71 */     int cmp = Double.compare(c1.getValue(this.value), c2.getValue(this.value));
/* 72 */     return (this.reverse) ? -cmp : cmp;
/*    */   }
/*    */ 
/*    */   public CounterComparator reverse()
/*    */   {
/* 81 */     return new CounterComparator(this.value, !this.reverse);
/*    */   }
/*    */ 
/*    */   public NodeComparator on(ICoverageNode.CounterEntity entity)
/*    */   {
/* 93 */     return new NodeComparator(this, entity);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.CounterComparator
 * JD-Core Version:    0.5.4
 */