/*     */ package org.jacoco.core.internal.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.jacoco.core.analysis.IClassCoverage;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.core.analysis.IMethodCoverage;
/*     */ 
/*     */ public class ClassCoverageImpl extends SourceNodeImpl
/*     */   implements IClassCoverage
/*     */ {
/*     */   private final long id;
/*     */   private final boolean noMatch;
/*     */   private final Collection<IMethodCoverage> methods;
/*     */   private String signature;
/*     */   private String superName;
/*     */   private String[] interfaces;
/*     */   private String sourceFileName;
/*     */ 
/*     */   public ClassCoverageImpl(String name, long id, boolean noMatch)
/*     */   {
/*  46 */     super(ICoverageNode.ElementType.CLASS, name);
/*  47 */     this.id = id;
/*  48 */     this.noMatch = noMatch;
/*  49 */     this.methods = new ArrayList();
/*  50 */     this.classCounter = CounterImpl.COUNTER_1_0;
/*     */   }
/*     */ 
/*     */   public void addMethod(IMethodCoverage method)
/*     */   {
/*  60 */     this.methods.add(method);
/*  61 */     increment(method);
/*     */ 
/*  64 */     if (this.methodCounter.getCoveredCount() > 0)
/*  65 */       this.classCounter = CounterImpl.COUNTER_0_1;
/*     */   }
/*     */ 
/*     */   public void setSignature(String signature)
/*     */   {
/*  76 */     this.signature = signature;
/*     */   }
/*     */ 
/*     */   public void setSuperName(String superName)
/*     */   {
/*  87 */     this.superName = superName;
/*     */   }
/*     */ 
/*     */   public void setInterfaces(String[] interfaces)
/*     */   {
/*  97 */     this.interfaces = interfaces;
/*     */   }
/*     */ 
/*     */   public void setSourceFileName(String sourceFileName)
/*     */   {
/* 107 */     this.sourceFileName = sourceFileName;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 113 */     return this.id;
/*     */   }
/*     */ 
/*     */   public boolean isNoMatch() {
/* 117 */     return this.noMatch;
/*     */   }
/*     */ 
/*     */   public String getSignature() {
/* 121 */     return this.signature;
/*     */   }
/*     */ 
/*     */   public String getSuperName() {
/* 125 */     return this.superName;
/*     */   }
/*     */ 
/*     */   public String[] getInterfaceNames() {
/* 129 */     return this.interfaces;
/*     */   }
/*     */ 
/*     */   public String getPackageName() {
/* 133 */     int pos = getName().lastIndexOf('/');
/* 134 */     return (pos == -1) ? "" : getName().substring(0, pos);
/*     */   }
/*     */ 
/*     */   public String getSourceFileName() {
/* 138 */     return this.sourceFileName;
/*     */   }
/*     */ 
/*     */   public Collection<IMethodCoverage> getMethods() {
/* 142 */     return this.methods;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.ClassCoverageImpl
 * JD-Core Version:    0.5.4
 */