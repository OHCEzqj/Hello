/*     */ package org.jacoco.core.data;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public final class ExecutionDataStore
/*     */   implements IExecutionDataVisitor
/*     */ {
/*     */   private final Map<Long, ExecutionData> entries;
/*     */   private final Set<String> names;
/*     */ 
/*     */   public ExecutionDataStore()
/*     */   {
/*  31 */     this.entries = new HashMap();
/*     */ 
/*  33 */     this.names = new HashSet();
/*     */   }
/*     */ 
/*     */   public void put(ExecutionData data)
/*     */     throws IllegalStateException
/*     */   {
/*  48 */     Long id = Long.valueOf(data.getId());
/*  49 */     ExecutionData entry = (ExecutionData)this.entries.get(id);
/*  50 */     if (entry == null) {
/*  51 */       this.entries.put(id, data);
/*  52 */       this.names.add(data.getName());
/*     */     } else {
/*  54 */       entry.merge(data);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void subtract(ExecutionData data)
/*     */     throws IllegalStateException
/*     */   {
/*  72 */     Long id = Long.valueOf(data.getId());
/*  73 */     ExecutionData entry = (ExecutionData)this.entries.get(id);
/*  74 */     if (entry != null)
/*  75 */       entry.merge(data, false);
/*     */   }
/*     */ 
/*     */   public void subtract(ExecutionDataStore store)
/*     */   {
/*  87 */     for (ExecutionData data : store.getContents())
/*  88 */       subtract(data);
/*     */   }
/*     */ 
/*     */   public ExecutionData get(long id)
/*     */   {
/* 101 */     return (ExecutionData)this.entries.get(Long.valueOf(id));
/*     */   }
/*     */ 
/*     */   public boolean contains(String name)
/*     */   {
/* 114 */     return this.names.contains(name);
/*     */   }
/*     */ 
/*     */   public ExecutionData get(Long id, String name, int probecount)
/*     */   {
/* 131 */     ExecutionData entry = (ExecutionData)this.entries.get(id);
/* 132 */     if (entry == null) {
/* 133 */       entry = new ExecutionData(id.longValue(), name, probecount);
/* 134 */       this.entries.put(id, entry);
/* 135 */       this.names.add(name);
/*     */     } else {
/* 137 */       entry.assertCompatibility(id.longValue(), name, probecount);
/*     */     }
/* 139 */     return entry;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 147 */     for (ExecutionData executionData : this.entries.values())
/* 148 */       executionData.reset();
/*     */   }
/*     */ 
/*     */   public Collection<ExecutionData> getContents()
/*     */   {
/* 158 */     return new ArrayList(this.entries.values());
/*     */   }
/*     */ 
/*     */   public void accept(IExecutionDataVisitor visitor)
/*     */   {
/* 168 */     for (ExecutionData data : getContents())
/* 169 */       visitor.visitClassExecution(data);
/*     */   }
/*     */ 
/*     */   public void visitClassExecution(ExecutionData data)
/*     */   {
/* 176 */     put(data);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.data.ExecutionDataStore
 * JD-Core Version:    0.5.4
 */