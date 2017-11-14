/*     */ package org.jacoco.core.tools;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.FileChannel;
/*     */ import org.jacoco.core.data.ExecutionDataReader;
/*     */ import org.jacoco.core.data.ExecutionDataStore;
/*     */ import org.jacoco.core.data.ExecutionDataWriter;
/*     */ import org.jacoco.core.data.SessionInfoStore;
/*     */ 
/*     */ public class ExecFileLoader
/*     */ {
/*     */   private final SessionInfoStore sessionInfos;
/*     */   private final ExecutionDataStore executionData;
/*     */ 
/*     */   public ExecFileLoader()
/*     */   {
/*  42 */     this.sessionInfos = new SessionInfoStore();
/*  43 */     this.executionData = new ExecutionDataStore();
/*     */   }
/*     */ 
/*     */   public void load(InputStream stream)
/*     */     throws IOException
/*     */   {
/*  55 */     ExecutionDataReader reader = new ExecutionDataReader(new BufferedInputStream(stream));
/*     */ 
/*  57 */     reader.setExecutionDataVisitor(this.executionData);
/*  58 */     reader.setSessionInfoVisitor(this.sessionInfos);
/*  59 */     reader.read();
/*     */   }
/*     */ 
/*     */   public void load(File file)
/*     */     throws IOException
/*     */   {
/*  71 */     InputStream stream = new FileInputStream(file);
/*     */     try {
/*  73 */       load(stream);
/*     */     } finally {
/*  75 */       stream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void save(OutputStream stream)
/*     */     throws IOException
/*     */   {
/*  88 */     ExecutionDataWriter dataWriter = new ExecutionDataWriter(stream);
/*  89 */     this.sessionInfos.accept(dataWriter);
/*  90 */     this.executionData.accept(dataWriter);
/*     */   }
/*     */ 
/*     */   public void save(File file, boolean append)
/*     */     throws IOException
/*     */   {
/* 107 */     File folder = file.getParentFile();
/* 108 */     if (folder != null) {
/* 109 */       folder.mkdirs();
/*     */     }
/* 111 */     FileOutputStream fileStream = new FileOutputStream(file, append);
/*     */ 
/* 113 */     fileStream.getChannel().lock();
/* 114 */     OutputStream bufferedStream = new BufferedOutputStream(fileStream);
/*     */     try {
/* 116 */       save(bufferedStream);
/*     */     } finally {
/* 118 */       bufferedStream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public SessionInfoStore getSessionInfoStore()
/*     */   {
/* 128 */     return this.sessionInfos;
/*     */   }
/*     */ 
/*     */   public ExecutionDataStore getExecutionDataStore()
/*     */   {
/* 137 */     return this.executionData;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.tools.ExecFileLoader
 * JD-Core Version:    0.5.4
 */