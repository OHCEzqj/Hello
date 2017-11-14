/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.data;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public final class ExecutionData
/*     */ {
/*     */   private final long id;
/*     */   private final String name;
/*     */   private final boolean[] probes;
/*     */ 
/*     */   public ExecutionData(long id, String name, boolean[] probes)
/*     */   {
/*  43 */     this.id = id;
/*  44 */     this.name = name;
/*  45 */     this.probes = probes;
/*     */   }
/*     */ 
/*     */   public ExecutionData(long id, String name, int probeCount)
/*     */   {
/*  60 */     this.id = id;
/*  61 */     this.name = name;
/*  62 */     this.probes = new boolean[probeCount];
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  72 */     return this.id;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  81 */     return this.name;
/*     */   }
/*     */ 
/*     */   public boolean[] getProbes()
/*     */   {
/*  91 */     return this.probes;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  98 */     Arrays.fill(this.probes, false);
/*     */   }
/*     */ 
/*     */   public boolean hasHits()
/*     */   {
/* 107 */     for (boolean p : this.probes) {
/* 108 */       if (p) {
/* 109 */         return true;
/*     */       }
/*     */     }
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   public void merge(ExecutionData other)
/*     */   {
/* 131 */     merge(other, true);
/*     */   }
/*     */ 
/*     */   public void merge(ExecutionData other, boolean flag)
/*     */   {
/* 158 */     assertCompatibility(other.getId(), other.getName(), other.getProbes().length);
/*     */ 
/* 160 */     boolean[] otherData = other.getProbes();
/* 161 */     for (int i = 0; i < this.probes.length; ++i)
/* 162 */       if (otherData[i] != 0)
/* 163 */         this.probes[i] = flag;
/*     */   }
/*     */ 
/*     */   public void assertCompatibility(long id, String name, int probecount)
/*     */     throws IllegalStateException
/*     */   {
/* 184 */     if (this.id != id) {
/* 185 */       throw new IllegalStateException(String.format("Different ids (%016x and %016x).", new Object[] { Long.valueOf(this.id), Long.valueOf(id) }));
/*     */     }
/*     */ 
/* 189 */     if (!this.name.equals(name)) {
/* 190 */       throw new IllegalStateException(String.format("Different class names %s and %s for id %016x.", new Object[] { this.name, name, Long.valueOf(id) }));
/*     */     }
/*     */ 
/* 194 */     if (this.probes.length != probecount)
/* 195 */       throw new IllegalStateException(String.format("Incompatible execution data for class %s with id %016x.", new Object[] { name, Long.valueOf(id) }));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 203 */     return String.format("ExecutionData[name=%s, id=%016x]", new Object[] { this.name, Long.valueOf(this.id) });
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.data.ExecutionData
 * JD-Core Version:    0.5.4
 */