/*    */ package org.jacoco.agent.rt.internal_8ff85ea.core;
/*    */ 
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ public final class JaCoCo
/*    */ {
/*    */   public static final String VERSION;
/*    */   public static final String HOMEURL;
/*    */   public static final String RUNTIMEPACKAGE;
/*    */ 
/*    */   static
/*    */   {
/* 31 */     ResourceBundle bundle = ResourceBundle.getBundle("org.jacoco.agent.rt.internal_8ff85ea.core.jacoco");
/*    */ 
/* 33 */     VERSION = bundle.getString("VERSION");
/* 34 */     HOMEURL = bundle.getString("HOMEURL");
/* 35 */     RUNTIMEPACKAGE = bundle.getString("RUNTIMEPACKAGE");
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.JaCoCo
 * JD-Core Version:    0.5.4
 */