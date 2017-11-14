/*     */ package org.jacoco.core.internal.instr;
/*     */ 
/*     */ import org.jacoco.asm.MethodVisitor;
/*     */ 
/*     */ public final class InstrSupport
/*     */ {
/*     */   public static final int ASM_API_VERSION = 327680;
/*     */   public static final String DATAFIELD_NAME = "$jacocoData";
/*     */   public static final int DATAFIELD_ACC = 4234;
/*     */   public static final int DATAFIELD_INTF_ACC = 4121;
/*     */   public static final String DATAFIELD_DESC = "[Z";
/*     */   public static final String INITMETHOD_NAME = "$jacocoInit";
/*     */   public static final String INITMETHOD_DESC = "()[Z";
/*     */   public static final int INITMETHOD_ACC = 4106;
/*     */   static final String CLINIT_NAME = "<clinit>";
/*     */   static final String CLINIT_DESC = "()V";
/*     */   static final int CLINIT_ACC = 4104;
/*     */ 
/*     */   public static void assertNotInstrumented(String member, String owner)
/*     */     throws IllegalStateException
/*     */   {
/* 175 */     if ((member.equals("$jacocoData")) || (member.equals("$jacocoInit")))
/* 176 */       throw new IllegalStateException(String.format("Class %s is already instrumented.", new Object[] { owner }));
/*     */   }
/*     */ 
/*     */   public static void push(MethodVisitor mv, int value)
/*     */   {
/* 192 */     if ((value >= -1) && (value <= 5))
/* 193 */       mv.visitInsn(3 + value);
/* 194 */     else if ((value >= -128) && (value <= 127))
/* 195 */       mv.visitIntInsn(16, value);
/* 196 */     else if ((value >= -32768) && (value <= 32767))
/* 197 */       mv.visitIntInsn(17, value);
/*     */     else
/* 199 */       mv.visitLdcInsn(Integer.valueOf(value));
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.InstrSupport
 * JD-Core Version:    0.5.4
 */