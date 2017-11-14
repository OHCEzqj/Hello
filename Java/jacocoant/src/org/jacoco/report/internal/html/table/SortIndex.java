/*    */ package org.jacoco.report.internal.html.table;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ final class SortIndex<T>
/*    */ {
/*    */   private final Comparator<? super T> comparator;
/* 47 */   private final List<SortIndex<T>.Entry> list = new ArrayList();
/*    */   private int[] positions;
/*    */ 
/*    */   public SortIndex(Comparator<? super T> comparator)
/*    */   {
/* 58 */     this.comparator = comparator;
/*    */   }
/*    */ 
/*    */   public void init(List<? extends T> items)
/*    */   {
/* 68 */     this.list.clear();
/* 69 */     int idx = 0;
/* 70 */     for (Iterator i$ = items.iterator(); i$.hasNext(); ) { Object i = i$.next();
/* 71 */       Entry entry = new Entry(idx++, i);
/* 72 */       this.list.add(entry); }
/*    */ 
/* 74 */     Collections.sort(this.list);
/* 75 */     if ((this.positions == null) || (this.positions.length < items.size())) {
/* 76 */       this.positions = new int[items.size()];
/*    */     }
/* 78 */     int pos = 0;
/* 79 */     for (Entry e : this.list)
/* 80 */       this.positions[e.idx] = (pos++);
/*    */   }
/*    */ 
/*    */   public int getPosition(int idx)
/*    */   {
/* 93 */     return this.positions[idx];
/*    */   }
/*    */ 
/*    */   private class Entry
/*    */     implements Comparable<SortIndex<T>.Entry>
/*    */   {
/*    */     final int idx;
/*    */     final T item;
/*    */ 
/*    */     Entry(T idx)
/*    */     {
/* 37 */       this.idx = idx;
/* 38 */       this.item = item;
/*    */     }
/*    */ 
/*    */     public int compareTo(SortIndex<T>.Entry o) {
/* 42 */       return SortIndex.this.comparator.compare(this.item, o.item);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.SortIndex
 * JD-Core Version:    0.5.4
 */