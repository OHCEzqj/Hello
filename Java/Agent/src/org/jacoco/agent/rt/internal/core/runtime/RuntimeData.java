/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.runtime;
/*     */ 
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.data.ExecutionData;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.data.ExecutionDataStore;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.data.IExecutionDataVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.data.ISessionInfoVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.data.SessionInfo;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.InstrSupport;
/*     */ 
/*     */ public class RuntimeData
/*     */ {
/*     */   protected final ExecutionDataStore store;
/*     */   private long startTimeStamp;
/*     */   private String sessionId;
/*     */ 
/*     */   public RuntimeData()
/*     */   {
/*  40 */     this.store = new ExecutionDataStore();
/*  41 */     this.sessionId = "<none>";
/*  42 */     this.startTimeStamp = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public void setSessionId(String id)
/*     */   {
/*  56 */     this.sessionId = id;
/*     */   }
/*     */ 
/*     */   public String getSessionId()
/*     */   {
/*  66 */     return this.sessionId;
/*     */   }
/*     */ 
/*     */   public final void collect(IExecutionDataVisitor executionDataVisitor, ISessionInfoVisitor sessionInfoVisitor, boolean reset)
/*     */   {
/*  83 */     synchronized (this.store) {
/*  84 */       SessionInfo info = new SessionInfo(this.sessionId, this.startTimeStamp, System.currentTimeMillis());
/*     */ 
/*  86 */       sessionInfoVisitor.visitSessionInfo(info);
/*  87 */       this.store.accept(executionDataVisitor);
/*  88 */       if (reset)
/*  89 */         reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void reset()
/*     */   {
/*  98 */     synchronized (this.store) {
/*  99 */       this.store.reset();
/* 100 */       this.startTimeStamp = System.currentTimeMillis();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ExecutionData getExecutionData(Long id, String name, int probecount)
/*     */   {
/* 119 */     synchronized (this.store) {
/* 120 */       return this.store.get(id, name, probecount);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getProbes(Object[] args)
/*     */   {
/* 145 */     Long classid = (Long)args[0];
/* 146 */     String name = (String)args[1];
/* 147 */     int probecount = ((Integer)args[2]).intValue();
/* 148 */     args[0] = getExecutionData(classid, name, probecount).getProbes();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object args)
/*     */   {
/* 161 */     if (args instanceof Object[]) {
/* 162 */       getProbes((Object[])(Object[])args);
/*     */     }
/* 164 */     return super.equals(args);
/*     */   }
/*     */ 
/*     */   public static void generateArgumentArray(long classid, String classname, int probecount, MethodVisitor mv)
/*     */   {
/* 183 */     mv.visitInsn(6);
/* 184 */     mv.visitTypeInsn(189, "java/lang/Object");
/*     */ 
/* 187 */     mv.visitInsn(89);
/* 188 */     mv.visitInsn(3);
/* 189 */     mv.visitLdcInsn(Long.valueOf(classid));
/* 190 */     mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
/*     */ 
/* 192 */     mv.visitInsn(83);
/*     */ 
/* 195 */     mv.visitInsn(89);
/* 196 */     mv.visitInsn(4);
/* 197 */     mv.visitLdcInsn(classname);
/* 198 */     mv.visitInsn(83);
/*     */ 
/* 201 */     mv.visitInsn(89);
/* 202 */     mv.visitInsn(5);
/* 203 */     InstrSupport.push(mv, probecount);
/* 204 */     mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
/*     */ 
/* 206 */     mv.visitInsn(83);
/*     */   }
/*     */ 
/*     */   public static void generateAccessCall(long classid, String classname, int probecount, MethodVisitor mv)
/*     */   {
/* 229 */     generateArgumentArray(classid, classname, probecount, mv);
/*     */ 
/* 234 */     mv.visitInsn(90);
/*     */ 
/* 240 */     mv.visitMethodInsn(182, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
/*     */ 
/* 242 */     mv.visitInsn(87);
/*     */ 
/* 246 */     mv.visitInsn(3);
/* 247 */     mv.visitInsn(50);
/*     */ 
/* 251 */     mv.visitTypeInsn(192, "[Z");
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.runtime.RuntimeData
 * JD-Core Version:    0.5.4
 */