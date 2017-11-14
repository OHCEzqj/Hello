/*    */ package org.jacoco.ant;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.tools.ant.types.Resource;
/*    */ import org.jacoco.report.InputStreamSourceFileLocator;
/*    */ 
/*    */ class AntFilesLocator extends InputStreamSourceFileLocator
/*    */ {
/*    */   private final Map<String, Resource> resources;
/*    */ 
/*    */   public AntFilesLocator(String encoding, int tabWidth)
/*    */   {
/* 32 */     super(encoding, tabWidth);
/* 33 */     this.resources = new HashMap();
/*    */   }
/*    */ 
/*    */   void add(Resource file)
/*    */   {
/* 43 */     this.resources.put(file.getName().replace(File.separatorChar, '/'), file);
/*    */   }
/*    */ 
/*    */   protected InputStream getSourceStream(String path) throws IOException
/*    */   {
/* 48 */     Resource file = (Resource)this.resources.get(path);
/* 49 */     if (file == null) {
/* 50 */       return null;
/*    */     }
/* 52 */     return file.getInputStream();
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.ant.AntFilesLocator
 * JD-Core Version:    0.5.4
 */