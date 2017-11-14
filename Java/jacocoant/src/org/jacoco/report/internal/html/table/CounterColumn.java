/*     */ package org.jacoco.report.internal.html.table;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.jacoco.core.analysis.CounterComparator;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.resources.Resources;
/*     */ 
/*     */ public abstract class CounterColumn
/*     */   implements IColumnRenderer
/*     */ {
/*     */   private final ICoverageNode.CounterEntity entity;
/*     */   private final NumberFormat integerFormat;
/*     */   private final Comparator<ITableItem> comparator;
/*     */ 
/*     */   public static CounterColumn newTotal(ICoverageNode.CounterEntity entity, Locale locale)
/*     */   {
/*  47 */     return new CounterColumn(entity, locale, CounterComparator.TOTALITEMS.reverse().on(entity))
/*     */     {
/*     */       protected int getValue(ICounter counter)
/*     */       {
/*  51 */         return counter.getTotalCount();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CounterColumn newMissed(ICoverageNode.CounterEntity entity, Locale locale)
/*     */   {
/*  67 */     return new CounterColumn(entity, locale, CounterComparator.MISSEDITEMS.reverse().on(entity))
/*     */     {
/*     */       protected int getValue(ICounter counter)
/*     */       {
/*  71 */         return counter.getMissedCount();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static CounterColumn newCovered(ICoverageNode.CounterEntity entity, Locale locale)
/*     */   {
/*  87 */     return new CounterColumn(entity, locale, CounterComparator.COVEREDITEMS.reverse().on(entity))
/*     */     {
/*     */       protected int getValue(ICounter counter)
/*     */       {
/*  91 */         return counter.getCoveredCount();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected CounterColumn(ICoverageNode.CounterEntity entity, Locale locale, Comparator<ICoverageNode> comparator)
/*     */   {
/* 115 */     this.entity = entity;
/* 116 */     this.integerFormat = DecimalFormat.getIntegerInstance(locale);
/* 117 */     this.comparator = new TableItemComparator(comparator);
/*     */   }
/*     */ 
/*     */   public boolean init(List<? extends ITableItem> items, ICoverageNode total)
/*     */   {
/* 122 */     for (ITableItem i : items) {
/* 123 */       if (i.getNode().getCounter(this.entity).getTotalCount() > 0) {
/* 124 */         return true;
/*     */       }
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   public void footer(HTMLElement td, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 133 */     cell(td, total);
/*     */   }
/*     */ 
/*     */   public void item(HTMLElement td, ITableItem item, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 139 */     cell(td, item.getNode());
/*     */   }
/*     */ 
/*     */   private void cell(HTMLElement td, ICoverageNode node) throws IOException
/*     */   {
/* 144 */     int value = getValue(node.getCounter(this.entity));
/* 145 */     td.text(this.integerFormat.format(value));
/*     */   }
/*     */ 
/*     */   public Comparator<ITableItem> getComparator() {
/* 149 */     return this.comparator;
/*     */   }
/*     */ 
/*     */   protected abstract int getValue(ICounter paramICounter);
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.CounterColumn
 * JD-Core Version:    0.5.4
 */