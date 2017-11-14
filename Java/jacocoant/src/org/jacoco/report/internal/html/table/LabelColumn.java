/*    */ package org.jacoco.report.internal.html.table;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.HTMLElement;
/*    */ import org.jacoco.report.internal.html.resources.Resources;
/*    */ 
/*    */ public class LabelColumn
/*    */   implements IColumnRenderer
/*    */ {
/* 29 */   private static final Comparator<ITableItem> COMPARATOR = new Comparator() {
/*    */     public int compare(ITableItem i1, ITableItem i2) {
/* 31 */       return i1.getLinkLabel().compareToIgnoreCase(i2.getLinkLabel());
/*    */     }
/* 29 */   };
/*    */ 
/*    */   public boolean init(List<? extends ITableItem> items, ICoverageNode total)
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public void footer(HTMLElement td, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*    */     throws IOException
/*    */   {
/* 43 */     td.text("Total");
/*    */   }
/*    */ 
/*    */   public void item(HTMLElement td, ITableItem item, Resources resources, ReportOutputFolder base)
/*    */     throws IOException
/*    */   {
/* 49 */     td.a(item, base);
/*    */   }
/*    */ 
/*    */   public Comparator<ITableItem> getComparator() {
/* 53 */     return COMPARATOR;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.LabelColumn
 * JD-Core Version:    0.5.4
 */