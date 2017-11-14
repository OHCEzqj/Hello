/*     */ package org.jacoco.report.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jacoco.report.IMultiReportOutput;
/*     */ 
/*     */ public class ReportOutputFolder
/*     */ {
/*     */   private final IMultiReportOutput output;
/*     */   private final ReportOutputFolder parent;
/*     */   private final String path;
/*  34 */   private final Map<String, ReportOutputFolder> subFolders = new HashMap();
/*     */   private final NormalizedFileNames fileNames;
/*     */ 
/*     */   public ReportOutputFolder(IMultiReportOutput output)
/*     */   {
/*  45 */     this(output, null, "");
/*     */   }
/*     */ 
/*     */   private ReportOutputFolder(IMultiReportOutput output, ReportOutputFolder parent, String path)
/*     */   {
/*  56 */     this.output = output;
/*  57 */     this.parent = parent;
/*  58 */     this.path = path;
/*  59 */     this.fileNames = new NormalizedFileNames();
/*     */   }
/*     */ 
/*     */   public ReportOutputFolder subFolder(String name)
/*     */   {
/*  70 */     String normalizedName = normalize(name);
/*  71 */     ReportOutputFolder folder = (ReportOutputFolder)this.subFolders.get(normalizedName);
/*  72 */     if (folder != null) {
/*  73 */       return folder;
/*     */     }
/*  75 */     folder = new ReportOutputFolder(this.output, this, this.path + normalizedName + "/");
/*     */ 
/*  77 */     this.subFolders.put(normalizedName, folder);
/*  78 */     return folder;
/*     */   }
/*     */ 
/*     */   public OutputStream createFile(String name)
/*     */     throws IOException
/*     */   {
/*  91 */     return this.output.createFile(this.path + normalize(name));
/*     */   }
/*     */ 
/*     */   public String getLink(ReportOutputFolder base, String name)
/*     */   {
/* 106 */     if (base.isAncestorOf(this)) {
/* 107 */       return this.path.substring(base.path.length()) + normalize(name);
/*     */     }
/* 109 */     if (base.parent == null) {
/* 110 */       throw new IllegalArgumentException("Folders with different roots.");
/*     */     }
/* 112 */     return "../" + getLink(base.parent, name);
/*     */   }
/*     */ 
/*     */   private boolean isAncestorOf(ReportOutputFolder folder) {
/* 116 */     if (this == folder) {
/* 117 */       return true;
/*     */     }
/* 119 */     return (folder.parent == null) ? false : isAncestorOf(folder.parent);
/*     */   }
/*     */ 
/*     */   private String normalize(String name) {
/* 123 */     return this.fileNames.getFileName(name);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.ReportOutputFolder
 * JD-Core Version:    0.5.4
 */