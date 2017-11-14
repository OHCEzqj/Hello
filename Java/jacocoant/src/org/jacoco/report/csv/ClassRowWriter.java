/*    */ package org.jacoco.report.csv;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IClassCoverage;
/*    */ import org.jacoco.core.analysis.ICounter;
/*    */ import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
/*    */ import org.jacoco.report.ILanguageNames;
/*    */ 
/*    */ class ClassRowWriter
/*    */ {
/* 27 */   private static final ICoverageNode.CounterEntity[] COUNTERS = { ICoverageNode.CounterEntity.INSTRUCTION, ICoverageNode.CounterEntity.BRANCH, ICoverageNode.CounterEntity.LINE, ICoverageNode.CounterEntity.COMPLEXITY, ICoverageNode.CounterEntity.METHOD };
/*    */   private final DelimitedWriter writer;
/*    */   private final ILanguageNames languageNames;
/*    */ 
/*    */   public ClassRowWriter(DelimitedWriter writer, ILanguageNames languageNames)
/*    */     throws IOException
/*    */   {
/* 48 */     this.writer = writer;
/* 49 */     this.languageNames = languageNames;
/* 50 */     writeHeader();
/*    */   }
/*    */ 
/*    */   private void writeHeader() throws IOException {
/* 54 */     this.writer.write(new String[] { "GROUP", "PACKAGE", "CLASS" });
/* 55 */     for (ICoverageNode.CounterEntity entity : COUNTERS) {
/* 56 */       this.writer.write(entity.name() + "_MISSED");
/* 57 */       this.writer.write(entity.name() + "_COVERED");
/*    */     }
/* 59 */     this.writer.nextLine();
/*    */   }
/*    */ 
/*    */   public void writeRow(String groupName, String packageName, IClassCoverage node)
/*    */     throws IOException
/*    */   {
/* 76 */     this.writer.write(groupName);
/* 77 */     this.writer.write(this.languageNames.getPackageName(packageName));
/* 78 */     String className = this.languageNames.getClassName(node.getName(), node.getSignature(), node.getSuperName(), node.getInterfaceNames());
/*    */ 
/* 81 */     this.writer.write(className);
/*    */ 
/* 83 */     for (ICoverageNode.CounterEntity entity : COUNTERS) {
/* 84 */       ICounter counter = node.getCounter(entity);
/* 85 */       this.writer.write(counter.getMissedCount());
/* 86 */       this.writer.write(counter.getCoveredCount());
/*    */     }
/*    */ 
/* 89 */     this.writer.nextLine();
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.csv.ClassRowWriter
 * JD-Core Version:    0.5.4
 */