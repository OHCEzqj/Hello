/*     */ package org.jacoco.core.analysis;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import org.jacoco.asm.ClassReader;
/*     */ import org.jacoco.asm.ClassVisitor;
/*     */ import org.jacoco.core.data.ExecutionData;
/*     */ import org.jacoco.core.data.ExecutionDataStore;
/*     */ import org.jacoco.core.internal.ContentTypeDetector;
/*     */ import org.jacoco.core.internal.Java9Support;
/*     */ import org.jacoco.core.internal.Pack200Streams;
/*     */ import org.jacoco.core.internal.analysis.ClassAnalyzer;
/*     */ import org.jacoco.core.internal.analysis.ClassCoverageImpl;
/*     */ import org.jacoco.core.internal.analysis.StringPool;
/*     */ import org.jacoco.core.internal.data.CRC64;
/*     */ import org.jacoco.core.internal.flow.ClassProbesAdapter;
/*     */ 
/*     */ public class Analyzer
/*     */ {
/*     */   private final ExecutionDataStore executionData;
/*     */   private final ICoverageVisitor coverageVisitor;
/*     */   private final StringPool stringPool;
/*     */ 
/*     */   public Analyzer(ExecutionDataStore executionData, ICoverageVisitor coverageVisitor)
/*     */   {
/*  63 */     this.executionData = executionData;
/*  64 */     this.coverageVisitor = coverageVisitor;
/*  65 */     this.stringPool = new StringPool();
/*     */   }
/*     */ 
/*     */   private ClassVisitor createAnalyzingVisitor(long classid, String className)
/*     */   {
/*  79 */     ExecutionData data = this.executionData.get(classid);
/*     */     boolean noMatch;
/*     */     boolean[] probes;
/*     */     boolean noMatch;
/*  82 */     if (data == null) {
/*  83 */       boolean[] probes = null;
/*  84 */       noMatch = this.executionData.contains(className);
/*     */     } else {
/*  86 */       probes = data.getProbes();
/*  87 */       noMatch = false;
/*     */     }
/*  89 */     ClassCoverageImpl coverage = new ClassCoverageImpl(className, classid, noMatch);
/*     */ 
/*  91 */     ClassAnalyzer analyzer = new ClassAnalyzer(coverage, probes, this.stringPool, coverage)
/*     */     {
/*     */       public void visitEnd()
/*     */       {
/*  95 */         super.visitEnd();
/*  96 */         Analyzer.this.coverageVisitor.visitCoverage(this.val$coverage);
/*     */       }
/*     */     };
/*  99 */     return new ClassProbesAdapter(analyzer, false);
/*     */   }
/*     */ 
/*     */   public void analyzeClass(ClassReader reader)
/*     */   {
/* 109 */     ClassVisitor visitor = createAnalyzingVisitor(CRC64.checksum(reader.b), reader.getClassName());
/*     */ 
/* 111 */     reader.accept(visitor, 0);
/*     */   }
/*     */ 
/*     */   public void analyzeClass(byte[] buffer, String location)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 127 */       analyzeClass(new ClassReader(Java9Support.downgradeIfRequired(buffer)));
/*     */     }
/*     */     catch (RuntimeException cause) {
/* 130 */       throw analyzerError(location, cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void analyzeClass(InputStream input, String location)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 147 */       analyzeClass(Java9Support.readFully(input), location);
/*     */     } catch (RuntimeException e) {
/* 149 */       throw analyzerError(location, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private IOException analyzerError(String location, Exception cause)
/*     */   {
/* 155 */     IOException ex = new IOException(String.format("Error while analyzing %s.", new Object[] { location }));
/*     */ 
/* 157 */     ex.initCause(cause);
/* 158 */     return ex;
/*     */   }
/*     */ 
/*     */   public int analyzeAll(InputStream input, String location)
/*     */     throws IOException
/*     */   {
/*     */     ContentTypeDetector detector;
/*     */     try
/*     */     {
/* 179 */       detector = new ContentTypeDetector(input);
/*     */     } catch (IOException e) {
/* 181 */       throw analyzerError(location, e);
/*     */     }
/* 183 */     switch (detector.getType())
/*     */     {
/*     */     case -889275714:
/* 185 */       analyzeClass(detector.getInputStream(), location);
/* 186 */       return 1;
/*     */     case 1347093252:
/* 188 */       return analyzeZip(detector.getInputStream(), location);
/*     */     case 529203200:
/* 190 */       return analyzeGzip(detector.getInputStream(), location);
/*     */     case -889270259:
/* 192 */       return analyzePack200(detector.getInputStream(), location);
/*     */     }
/* 194 */     return 0;
/*     */   }
/*     */ 
/*     */   public int analyzeAll(File file)
/*     */     throws IOException
/*     */   {
/* 210 */     int count = 0;
/* 211 */     if (file.isDirectory()) {
/* 212 */       for (File f : file.listFiles())
/* 213 */         count += analyzeAll(f);
/*     */     }
/*     */     else {
/* 216 */       InputStream in = new FileInputStream(file);
/*     */       try {
/* 218 */         count += analyzeAll(in, file.getPath());
/*     */       } finally {
/* 220 */         in.close();
/*     */       }
/*     */     }
/* 223 */     return count;
/*     */   }
/*     */ 
/*     */   public int analyzeAll(String path, File basedir)
/*     */     throws IOException
/*     */   {
/* 242 */     int count = 0;
/* 243 */     StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
/*     */ 
/* 245 */     while (st.hasMoreTokens()) {
/* 246 */       count += analyzeAll(new File(basedir, st.nextToken()));
/*     */     }
/* 248 */     return count;
/*     */   }
/*     */ 
/*     */   private int analyzeZip(InputStream input, String location) throws IOException
/*     */   {
/* 253 */     ZipInputStream zip = new ZipInputStream(input);
/*     */ 
/* 255 */     int count = 0;
/* 256 */     while ((entry = nextEntry(zip, location)) != null)
/*     */     {
/*     */       ZipEntry entry;
/* 257 */       count += analyzeAll(zip, location + "@" + entry.getName());
/*     */     }
/* 259 */     return count;
/*     */   }
/*     */ 
/*     */   private ZipEntry nextEntry(ZipInputStream input, String location) throws IOException
/*     */   {
/*     */     try {
/* 265 */       return input.getNextEntry();
/*     */     } catch (IOException e) {
/* 267 */       throw analyzerError(location, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int analyzeGzip(InputStream input, String location) throws IOException
/*     */   {
/*     */     GZIPInputStream gzipInputStream;
/*     */     try {
/* 275 */       gzipInputStream = new GZIPInputStream(input);
/*     */     } catch (IOException e) {
/* 277 */       throw analyzerError(location, e);
/*     */     }
/* 279 */     return analyzeAll(gzipInputStream, location);
/*     */   }
/*     */ 
/*     */   private int analyzePack200(InputStream input, String location) throws IOException
/*     */   {
/*     */     InputStream unpackedInput;
/*     */     try {
/* 286 */       unpackedInput = Pack200Streams.unpack(input);
/*     */     } catch (IOException e) {
/* 288 */       throw analyzerError(location, e);
/*     */     }
/* 290 */     return analyzeAll(unpackedInput, location);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.Analyzer
 * JD-Core Version:    0.5.4
 */