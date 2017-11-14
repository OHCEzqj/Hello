/*    */ package org.jacoco.ant;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Iterator;
/*    */ import org.apache.tools.ant.BuildException;
/*    */ import org.apache.tools.ant.Task;
/*    */ import org.apache.tools.ant.types.Resource;
/*    */ import org.apache.tools.ant.types.ResourceCollection;
/*    */ import org.apache.tools.ant.types.resources.Union;
/*    */ import org.apache.tools.ant.util.FileUtils;
/*    */ import org.jacoco.core.instr.Instrumenter;
/*    */ import org.jacoco.core.runtime.OfflineInstrumentationAccessGenerator;
/*    */ 
/*    */ public class InstrumentTask extends Task
/*    */ {
/*    */   private File destdir;
/*    */   private final Union files;
/*    */   private boolean removesignatures;
/*    */ 
/*    */   public InstrumentTask()
/*    */   {
/* 38 */     this.files = new Union();
/*    */ 
/* 40 */     this.removesignatures = true;
/*    */   }
/*    */ 
/*    */   public void setDestdir(File destdir)
/*    */   {
/* 49 */     this.destdir = destdir;
/*    */   }
/*    */ 
/*    */   public void setRemovesignatures(boolean removesignatures)
/*    */   {
/* 59 */     this.removesignatures = removesignatures;
/*    */   }
/*    */ 
/*    */   public void addConfigured(ResourceCollection resources)
/*    */   {
/* 69 */     this.files.add(resources);
/*    */   }
/*    */ 
/*    */   public void execute() throws BuildException
/*    */   {
/* 74 */     if (this.destdir == null) {
/* 75 */       throw new BuildException("Destination directory must be supplied", getLocation());
/*    */     }
/*    */ 
/* 78 */     int total = 0;
/* 79 */     Instrumenter instrumenter = new Instrumenter(new OfflineInstrumentationAccessGenerator());
/*    */ 
/* 81 */     instrumenter.setRemoveSignatures(this.removesignatures);
/* 82 */     Iterator resourceIterator = this.files.iterator();
/* 83 */     while (resourceIterator.hasNext()) {
/* 84 */       Resource resource = (Resource)resourceIterator.next();
/* 85 */       if (resource.isDirectory()) {
/*    */         continue;
/*    */       }
/* 88 */       total += instrument(instrumenter, resource);
/*    */     }
/* 90 */     log(String.format("Instrumented %s classes to %s", new Object[] { Integer.valueOf(total), this.destdir.getAbsolutePath() }));
/*    */   }
/*    */ 
/*    */   private int instrument(Instrumenter instrumenter, Resource resource)
/*    */   {
/* 96 */     File file = new File(this.destdir, resource.getName());
/* 97 */     file.getParentFile().mkdirs();
/*    */     try {
/* 99 */       InputStream input = null;
/* 100 */       OutputStream output = null;
/*    */       try {
/* 102 */         input = resource.getInputStream();
/* 103 */         output = new FileOutputStream(file);
/* 104 */         int i = instrumenter.instrumentAll(input, output, resource.getName());
/*    */ 
/* 108 */         return i;
/*    */       }
/*    */       finally
/*    */       {
/* 107 */         FileUtils.close(input);
/* 108 */         FileUtils.close(output);
/*    */       }
/*    */     } catch (Exception e) {
/* 111 */       file.delete();
/* 112 */       throw new BuildException(String.format("Error while instrumenting %s", new Object[] { resource }), e, getLocation());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.ant.InstrumentTask
 * JD-Core Version:    0.5.4
 */