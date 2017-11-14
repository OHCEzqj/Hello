/*     */ package org.jacoco.core.internal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class ContentTypeDetector
/*     */ {
/*     */   public static final int UNKNOWN = -1;
/*     */   public static final int CLASSFILE = -889275714;
/*     */   public static final int ZIPFILE = 1347093252;
/*     */   public static final int GZFILE = 529203200;
/*     */   public static final int PACK200FILE = -889270259;
/*     */   private static final int BUFFER_SIZE = 8;
/*     */   private final InputStream in;
/*     */   private final int type;
/*     */ 
/*     */   public ContentTypeDetector(InputStream in)
/*     */     throws IOException
/*     */   {
/*  57 */     if (in.markSupported())
/*  58 */       this.in = in;
/*     */     else {
/*  60 */       this.in = new BufferedInputStream(in, 8);
/*     */     }
/*  62 */     this.in.mark(8);
/*  63 */     this.type = determineType(this.in);
/*  64 */     this.in.reset();
/*     */   }
/*     */ 
/*     */   private static int determineType(InputStream in) throws IOException {
/*  68 */     int header = readInt(in);
/*  69 */     switch (header)
/*     */     {
/*     */     case 1347093252:
/*  71 */       return 1347093252;
/*     */     case -889270259:
/*  73 */       return -889270259;
/*     */     case -889275714:
/*  76 */       switch (readInt(in))
/*     */       {
/*     */       case 46:
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/*     */       case 196653:
/*  86 */         return -889275714;
/*     */       }
/*     */     }
/*  89 */     if ((header & 0xFFFF0000) == 529203200) {
/*  90 */       return 529203200;
/*     */     }
/*  92 */     return -1;
/*     */   }
/*     */ 
/*     */   private static int readInt(InputStream in) throws IOException {
/*  96 */     return in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read();
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/* 106 */     return this.in;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 115 */     return this.type;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.ContentTypeDetector
 * JD-Core Version:    0.5.4
 */