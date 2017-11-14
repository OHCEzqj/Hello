/*     */ package org.jacoco.core.internal.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jacoco.core.analysis.CoverageNodeImpl;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.IClassCoverage;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.core.analysis.IPackageCoverage;
/*     */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*     */ 
/*     */ public class BundleCoverageImpl extends CoverageNodeImpl
/*     */   implements IBundleCoverage
/*     */ {
/*     */   private final Collection<IPackageCoverage> packages;
/*     */ 
/*     */   public BundleCoverageImpl(String name, Collection<IPackageCoverage> packages)
/*     */   {
/*  46 */     super(ICoverageNode.ElementType.BUNDLE, name);
/*  47 */     this.packages = packages;
/*  48 */     increment(packages);
/*     */   }
/*     */ 
/*     */   public BundleCoverageImpl(String name, Collection<IClassCoverage> classes, Collection<ISourceFileCoverage> sourcefiles)
/*     */   {
/*  65 */     this(name, groupByPackage(classes, sourcefiles));
/*     */   }
/*     */ 
/*     */   private static Collection<IPackageCoverage> groupByPackage(Collection<IClassCoverage> classes, Collection<ISourceFileCoverage> sourcefiles)
/*     */   {
/*  71 */     Map classesByPackage = new HashMap();
/*  72 */     for (IClassCoverage c : classes) {
/*  73 */       addByName(classesByPackage, c.getPackageName(), c);
/*     */     }
/*     */ 
/*  76 */     Map sourceFilesByPackage = new HashMap();
/*  77 */     for (ISourceFileCoverage s : sourcefiles) {
/*  78 */       addByName(sourceFilesByPackage, s.getPackageName(), s);
/*     */     }
/*     */ 
/*  81 */     Set packageNames = new HashSet();
/*  82 */     packageNames.addAll(classesByPackage.keySet());
/*  83 */     packageNames.addAll(sourceFilesByPackage.keySet());
/*     */ 
/*  85 */     Collection result = new ArrayList();
/*  86 */     for (String name : packageNames) {
/*  87 */       Collection c = (Collection)classesByPackage.get(name);
/*  88 */       if (c == null) {
/*  89 */         c = Collections.emptyList();
/*     */       }
/*  91 */       Collection s = (Collection)sourceFilesByPackage.get(name);
/*  92 */       if (s == null) {
/*  93 */         s = Collections.emptyList();
/*     */       }
/*  95 */       result.add(new PackageCoverageImpl(name, c, s));
/*     */     }
/*  97 */     return result;
/*     */   }
/*     */ 
/*     */   private static <T> void addByName(Map<String, Collection<T>> map, String name, T value)
/*     */   {
/* 102 */     Collection list = (Collection)map.get(name);
/* 103 */     if (list == null) {
/* 104 */       list = new ArrayList();
/* 105 */       map.put(name, list);
/*     */     }
/* 107 */     list.add(value);
/*     */   }
/*     */ 
/*     */   public Collection<IPackageCoverage> getPackages()
/*     */   {
/* 113 */     return this.packages;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.analysis.BundleCoverageImpl
 * JD-Core Version:    0.5.4
 */