/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.internal;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class Java9Support
/*     */ {
/*     */   public static final int V1_9 = 53;
/*     */ 
/*     */   public static byte[] readFully(InputStream is)
/*     */     throws IOException
/*     */   {
/*  44 */     if (is == null) {
/*  45 */       throw new IllegalArgumentException();
/*     */     }
/*  47 */     byte[] buf = new byte[1024];
/*  48 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */     while (true) {
/*  50 */       int r = is.read(buf);
/*  51 */       if (r == -1) {
/*     */         break;
/*     */       }
/*  54 */       out.write(buf, 0, r);
/*     */     }
/*  56 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */   private static void putShort(byte[] b, int index, int s) {
/*  60 */     b[index] = (byte)(s >>> 8);
/*  61 */     b[(index + 1)] = (byte)s;
/*     */   }
/*     */ 
/*     */   private static short readShort(byte[] b, int index) {
/*  65 */     return (short)((b[index] & 0xFF) << 8 | b[(index + 1)] & 0xFF);
/*     */   }
/*     */ 
/*     */   public static boolean isPatchRequired(byte[] buffer)
/*     */   {
/*  76 */     return readShort(buffer, 6) == 53;
/*     */   }
/*     */ 
/*     */   public static byte[] downgradeIfRequired(byte[] buffer)
/*     */   {
/*  88 */     return (isPatchRequired(buffer)) ? downgrade(buffer) : buffer;
/*     */   }
/*     */ 
/*     */   public static byte[] downgrade(byte[] b)
/*     */   {
/*  99 */     byte[] result = new byte[b.length];
/* 100 */     System.arraycopy(b, 0, result, 0, b.length);
/* 101 */     putShort(result, 6, 52);
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   public static void upgrade(byte[] b)
/*     */   {
/* 112 */     putShort(b, 6, 53);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.Java9Support
 * JD-Core Version:    0.5.4
 */