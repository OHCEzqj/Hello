/*     */ package org.jacoco.report.internal.html.table;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.html.HTMLElement;
/*     */ import org.jacoco.report.internal.html.resources.Resources;
/*     */ import org.jacoco.report.internal.html.resources.Styles;
/*     */ 
/*     */ public class Table
/*     */ {
/*     */   private final List<Column> columns;
/*     */   private Comparator<ITableItem> defaultComparator;
/*     */ 
/*     */   public Table()
/*     */   {
/*  39 */     this.columns = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void add(String header, String style, IColumnRenderer renderer, boolean defaultSorting)
/*     */   {
/*  59 */     this.columns.add(new Column(this.columns.size(), header, style, renderer, defaultSorting));
/*     */ 
/*  61 */     if (defaultSorting) {
/*  62 */       if (this.defaultComparator != null) {
/*  63 */         throw new IllegalStateException("Default sorting only allowed for one column.");
/*     */       }
/*     */ 
/*  66 */       this.defaultComparator = renderer.getComparator();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void render(HTMLElement parent, List<? extends ITableItem> items, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/*  91 */     List sortedItems = sort(items);
/*  92 */     HTMLElement table = parent.table("coverage");
/*  93 */     table.attr("id", "coveragetable");
/*  94 */     header(table, sortedItems, total);
/*  95 */     footer(table, total, resources, base);
/*  96 */     body(table, sortedItems, resources, base);
/*     */   }
/*     */ 
/*     */   private void header(HTMLElement table, List<? extends ITableItem> items, ICoverageNode total)
/*     */     throws IOException
/*     */   {
/* 102 */     HTMLElement tr = table.thead().tr();
/* 103 */     for (Column c : this.columns)
/* 104 */       c.init(tr, items, total);
/*     */   }
/*     */ 
/*     */   private void footer(HTMLElement table, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 111 */     HTMLElement tr = table.tfoot().tr();
/* 112 */     for (Column c : this.columns)
/* 113 */       c.footer(tr, total, resources, base);
/*     */   }
/*     */ 
/*     */   private void body(HTMLElement table, List<? extends ITableItem> items, Resources resources, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 120 */     HTMLElement tbody = table.tbody();
/* 121 */     int idx = 0;
/* 122 */     for (ITableItem item : items) {
/* 123 */       HTMLElement tr = tbody.tr();
/* 124 */       for (Column c : this.columns) {
/* 125 */         c.body(tr, idx, item, resources, base);
/*     */       }
/* 127 */       ++idx;
/*     */     }
/*     */   }
/*     */ 
/*     */   private List<? extends ITableItem> sort(List<? extends ITableItem> items)
/*     */   {
/* 133 */     if (this.defaultComparator != null) {
/* 134 */       List result = new ArrayList(items);
/* 135 */       Collections.sort(result, this.defaultComparator);
/* 136 */       return result;
/*     */     }
/* 138 */     return items;
/*     */   }
/*     */ 
/*     */   private static class Column
/*     */   {
/*     */     private final char idprefix;
/*     */     private final String header;
/*     */     private final IColumnRenderer renderer;
/*     */     private final SortIndex<ITableItem> index;
/*     */     private final String style;
/*     */     private final String headerStyle;
/*     */     private boolean visible;
/*     */ 
/*     */     Column(int idx, String header, String style, IColumnRenderer renderer, boolean defaultSorting)
/*     */     {
/* 153 */       this.idprefix = (char)(97 + idx);
/* 154 */       this.header = header;
/* 155 */       this.renderer = renderer;
/* 156 */       this.index = new SortIndex(renderer.getComparator());
/* 157 */       this.style = style;
/* 158 */       this.headerStyle = Styles.combine(new String[] { (defaultSorting) ? "down" : null, "sortable", style });
/*     */     }
/*     */ 
/*     */     void init(HTMLElement tr, List<? extends ITableItem> items, ICoverageNode total)
/*     */       throws IOException
/*     */     {
/* 164 */       this.visible = this.renderer.init(items, total);
/* 165 */       if (this.visible) {
/* 166 */         this.index.init(items);
/* 167 */         HTMLElement td = tr.td(this.headerStyle);
/* 168 */         td.attr("id", String.valueOf(this.idprefix));
/* 169 */         td.attr("onclick", "toggleSort(this)");
/* 170 */         td.text(this.header);
/*     */       }
/*     */     }
/*     */ 
/*     */     void footer(HTMLElement tr, ICoverageNode total, Resources resources, ReportOutputFolder base)
/*     */       throws IOException
/*     */     {
/* 177 */       if (this.visible)
/* 178 */         this.renderer.footer(tr.td(this.style), total, resources, base);
/*     */     }
/*     */ 
/*     */     void body(HTMLElement tr, int idx, ITableItem item, Resources resources, ReportOutputFolder base)
/*     */       throws IOException
/*     */     {
/* 185 */       if (this.visible) {
/* 186 */         HTMLElement td = tr.td(this.style);
/* 187 */         td.attr("id", this.idprefix + String.valueOf(this.index.getPosition(idx)));
/* 188 */         this.renderer.item(td, item, resources, base);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.Table
 * JD-Core Version:    0.5.4
 */