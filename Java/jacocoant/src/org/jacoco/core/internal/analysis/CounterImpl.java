/*     */ package org.jacoco.core.internal.analysis;
/*     */ 
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICounter.CounterValue;
/*     */ 
/*     */ public abstract class CounterImpl
/*     */   implements ICounter
/*     */ {
/*     */   private static final int SINGLETON_LIMIT = 30;
/*  25 */   private static final CounterImpl[][] SINGLETONS = new CounterImpl[31][];
/*     */   public static final CounterImpl COUNTER_0_0;
/*     */   public static final CounterImpl COUNTER_1_0;
/*     */   public static final CounterImpl COUNTER_0_1;
/*     */   protected int missed;
/*     */   protected int covered;
/*     */ 
/*     */   public static CounterImpl getInstance(int missed, int covered)
/*     */   {
/*  85 */     if ((missed <= 30) && (covered <= 30)) {
/*  86 */       return SINGLETONS[missed][covered];
/*     */     }
/*  88 */     return new Var(missed, covered);
/*     */   }
/*     */ 
/*     */   public static CounterImpl getInstance(ICounter counter)
/*     */   {
/* 100 */     return getInstance(counter.getMissedCount(), counter.getCoveredCount());
/*     */   }
/*     */ 
/*     */   protected CounterImpl(int missed, int covered)
/*     */   {
/* 118 */     this.missed = missed;
/* 119 */     this.covered = covered;
/*     */   }
/*     */ 
/*     */   public CounterImpl increment(ICounter counter)
/*     */   {
/* 132 */     return increment(counter.getMissedCount(), counter.getCoveredCount());
/*     */   }
/*     */ 
/*     */   public abstract CounterImpl increment(int paramInt1, int paramInt2);
/*     */ 
/*     */   public double getValue(ICounter.CounterValue value)
/*     */   {
/* 151 */     switch (1.$SwitchMap$org$jacoco$core$analysis$ICounter$CounterValue[value.ordinal()])
/*     */     {
/*     */     case 1:
/* 153 */       return getTotalCount();
/*     */     case 2:
/* 155 */       return getMissedCount();
/*     */     case 3:
/* 157 */       return getCoveredCount();
/*     */     case 4:
/* 159 */       return getMissedRatio();
/*     */     case 5:
/* 161 */       return getCoveredRatio();
/*     */     }
/* 163 */     throw new AssertionError(value);
/*     */   }
/*     */ 
/*     */   public int getTotalCount()
/*     */   {
/* 168 */     return this.missed + this.covered;
/*     */   }
/*     */ 
/*     */   public int getCoveredCount() {
/* 172 */     return this.covered;
/*     */   }
/*     */ 
/*     */   public int getMissedCount() {
/* 176 */     return this.missed;
/*     */   }
/*     */ 
/*     */   public double getCoveredRatio() {
/* 180 */     return this.covered / (this.missed + this.covered);
/*     */   }
/*     */ 
/*     */   public double getMissedRatio() {
/* 184 */     return this.missed / (this.missed + this.covered);
/*     */   }
/*     */ 
/*     */   public int getStatus() {
/* 188 */     int status = (this.covered > 0) ? 2 : 0;
/* 189 */     if (this.missed > 0) {
/* 190 */       status |= 1;
/*     */     }
/* 192 */     return status;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 197 */     if (obj instanceof ICounter) {
/* 198 */       ICounter that = (ICounter)obj;
/* 199 */       return (this.missed == that.getMissedCount()) && (this.covered == that.getCoveredCount());
/*     */     }
/*     */ 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 208 */     return this.missed ^ this.covered * 17;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 213 */     StringBuilder b = new StringBuilder("Counter[");
/* 214 */     b.append(getMissedCount());
/* 215 */     b.append('/').append(getCoveredCount());
/* 216 */     b.append(']');
/* 217 */     return b.toString();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     for (int i = 0; i <= 30; ++i) {
/*  29 */       SINGLETONS[i] = new CounterImpl[31];
/*  30 */       for (int j = 0; j <= 30; ++j) {
/*  31 */         SINGLETONS[i][j] = new Fix(i, j);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  37 */     COUNTER_0_0 = SINGLETONS[0][0];
/*     */ 
/*  40 */     COUNTER_1_0 = SINGLETONS[1][0];
/*     */ 
/*  43 */     COUNTER_0_1 = SINGLETONS[0][1];
/*     */   }
/*     */ 
/*     */   private static class Fix extends CounterImpl
/*     */   {
/*     */     public Fix(int missed, int covered)
/*     */     {
/*  66 */       super(missed, covered);
/*     */     }
/*     */ 
/*     */     public CounterImpl increment(int missed, int covered)
/*     */     {
/*  71 */       return getInstance(this.missed + missed, this.covered + covered);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Var extends CounterImpl
/*     */   {
/*     */     public Var(int missed, int covered)
/*     */     {
/*  50 */       super(missed, covered);
/*     */     }
/*     */ 
/*     */     public CounterImpl increment(int missed, int covered)
/*     */     {
/*  55 */       this.missed += missed;
/*  56 */       this.covered += covered;
/*  57 */       return this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.CounterImpl
 * JD-Core Version:    0.5.4
 */