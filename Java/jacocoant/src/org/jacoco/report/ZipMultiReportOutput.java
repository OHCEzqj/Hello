/*     */ package org.jacoco.report;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class ZipMultiReportOutput
/*     */   implements IMultiReportOutput
/*     */ {
/*     */   private final ZipOutputStream zip;
/*     */   private OutputStream currentEntry;
/*     */ 
/*     */   public ZipMultiReportOutput(ZipOutputStream zip)
/*     */   {
/*  36 */     this.zip = zip;
/*     */   }
/*     */ 
/*     */   public ZipMultiReportOutput(OutputStream out)
/*     */   {
/*  46 */     this(new ZipOutputStream(out));
/*     */   }
/*     */ 
/*     */   public OutputStream createFile(String path) throws IOException {
/*  50 */     if (this.currentEntry != null) {
/*  51 */       this.currentEntry.close();
/*     */     }
/*  53 */     ZipEntry entry = new ZipEntry(path);
/*  54 */     this.zip.putNextEntry(entry);
/*  55 */     this.currentEntry = new EntryOutput(null);
/*  56 */     return this.currentEntry;
/*     */   }
/*     */ 
/*     */   public void close() throws IOException {
/*  60 */     this.zip.close();
/*     */   }
/*     */   private final class EntryOutput extends OutputStream {
/*     */     private boolean closed;
/*     */ 
/*  65 */     private EntryOutput() { this.closed = false; }
/*     */ 
/*     */     public void write(byte[] b, int off, int len)
/*     */       throws IOException
/*     */     {
/*  70 */       ensureNotClosed();
/*  71 */       ZipMultiReportOutput.this.zip.write(b, off, len);
/*     */     }
/*     */ 
/*     */     public void write(byte[] b) throws IOException
/*     */     {
/*  76 */       ensureNotClosed();
/*  77 */       ZipMultiReportOutput.this.zip.write(b);
/*     */     }
/*     */ 
/*     */     public void write(int b) throws IOException
/*     */     {
/*  82 */       ensureNotClosed();
/*  83 */       ZipMultiReportOutput.this.zip.write(b);
/*     */     }
/*     */ 
/*     */     public void flush() throws IOException
/*     */     {
/*  88 */       ensureNotClosed();
/*  89 */       ZipMultiReportOutput.this.zip.flush();
/*     */     }
/*     */ 
/*     */     public void close() throws IOException
/*     */     {
/*  94 */       if (!this.closed) {
/*  95 */         this.closed = true;
/*  96 */         ZipMultiReportOutput.this.zip.closeEntry();
/*     */       }
/*     */     }
/*     */ 
/*     */     private void ensureNotClosed() throws IOException {
/* 101 */       if (this.closed)
/* 102 */         throw new IOException("Zip entry already closed.");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.ZipMultiReportOutput
 * JD-Core Version:    0.5.4
 */