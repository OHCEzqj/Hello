/*     */ package org.jacoco.core.data;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.jacoco.core.internal.data.CompactDataOutput;
/*     */ 
/*     */ public class ExecutionDataWriter
/*     */   implements ISessionInfoVisitor, IExecutionDataVisitor
/*     */ {
/*  31 */   public static final char FORMAT_VERSION = 'ဇ';
/*     */   public static final char MAGIC_NUMBER = '샀';
/*     */   public static final byte BLOCK_HEADER = 1;
/*     */   public static final byte BLOCK_SESSIONINFO = 16;
/*     */   public static final byte BLOCK_EXECUTIONDATA = 17;
/*     */   protected final CompactDataOutput out;
/*     */ 
/*     */   public ExecutionDataWriter(OutputStream output)
/*     */     throws IOException
/*     */   {
/*  60 */     this.out = new CompactDataOutput(output);
/*  61 */     writeHeader();
/*     */   }
/*     */ 
/*     */   private void writeHeader()
/*     */     throws IOException
/*     */   {
/*  71 */     this.out.writeByte(1);
/*  72 */     this.out.writeChar(49344);
/*  73 */     this.out.writeChar(FORMAT_VERSION);
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/*  83 */     this.out.flush();
/*     */   }
/*     */ 
/*     */   public void visitSessionInfo(SessionInfo info) {
/*     */     try {
/*  88 */       this.out.writeByte(16);
/*  89 */       this.out.writeUTF(info.getId());
/*  90 */       this.out.writeLong(info.getStartTimeStamp());
/*  91 */       this.out.writeLong(info.getDumpTimeStamp());
/*     */     } catch (IOException e) {
/*  93 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitClassExecution(ExecutionData data) {
/*  98 */     if (!data.hasHits()) return;
/*     */     try {
/* 100 */       this.out.writeByte(17);
/* 101 */       this.out.writeLong(data.getId());
/* 102 */       this.out.writeUTF(data.getName());
/* 103 */       this.out.writeBooleanArray(data.getProbes());
/*     */     } catch (IOException e) {
/* 105 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final byte[] getFileHeader()
/*     */   {
/* 118 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */     try {
/* 120 */       new ExecutionDataWriter(buffer);
/*     */     }
/*     */     catch (IOException e) {
/* 123 */       throw new AssertionError(e);
/*     */     }
/* 125 */     return buffer.toByteArray();
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.data.ExecutionDataWriter
 * JD-Core Version:    0.5.4
 */