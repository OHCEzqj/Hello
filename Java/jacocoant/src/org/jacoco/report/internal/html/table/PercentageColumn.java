/*    */ package org.jacoco.report.internal.html.table;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.jacoco.core.analysis.CounterComparator;
/*    */ import org.jacoco.core.analysis.ICounter;
/*    */ import org.jacoco.core.analysis.ICoverageNode;
/*    */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.HTMLElement;
/*    */ import org.jacoco.report.internal.html.resources.Resources;
/*    */ 
/*    */ public class PercentageColumn
/*    */   implements IColumnRenderer
/*    */ {
/*    */   private final ICoverageNode.CounterEntity entity;
/*    */   private final NumberFormat percentageFormat;
/*    */   private final Comparator<ITableItem> comparator;
/*    */ 
/*    */   public PercentageColumn(ICoverageNode.CounterEntity entity, Locale locale)
/*    */   {
/* 53 */     this.entity = entity;
/* 54 */     this.percentageFormat = NumberFormat.getPercentInstance(locale);
/* 55 */     this.comparator = new TableItemComparator(CounterComparator.MISSEDRATIO.on(entity));
/*    */   }
/*    */ 
/*    */   public boolean init(List<? extends ITableItem> items, ICoverageNode total)
/*    */   {
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   public void footer(HTMLElement td, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*    */     throws IOException
/*    */   {
/* 67 */     cell(td, total);
/*    */   }
/*    */ 
/*    */   public void item(HTMLElement td, ITableItem item, Resources resources, ReportOutputFolder base)
/*    */     throws IOException
/*    */   {
/* 73 */     cell(td, item.getNode());
/*    */   }
/*    */ 
/*    */   private void cell(HTMLElement td, ICoverageNode node) throws IOException
/*    */   {
/* 78 */     ICounter counter = node.getCounter(this.entity);
/* 79 */     int total = counter.getTotalCount();
/* 80 */     if (total == 0)
/* 81 */       td.text("n/a");
/*    */     else
/* 83 */       td.text(format(counter.getCoveredRatio()));
/*    */   }
/*    */ 
/*    */   private String format(double ratio)
/*    */   {
/* 94 */     return this.percentageFormat.format(BigDecimal.valueOf(ratio).setScale(2, RoundingMode.FLOOR));
/*    */   }
/*    */ 
/*    */   public Comparator<ITableItem> getComparator()
/*    */   {
/* 99 */     return this.comparator;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.PercentageColumn
 * JD-Core Version:    0.5.4
 */