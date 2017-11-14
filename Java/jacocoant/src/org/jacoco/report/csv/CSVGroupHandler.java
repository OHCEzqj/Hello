/*    */ package org.jacoco.report.csv;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jacoco.core.analysis.IBundleCoverage;
/*    */ import org.jacoco.core.analysis.IClassCoverage;
/*    */ import org.jacoco.core.analysis.IPackageCoverage;
/*    */ import org.jacoco.report.IReportGroupVisitor;
/*    */ import org.jacoco.report.ISourceFileLocator;
/*    */ 
/*    */ class CSVGroupHandler
/*    */   implements IReportGroupVisitor
/*    */ {
/*    */   private final ClassRowWriter writer;
/*    */   private final String groupName;
/*    */ 
/*    */   public CSVGroupHandler(ClassRowWriter writer)
/*    */   {
/* 32 */     this(writer, null);
/*    */   }
/*    */ 
/*    */   private CSVGroupHandler(ClassRowWriter writer, String groupName) {
/* 36 */     this.writer = writer;
/* 37 */     this.groupName = groupName;
/*    */   }
/*    */ 
/*    */   public void visitBundle(IBundleCoverage bundle, ISourceFileLocator locator) throws IOException
/*    */   {
/* 42 */     String name = appendName(bundle.getName());
/* 43 */     for (IPackageCoverage p : bundle.getPackages()) {
/* 44 */       packageName = p.getName();
/* 45 */       for (IClassCoverage c : p.getClasses())
/* 46 */         this.writer.writeRow(name, packageName, c);
/*    */     }
/*    */     String packageName;
/*    */   }
/*    */ 
/*    */   public IReportGroupVisitor visitGroup(String name) throws IOException {
/* 52 */     return new CSVGroupHandler(this.writer, appendName(name));
/*    */   }
/*    */ 
/*    */   private String appendName(String name) {
/* 56 */     return this.groupName + "/" + name;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.csv.CSVGroupHandler
 * JD-Core Version:    0.5.4
 */