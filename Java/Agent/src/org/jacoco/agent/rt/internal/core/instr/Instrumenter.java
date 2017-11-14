/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.instr;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassReader;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassWriter;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.ContentTypeDetector;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.Java9Support;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.Pack200Streams;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.flow.ClassProbesAdapter;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.ClassInstrumenter;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.IProbeArrayStrategy;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.ProbeArrayStrategyFactory;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.SignatureRemover;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.runtime.IExecutionDataAccessorGenerator;
/*     */ 
/*     */ public class Instrumenter
/*     */ {
/*     */   private final IExecutionDataAccessorGenerator accessorGenerator;
/*     */   private final SignatureRemover signatureRemover;
/*     */ 
/*     */   public Instrumenter(IExecutionDataAccessorGenerator runtime)
/*     */   {
/*  53 */     this.accessorGenerator = runtime;
/*  54 */     this.signatureRemover = new SignatureRemover();
/*     */   }
/*     */ 
/*     */   public void setRemoveSignatures(boolean flag)
/*     */   {
/*  67 */     this.signatureRemover.setActive(flag);
/*     */   }
/*     */ 
/*     */   public byte[] instrument(ClassReader reader)
/*     */   {
/*  79 */     ClassWriter writer = new ClassWriter(reader, 0)
/*     */     {
/*     */       protected String getCommonSuperClass(String type1, String type2)
/*     */       {
/*  83 */         throw new IllegalStateException();
/*     */       }
/*     */     };
/*  86 */     IProbeArrayStrategy strategy = ProbeArrayStrategyFactory.createFor(reader, this.accessorGenerator);
/*     */ 
/*  88 */     ClassVisitor visitor = new ClassProbesAdapter(new ClassInstrumenter(strategy, writer), true);
/*     */ 
/*  90 */     reader.accept(visitor, 8);
/*  91 */     return writer.toByteArray();
/*     */   }
/*     */ 
/*     */   public byte[] instrument(byte[] buffer, String name)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 108 */       if (Java9Support.isPatchRequired(buffer)) {
/* 109 */         byte[] result = instrument(new ClassReader(Java9Support.downgrade(buffer)));
/*     */ 
/* 111 */         Java9Support.upgrade(result);
/* 112 */         return result;
/*     */       }
/* 114 */       return instrument(new ClassReader(buffer));
/*     */     }
/*     */     catch (RuntimeException e) {
/* 117 */       throw instrumentError(name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] instrument(InputStream input, String name)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 136 */       return instrument(Java9Support.readFully(input), name);
/*     */     } catch (RuntimeException e) {
/* 138 */       throw instrumentError(name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void instrument(InputStream input, OutputStream output, String name)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 158 */       output.write(instrument(Java9Support.readFully(input), name));
/*     */     } catch (RuntimeException e) {
/* 160 */       throw instrumentError(name, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private IOException instrumentError(String name, RuntimeException cause)
/*     */   {
/* 166 */     IOException ex = new IOException(String.format("Error while instrumenting class %s.", new Object[] { name }));
/*     */ 
/* 168 */     ex.initCause(cause);
/* 169 */     return ex;
/*     */   }
/*     */ 
/*     */   public int instrumentAll(InputStream input, OutputStream output, String name)
/*     */     throws IOException
/*     */   {
/* 190 */     ContentTypeDetector detector = new ContentTypeDetector(input);
/* 191 */     switch (detector.getType())
/*     */     {
/*     */     case -889275714:
/* 193 */       instrument(detector.getInputStream(), output, name);
/* 194 */       return 1;
/*     */     case 1347093252:
/* 196 */       return instrumentZip(detector.getInputStream(), output, name);
/*     */     case 529203200:
/* 198 */       return instrumentGzip(detector.getInputStream(), output, name);
/*     */     case -889270259:
/* 200 */       return instrumentPack200(detector.getInputStream(), output, name);
/*     */     }
/* 202 */     copy(detector.getInputStream(), output);
/* 203 */     return 0;
/*     */   }
/*     */ 
/*     */   private int instrumentZip(InputStream input, OutputStream output, String name)
/*     */     throws IOException
/*     */   {
/* 209 */     ZipInputStream zipin = new ZipInputStream(input);
/* 210 */     ZipOutputStream zipout = new ZipOutputStream(output);
/*     */ 
/* 212 */     int count = 0;
/* 213 */     while ((entry = zipin.getNextEntry()) != null)
/*     */     {
/*     */       ZipEntry entry;
/* 214 */       String entryName = entry.getName();
/* 215 */       if (this.signatureRemover.removeEntry(entryName)) {
/*     */         continue;
/*     */       }
/*     */ 
/* 219 */       zipout.putNextEntry(new ZipEntry(entryName));
/* 220 */       if (!this.signatureRemover.filterEntry(entryName, zipin, zipout)) {
/* 221 */         count += instrumentAll(zipin, zipout, name + "@" + entryName);
/*     */       }
/* 223 */       zipout.closeEntry();
/*     */     }
/* 225 */     zipout.finish();
/* 226 */     return count;
/*     */   }
/*     */ 
/*     */   private int instrumentGzip(InputStream input, OutputStream output, String name) throws IOException
/*     */   {
/* 231 */     GZIPOutputStream gzout = new GZIPOutputStream(output);
/* 232 */     int count = instrumentAll(new GZIPInputStream(input), gzout, name);
/* 233 */     gzout.finish();
/* 234 */     return count;
/*     */   }
/*     */ 
/*     */   private int instrumentPack200(InputStream input, OutputStream output, String name) throws IOException
/*     */   {
/* 239 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 240 */     int count = instrumentAll(Pack200Streams.unpack(input), buffer, name);
/*     */ 
/* 242 */     Pack200Streams.pack(buffer.toByteArray(), output);
/* 243 */     return count;
/*     */   }
/*     */ 
/*     */   private void copy(InputStream input, OutputStream output) throws IOException
/*     */   {
/* 248 */     byte[] buffer = new byte[1024];
/*     */ 
/* 250 */     while ((len = input.read(buffer)) != -1)
/*     */     {
/*     */       int len;
/* 251 */       output.write(buffer, 0, len);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.instr.Instrumenter
 * JD-Core Version:    0.5.4
 */