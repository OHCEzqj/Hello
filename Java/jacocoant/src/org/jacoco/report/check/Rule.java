/*     */ package org.jacoco.report.check;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.core.runtime.WildcardMatcher;
/*     */ 
/*     */ public final class Rule
/*     */ {
/*     */   private ICoverageNode.ElementType element;
/*     */   private String includes;
/*     */   private String excludes;
/*     */   private List<Limit> limits;
/*     */   private WildcardMatcher includesMatcher;
/*     */   private WildcardMatcher excludesMatcher;
/*     */ 
/*     */   public Rule()
/*     */   {
/*  38 */     this.element = ICoverageNode.ElementType.BUNDLE;
/*  39 */     this.limits = new ArrayList();
/*  40 */     setIncludes("*");
/*  41 */     setExcludes("");
/*     */   }
/*     */ 
/*     */   public ICoverageNode.ElementType getElement()
/*     */   {
/*  48 */     return this.element;
/*     */   }
/*     */ 
/*     */   public void setElement(ICoverageNode.ElementType elementType)
/*     */   {
/*  56 */     this.element = elementType;
/*     */   }
/*     */ 
/*     */   public String getIncludes()
/*     */   {
/*  63 */     return this.includes;
/*     */   }
/*     */ 
/*     */   public void setIncludes(String includes)
/*     */   {
/*  71 */     this.includes = includes;
/*  72 */     this.includesMatcher = new WildcardMatcher(includes);
/*     */   }
/*     */ 
/*     */   public String getExcludes()
/*     */   {
/*  79 */     return this.excludes;
/*     */   }
/*     */ 
/*     */   public void setExcludes(String excludes)
/*     */   {
/*  88 */     this.excludes = excludes;
/*  89 */     this.excludesMatcher = new WildcardMatcher(excludes);
/*     */   }
/*     */ 
/*     */   public List<Limit> getLimits()
/*     */   {
/*  96 */     return this.limits;
/*     */   }
/*     */ 
/*     */   public void setLimits(List<Limit> limits)
/*     */   {
/* 104 */     this.limits = limits;
/*     */   }
/*     */ 
/*     */   public Limit createLimit()
/*     */   {
/* 113 */     Limit limit = new Limit();
/* 114 */     this.limits.add(limit);
/* 115 */     return limit;
/*     */   }
/*     */ 
/*     */   boolean matches(String name) {
/* 119 */     return (this.includesMatcher.matches(name)) && (!this.excludesMatcher.matches(name));
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.check.Rule
 * JD-Core Version:    0.5.4
 */