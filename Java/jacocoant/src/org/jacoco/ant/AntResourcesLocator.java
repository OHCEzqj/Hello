/*    */ package org.jacoco.ant;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.apache.tools.ant.types.Resource;
/*    */ import org.apache.tools.ant.types.resources.FileResource;
/*    */ import org.jacoco.report.DirectorySourceFileLocator;
/*    */ import org.jacoco.report.MultiSourceFileLocator;
/*    */ 
/*    */ class AntResourcesLocator extends MultiSourceFileLocator
/*    */ {
/*    */   private final String encoding;
/*    */   private final AntFilesLocator filesLocator;
/*    */   private boolean empty;
/*    */ 
/*    */   AntResourcesLocator(String encoding, int tabWidth)
/*    */   {
/* 38 */     super(tabWidth);
/* 39 */     this.encoding = encoding;
/* 40 */     this.filesLocator = new AntFilesLocator(encoding, tabWidth);
/* 41 */     this.empty = true;
/* 42 */     super.add(this.filesLocator);
/*    */   }
/*    */ 
/*    */   void add(Resource resource)
/*    */   {
/* 52 */     this.empty = false;
/* 53 */     if (resource.isDirectory()) {
/* 54 */       FileResource dir = (FileResource)resource;
/* 55 */       super.add(new DirectorySourceFileLocator(dir.getFile(), this.encoding, getTabWidth()));
/*    */     }
/*    */     else {
/* 58 */       this.filesLocator.add(resource);
/*    */     }
/*    */   }
/*    */ 
/*    */   void addAll(Iterator<?> iterator) {
/* 63 */     while (iterator.hasNext())
/* 64 */       add((Resource)iterator.next());
/*    */   }
/*    */ 
/*    */   boolean isEmpty()
/*    */   {
/* 74 */     return this.empty;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.ant.AntResourcesLocator
 * JD-Core Version:    0.5.4
 */