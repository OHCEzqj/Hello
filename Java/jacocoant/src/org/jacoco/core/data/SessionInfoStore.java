/*    */ package org.jacoco.core.data;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SessionInfoStore
/*    */   implements ISessionInfoVisitor
/*    */ {
/*    */   private final List<SessionInfo> infos;
/*    */ 
/*    */   public SessionInfoStore()
/*    */   {
/* 27 */     this.infos = new ArrayList();
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 35 */     return this.infos.isEmpty();
/*    */   }
/*    */ 
/*    */   public List<SessionInfo> getInfos()
/*    */   {
/* 46 */     List copy = new ArrayList(this.infos);
/* 47 */     Collections.sort(copy);
/* 48 */     return copy;
/*    */   }
/*    */ 
/*    */   public SessionInfo getMerged(String id)
/*    */   {
/* 64 */     if (this.infos.isEmpty()) {
/* 65 */       return new SessionInfo(id, 0L, 0L);
/*    */     }
/* 67 */     long start = 9223372036854775807L;
/* 68 */     long dump = -9223372036854775808L;
/* 69 */     for (SessionInfo i : this.infos) {
/* 70 */       start = Math.min(start, i.getStartTimeStamp());
/* 71 */       dump = Math.max(dump, i.getDumpTimeStamp());
/*    */     }
/* 73 */     return new SessionInfo(id, start, dump);
/*    */   }
/*    */ 
/*    */   public void accept(ISessionInfoVisitor visitor)
/*    */   {
/* 84 */     for (SessionInfo i : getInfos())
/* 85 */       visitor.visitSessionInfo(i);
/*    */   }
/*    */ 
/*    */   public void visitSessionInfo(SessionInfo info)
/*    */   {
/* 92 */     this.infos.add(info);
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.data.SessionInfoStore
 * JD-Core Version:    0.5.4
 */