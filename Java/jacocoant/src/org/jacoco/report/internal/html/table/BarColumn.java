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
/*     */ import org.jacoco.core.analysis.NodeComparator;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.resources.Resources;
/*     */ import org.jacoco.report.internal.xml.XMLElement;
/*     */ 
/*     */ public class BarColumn
/*     */   implements IColumnRenderer
/*     */ {
/*     */   private static final int WIDTH = 120;
/*     */   private final ICoverageNode.CounterEntity entity;
/*     */   private final NumberFormat integerFormat;
/*     */   private int max;
/*     */   private final Comparator<ITableItem> comparator;
/*     */ 
/*     */   public BarColumn(ICoverageNode.CounterEntity entity, Locale locale)
/*     */   {
/*  56 */     this.entity = entity;
/*  57 */     this.integerFormat = DecimalFormat.getIntegerInstance(locale);
/*  58 */     this.comparator = new TableItemComparator(CounterComparator.MISSEDITEMS.reverse().on(entity).second(CounterComparator.TOTALITEMS.reverse().on(entity)));
/*     */   }
/*     */ 
/*     */   public boolean init(List<? extends ITableItem> items, ICoverageNode total)
/*     */   {
/*  65 */     this.max = 0;
/*  66 */     for (ITableItem item : items) {
/*  67 */       int count = item.getNode().getCounter(this.entity).getTotalCount();
/*  68 */       if (count > this.max) {
/*  69 */         this.max = count;
/*     */       }
/*     */     }
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   public void footer(HTMLElement td, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/*  78 */     ICounter counter = total.getCounter(this.entity);
/*  79 */     td.text(this.integerFormat.format(counter.getMissedCount())).text(" of ").text(this.integerFormat.format(counter.getTotalCount()));
/*     */   }
/*     */ 
/*     */   public void item(HTMLElement td, ITableItem item, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/*  86 */     if (this.max > 0) {
/*  87 */       ICounter counter = item.getNode().getCounter(this.entity);
/*  88 */       int missed = counter.getMissedCount();
/*  89 */       bar(td, missed, "redbar.gif", resources, base);
/*  90 */       int covered = counter.getCoveredCount();
/*  91 */       bar(td, covered, "greenbar.gif", resources, base);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void bar(HTMLElement td, int count, String image, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/*  98 */     int width = count * 120 / this.max;
/*  99 */     if (width > 0)
/* 100 */       td.img(resources.getLink(base, image), width, 10, this.integerFormat.format(count));
/*     */   }
/*     */ 
/*     */   public Comparator<ITableItem> getComparator()
/*     */   {
/* 106 */     return this.comparator;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.BarColumn
 * JD-Core Version:    0.5.4
 */