/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core.runtime;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public abstract class AbstractRuntime
/*    */   implements IRuntime
/*    */ {
/*    */   protected RuntimeData data;
/* 31 */   private static final Random RANDOM = new Random();
/*    */ 
/*    */   public void startup(RuntimeData data)
/*    */     throws Exception
/*    */   {
/* 28 */     this.data = data;
/*    */   }
/*    */ 
/*    */   public static String createRandomId()
/*    */   {
/* 39 */     return Integer.toHexString(RANDOM.nextInt());
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.runtime.AbstractRuntime
 * JD-Core Version:    0.5.4
 */