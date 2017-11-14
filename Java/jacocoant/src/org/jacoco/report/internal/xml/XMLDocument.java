/*     */ package org.jacoco.report.internal.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class XMLDocument extends XMLElement
/*     */ {
/*     */   private static final String HEADER = "<?xml version=\"1.0\" encoding=\"%s\"?>";
/*     */   private static final String HEADER_STANDALONE = "<?xml version=\"1.0\" encoding=\"%s\" standalone=\"yes\"?>";
/*     */   private static final String DOCTYPE = "<!DOCTYPE %s PUBLIC \"%s\" \"%s\">";
/*     */ 
/*     */   public XMLDocument(String rootnode, String pubId, String system, String encoding, boolean standalone, Writer writer)
/*     */     throws IOException
/*     */   {
/*  60 */     super(writer, rootnode);
/*  61 */     writeHeader(rootnode, pubId, system, encoding, standalone, writer);
/*  62 */     beginOpenTag();
/*     */   }
/*     */ 
/*     */   public XMLDocument(String rootnode, String pubId, String system, String encoding, boolean standalone, OutputStream output)
/*     */     throws IOException
/*     */   {
/*  88 */     this(rootnode, pubId, system, encoding, standalone, new OutputStreamWriter(output, encoding));
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  94 */     super.close();
/*  95 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   private static void writeHeader(String rootnode, String pubId, String system, String encoding, boolean standalone, Writer writer)
/*     */     throws IOException
/*     */   {
/* 101 */     if (standalone)
/* 102 */       writer.write(String.format("<?xml version=\"1.0\" encoding=\"%s\" standalone=\"yes\"?>", new Object[] { encoding }));
/*     */     else {
/* 104 */       writer.write(String.format("<?xml version=\"1.0\" encoding=\"%s\"?>", new Object[] { encoding }));
/*     */     }
/* 106 */     if (pubId != null)
/* 107 */       writer.write(String.format("<!DOCTYPE %s PUBLIC \"%s\" \"%s\">", new Object[] { rootnode, pubId, system }));
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.xml.XMLDocument
 * JD-Core Version:    0.5.4
 */