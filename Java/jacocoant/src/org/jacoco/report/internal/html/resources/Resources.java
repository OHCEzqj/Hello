/*     */ package org.jacoco.report.internal.html.resources;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.jacoco.core.analysis.ICoverageNode.ElementType;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ 
/*     */ public class Resources
/*     */ {
/*     */   public static final String STYLESHEET = "report.css";
/*     */   public static final String PRETTIFY_STYLESHEET = "prettify.css";
/*     */   public static final String PRETTIFY_SCRIPT = "prettify.js";
/*     */   public static final String SORT_SCRIPT = "sort.js";
/*     */   public static final String REDBAR = "redbar.gif";
/*     */   public static final String GREENBAR = "greenbar.gif";
/*     */   private final ReportOutputFolder folder;
/*     */ 
/*     */   public Resources(ReportOutputFolder root)
/*     */   {
/*  54 */     this.folder = root.subFolder("jacoco-resources");
/*     */   }
/*     */ 
/*     */   public String getLink(ReportOutputFolder base, String name)
/*     */   {
/*  67 */     return this.folder.getLink(base, name);
/*     */   }
/*     */ 
/*     */   public static String getElementStyle(ICoverageNode.ElementType type)
/*     */   {
/*  78 */     switch (1.$SwitchMap$org$jacoco$core$analysis$ICoverageNode$ElementType[type.ordinal()])
/*     */     {
/*     */     case 1:
/*  80 */       return "el_group";
/*     */     case 2:
/*  82 */       return "el_bundle";
/*     */     case 3:
/*  84 */       return "el_package";
/*     */     case 4:
/*  86 */       return "el_source";
/*     */     case 5:
/*  88 */       return "el_class";
/*     */     case 6:
/*  90 */       return "el_method";
/*     */     }
/*  92 */     throw new AssertionError("Unknown element type: " + type);
/*     */   }
/*     */ 
/*     */   public void copyResources()
/*     */     throws IOException
/*     */   {
/* 102 */     copyResource("report.css");
/* 103 */     copyResource("report.gif");
/* 104 */     copyResource("group.gif");
/* 105 */     copyResource("bundle.gif");
/* 106 */     copyResource("package.gif");
/* 107 */     copyResource("source.gif");
/* 108 */     copyResource("class.gif");
/* 109 */     copyResource("method.gif");
/* 110 */     copyResource("session.gif");
/* 111 */     copyResource("sort.gif");
/* 112 */     copyResource("up.gif");
/* 113 */     copyResource("down.gif");
/* 114 */     copyResource("branchfc.gif");
/* 115 */     copyResource("branchnc.gif");
/* 116 */     copyResource("branchpc.gif");
/* 117 */     copyResource("redbar.gif");
/* 118 */     copyResource("greenbar.gif");
/* 119 */     copyResource("prettify.css");
/* 120 */     copyResource("prettify.js");
/* 121 */     copyResource("sort.js");
/*     */   }
/*     */ 
/*     */   private void copyResource(String name) throws IOException {
/* 125 */     InputStream in = Resources.class.getResourceAsStream(name);
/* 126 */     OutputStream out = this.folder.createFile(name);
/* 127 */     byte[] buffer = new byte[256];
/*     */ 
/* 129 */     while ((len = in.read(buffer)) != -1)
/*     */     {
/*     */       int len;
/* 130 */       out.write(buffer, 0, len);
/*     */     }
/* 132 */     in.close();
/* 133 */     out.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.resources.Resources
 * JD-Core Version:    0.5.4
 */