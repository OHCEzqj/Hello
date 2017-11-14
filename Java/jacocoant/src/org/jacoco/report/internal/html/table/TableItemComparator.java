/*    */ package org.jacoco.report.internal.html.table;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ 
/*    */ class TableItemComparator
/*    */   implements Comparator<ITableItem>
/*    */ {
/*    */   private final Comparator<ICoverageNode> comparator;
/*    */ 
/*    */   TableItemComparator(Comparator<ICoverageNode> comparator)
/*    */   {
/* 26 */     this.comparator = comparator;
/*    */   }
/*    */ 
/*    */   public int compare(ITableItem i1, ITableItem i2) {
/* 30 */     return this.comparator.compare(i1.getNode(), i2.getNode());
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.TableItemComparator
 * JD-Core Version:    0.5.4
 */