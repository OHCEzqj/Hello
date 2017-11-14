/*    */ package org.jacoco.report.internal.html.index;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.jacoco.report.internal.ReportOutputFolder;
/*    */ import org.jacoco.report.internal.html.ILinkable;
/*    */ 
/*    */ public class ElementIndex
/*    */   implements IIndexUpdate
/*    */ {
/*    */   private final ReportOutputFolder baseFolder;
/* 28 */   private final Map<Long, String> allClasses = new HashMap();
/*    */ 
/*    */   public ElementIndex(ReportOutputFolder baseFolder)
/*    */   {
/* 37 */     this.baseFolder = baseFolder;
/*    */   }
/*    */ 
/*    */   public String getLinkToClass(long classid)
/*    */   {
/* 49 */     return (String)this.allClasses.get(Long.valueOf(classid));
/*    */   }
/*    */ 
/*    */   public void addClass(ILinkable link, long classid)
/*    */   {
/* 55 */     this.allClasses.put(Long.valueOf(classid), link.getLink(this.baseFolder));
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.index.ElementIndex
 * JD-Core Version:    0.5.4
 */