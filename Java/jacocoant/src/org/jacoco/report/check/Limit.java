/*     */ package org.jacoco.report.check;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICounter.CounterValue;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*     */ 
/*     */ public class Limit
/*     */ {
/*     */   private static final Map<ICounter.CounterValue, String> VALUE_NAMES;
/*     */   private static final Map<ICoverageNode.CounterEntity, String> ENTITY_NAMES;
/*     */   private ICoverageNode.CounterEntity entity;
/*     */   private ICounter.CounterValue value;
/*     */   private BigDecimal minimum;
/*     */   private BigDecimal maximum;
/*     */ 
/*     */   public Limit()
/*     */   {
/*  69 */     this.entity = ICoverageNode.CounterEntity.INSTRUCTION;
/*  70 */     this.value = ICounter.CounterValue.COVEREDRATIO;
/*     */   }
/*     */ 
/*     */   public ICoverageNode.CounterEntity getEntity()
/*     */   {
/*  77 */     return this.entity;
/*     */   }
/*     */ 
/*     */   public void setCounter(String entity)
/*     */   {
/*  88 */     this.entity = ICoverageNode.CounterEntity.valueOf(entity);
/*     */   }
/*     */ 
/*     */   public ICounter.CounterValue getValue()
/*     */   {
/*  95 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 106 */     this.value = ICounter.CounterValue.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String getMinimum()
/*     */   {
/* 114 */     return (this.minimum == null) ? null : this.minimum.toPlainString();
/*     */   }
/*     */ 
/*     */   public void setMinimum(String minimum)
/*     */   {
/* 127 */     this.minimum = ((minimum == null) ? null : new BigDecimal(minimum));
/*     */   }
/*     */ 
/*     */   public String getMaximum()
/*     */   {
/* 135 */     return (this.maximum == null) ? null : this.maximum.toPlainString();
/*     */   }
/*     */ 
/*     */   public void setMaximum(String maximum)
/*     */   {
/* 148 */     this.maximum = ((maximum == null) ? null : new BigDecimal(maximum));
/*     */   }
/*     */ 
/*     */   String check(ICoverageNode node) {
/* 152 */     double d = node.getCounter(this.entity).getValue(this.value);
/* 153 */     if (Double.isNaN(d)) {
/* 154 */       return null;
/*     */     }
/* 156 */     BigDecimal bd = BigDecimal.valueOf(d);
/* 157 */     if ((this.minimum != null) && (this.minimum.compareTo(bd) > 0)) {
/* 158 */       return message("minimum", bd, this.minimum, RoundingMode.FLOOR);
/*     */     }
/* 160 */     if ((this.maximum != null) && (this.maximum.compareTo(bd) < 0)) {
/* 161 */       return message("maximum", bd, this.maximum, RoundingMode.CEILING);
/*     */     }
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   private String message(String minmax, BigDecimal v, BigDecimal ref, RoundingMode mode)
/*     */   {
/* 168 */     BigDecimal rounded = v.setScale(ref.scale(), mode);
/* 169 */     return String.format("%s %s is %s, but expected %s is %s", new Object[] { ENTITY_NAMES.get(this.entity), VALUE_NAMES.get(this.value), rounded.toPlainString(), minmax, ref.toPlainString() });
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  33 */     Map values = new HashMap();
/*  34 */     values.put(ICounter.CounterValue.TOTALCOUNT, "total count");
/*  35 */     values.put(ICounter.CounterValue.MISSEDCOUNT, "missed count");
/*  36 */     values.put(ICounter.CounterValue.COVEREDCOUNT, "covered count");
/*  37 */     values.put(ICounter.CounterValue.MISSEDRATIO, "missed ratio");
/*  38 */     values.put(ICounter.CounterValue.COVEREDRATIO, "covered ratio");
/*  39 */     VALUE_NAMES = Collections.unmodifiableMap(values);
/*     */ 
/*  41 */     Map entities = new HashMap();
/*  42 */     entities.put(ICoverageNode.CounterEntity.INSTRUCTION, "instructions");
/*  43 */     entities.put(ICoverageNode.CounterEntity.BRANCH, "branches");
/*  44 */     entities.put(ICoverageNode.CounterEntity.COMPLEXITY, "complexity");
/*  45 */     entities.put(ICoverageNode.CounterEntity.LINE, "lines");
/*  46 */     entities.put(ICoverageNode.CounterEntity.METHOD, "methods");
/*  47 */     entities.put(ICoverageNode.CounterEntity.CLASS, "classes");
/*  48 */     ENTITY_NAMES = Collections.unmodifiableMap(entities);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.check.Limit
 * JD-Core Version:    0.5.4
 */