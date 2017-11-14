/*    */ package org.jacoco.report.internal.html;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Writer;
/*    */ import org.jacoco.report.internal.xml.XMLDocument;
/*    */ 
/*    */ public class HTMLDocument extends XMLDocument
/*    */ {
/*    */   private static final String ROOT = "html";
/*    */   private static final String PUBID = "-//W3C//DTD XHTML 1.0 Strict//EN";
/*    */   private static final String SYSTEM = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
/*    */   private static final String XMLNS = "xmlns";
/*    */   private static final String XHTML_NAMESPACE_URL = "http://www.w3.org/1999/xhtml";
/*    */ 
/*    */   public HTMLDocument(Writer writer, String encoding)
/*    */     throws IOException
/*    */   {
/* 48 */     super("html", "-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd", encoding, false, writer);
/* 49 */     attr("xmlns", "http://www.w3.org/1999/xhtml");
/*    */   }
/*    */ 
/*    */   public HTMLDocument(OutputStream output, String encoding)
/*    */     throws IOException
/*    */   {
/* 64 */     super("html", "-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd", encoding, false, output);
/* 65 */     attr("xmlns", "http://www.w3.org/1999/xhtml");
/*    */   }
/*    */ 
/*    */   public HTMLElement element(String name) throws IOException
/*    */   {
/* 70 */     HTMLElement element = new HTMLElement(this.writer, name);
/* 71 */     addChildElement(element);
/* 72 */     return element;
/*    */   }
/*    */ 
/*    */   public HTMLElement head()
/*    */     throws IOException
/*    */   {
/* 83 */     return element("head");
/*    */   }
/*    */ 
/*    */   public HTMLElement body()
/*    */     throws IOException
/*    */   {
/* 94 */     return element("body");
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.HTMLDocument
 * JD-Core Version:    0.5.4
 */