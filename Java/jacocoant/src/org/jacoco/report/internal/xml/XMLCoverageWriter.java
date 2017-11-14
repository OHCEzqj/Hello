/*     */ package org.jacoco.report.internal.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.IClassCoverage;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*     */ import org.jacoco.core.analysis.ILine;
/*     */ import org.jacoco.core.analysis.IMethodCoverage;
/*     */ import org.jacoco.core.analysis.IPackageCoverage;
/*     */ import org.jacoco.core.analysis.ISourceFileCoverage;
/*     */ import org.jacoco.core.analysis.ISourceNode;
/*     */ 
/*     */ public final class XMLCoverageWriter
/*     */ {
/*     */   public static XMLElement createChild(XMLElement parent, String tagname, String name)
/*     */     throws IOException
/*     */   {
/*  48 */     XMLElement child = parent.element(tagname);
/*  49 */     child.attr("name", name);
/*  50 */     return child;
/*     */   }
/*     */ 
/*     */   public static void writeBundle(IBundleCoverage bundle, XMLElement element)
/*     */     throws IOException
/*     */   {
/*  65 */     for (IPackageCoverage p : bundle.getPackages()) {
/*  66 */       writePackage(p, element);
/*     */     }
/*  68 */     writeCounters(bundle, element);
/*     */   }
/*     */ 
/*     */   private static void writePackage(IPackageCoverage p, XMLElement parent) throws IOException
/*     */   {
/*  73 */     XMLElement element = createChild(parent, "package", p.getName());
/*  74 */     for (IClassCoverage c : p.getClasses()) {
/*  75 */       writeClass(c, element);
/*     */     }
/*  77 */     for (ISourceFileCoverage s : p.getSourceFiles()) {
/*  78 */       writeSourceFile(s, element);
/*     */     }
/*  80 */     writeCounters(p, element);
/*     */   }
/*     */ 
/*     */   private static void writeClass(IClassCoverage c, XMLElement parent) throws IOException
/*     */   {
/*  85 */     XMLElement element = createChild(parent, "class", c.getName());
/*  86 */     for (IMethodCoverage m : c.getMethods()) {
/*  87 */       writeMethod(m, element);
/*     */     }
/*  89 */     writeCounters(c, element);
/*     */   }
/*     */ 
/*     */   private static void writeMethod(IMethodCoverage m, XMLElement parent) throws IOException
/*     */   {
/*  94 */     XMLElement element = createChild(parent, "method", m.getName());
/*  95 */     element.attr("desc", m.getDesc());
/*  96 */     int line = m.getFirstLine();
/*  97 */     if (line != -1) {
/*  98 */       element.attr("line", line);
/*     */     }
/* 100 */     writeCounters(m, element);
/*     */   }
/*     */ 
/*     */   private static void writeSourceFile(ISourceFileCoverage s, XMLElement parent) throws IOException
/*     */   {
/* 105 */     XMLElement element = createChild(parent, "sourcefile", s.getName());
/*     */ 
/* 107 */     writeLines(s, element);
/* 108 */     writeCounters(s, element);
/*     */   }
/*     */ 
/*     */   public static void writeCounters(ICoverageNode node, XMLElement parent)
/*     */     throws IOException
/*     */   {
/* 123 */     for (ICoverageNode.CounterEntity counterEntity : ICoverageNode.CounterEntity.values()) {
/* 124 */       ICounter counter = node.getCounter(counterEntity);
/* 125 */       if (counter.getTotalCount() > 0) {
/* 126 */         XMLElement counterNode = parent.element("counter");
/* 127 */         counterNode.attr("type", counterEntity.name());
/* 128 */         writeCounter(counterNode, "missed", "covered", counter);
/* 129 */         counterNode.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeLines(ISourceNode source, XMLElement parent) throws IOException
/*     */   {
/* 136 */     int last = source.getLastLine();
/* 137 */     for (int nr = source.getFirstLine(); nr <= last; ++nr) {
/* 138 */       ILine line = source.getLine(nr);
/* 139 */       if (line.getStatus() != 0) {
/* 140 */         XMLElement element = parent.element("line");
/* 141 */         element.attr("nr", nr);
/* 142 */         writeCounter(element, "mi", "ci", line.getInstructionCounter());
/* 143 */         writeCounter(element, "mb", "cb", line.getBranchCounter());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeCounter(XMLElement element, String missedattr, String coveredattr, ICounter counter)
/*     */     throws IOException
/*     */   {
/* 151 */     element.attr(missedattr, counter.getMissedCount());
/* 152 */     element.attr(coveredattr, counter.getCoveredCount());
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.xml.XMLCoverageWriter
 * JD-Core Version:    0.5.4
 */