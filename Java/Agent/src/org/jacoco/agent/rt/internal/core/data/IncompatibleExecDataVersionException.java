/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.data;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class IncompatibleExecDataVersionException extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final int actualVersion;
/*    */ 
/*    */   public IncompatibleExecDataVersionException(int actualVersion)
/*    */   {
/* 32 */     super(String.format("Cannot read execution data version 0x%x. This version of JaCoCo uses execution data version 0x%x.", new Object[] { Integer.valueOf(actualVersion), Integer.valueOf(ExecutionDataWriter.FORMAT_VERSION) }));
/*    */ 
/* 36 */     this.actualVersion = actualVersion;
/*    */   }
/*    */ 
/*    */   public int getExpectedVersion()
/*    */   {
/* 46 */     return ExecutionDataWriter.FORMAT_VERSION;
/*    */   }
/*    */ 
/*    */   public int getActualVersion()
/*    */   {
/* 55 */     return this.actualVersion;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.data.IncompatibleExecDataVersionException
 * JD-Core Version:    0.5.4
 */