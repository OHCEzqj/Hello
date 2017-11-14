/*    */ package org.jacoco.report;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.jacoco.core.data.ExecutionData;
/*    */ import org.jacoco.core.data.SessionInfo;
/*    */ 
/*    */ public class MultiReportVisitor extends MultiGroupVisitor
/*    */   implements IReportVisitor
/*    */ {
/*    */   private final List<IReportVisitor> visitors;
/*    */ 
/*    */   public MultiReportVisitor(List<IReportVisitor> visitors)
/*    */   {
/* 39 */     super(visitors);
/* 40 */     this.visitors = visitors;
/*    */   }
/*    */ 
/*    */   public void visitInfo(List<SessionInfo> sessionInfos, Collection<ExecutionData> executionData) throws IOException
/*    */   {
/* 45 */     for (IReportVisitor v : this.visitors)
/* 46 */       v.visitInfo(sessionInfos, executionData);
/*    */   }
/*    */ 
/*    */   public void visitEnd() throws IOException
/*    */   {
/* 51 */     for (IReportVisitor v : this.visitors)
/* 52 */       v.visitEnd();
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.MultiReportVisitor
 * JD-Core Version:    0.5.4
 */