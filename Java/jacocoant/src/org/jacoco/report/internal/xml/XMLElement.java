/*     */ package org.jacoco.report.internal.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class XMLElement
/*     */ {
/*     */   private static final char SPACE = ' ';
/*     */   private static final char EQ = '=';
/*     */   private static final char LT = '<';
/*     */   private static final char GT = '>';
/*     */   private static final char QUOT = '"';
/*     */   private static final char AMP = '&';
/*     */   private static final char SLASH = '/';
/*     */   protected final Writer writer;
/*     */   private final String name;
/*     */   private boolean openTagDone;
/*     */   private boolean closed;
/*     */   private XMLElement lastchild;
/*     */ 
/*     */   protected XMLElement(Writer writer, String name)
/*     */   {
/*  61 */     this.writer = writer;
/*  62 */     this.name = name;
/*  63 */     this.openTagDone = false;
/*  64 */     this.closed = false;
/*  65 */     this.lastchild = null;
/*     */   }
/*     */ 
/*     */   protected void beginOpenTag()
/*     */     throws IOException
/*     */   {
/*  76 */     this.writer.write(60);
/*  77 */     this.writer.write(this.name);
/*     */   }
/*     */ 
/*     */   private void finishOpenTag() throws IOException {
/*  81 */     if (!this.openTagDone) {
/*  82 */       this.writer.append('>');
/*  83 */       this.openTagDone = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addChildElement(XMLElement child)
/*     */     throws IOException
/*     */   {
/*  97 */     if (this.closed) {
/*  98 */       throw new IOException(String.format("Element %s already closed.", new Object[] { this.name }));
/*     */     }
/* 100 */     finishOpenTag();
/* 101 */     if (this.lastchild != null) {
/* 102 */       this.lastchild.close();
/*     */     }
/* 104 */     child.beginOpenTag();
/* 105 */     this.lastchild = child;
/*     */   }
/*     */ 
/*     */   private void quote(String text) throws IOException {
/* 109 */     int len = text.length();
/* 110 */     for (int i = 0; i < len; ++i) {
/* 111 */       char c = text.charAt(i);
/* 112 */       switch (c)
/*     */       {
/*     */       case '<':
/* 114 */         this.writer.write("&lt;");
/* 115 */         break;
/*     */       case '>':
/* 117 */         this.writer.write("&gt;");
/* 118 */         break;
/*     */       case '"':
/* 120 */         this.writer.write("&quot;");
/* 121 */         break;
/*     */       case '&':
/* 123 */         this.writer.write("&amp;");
/* 124 */         break;
/*     */       default:
/* 126 */         this.writer.write(c);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public XMLElement attr(String name, String value)
/*     */     throws IOException
/*     */   {
/* 149 */     if (value == null) {
/* 150 */       return this;
/*     */     }
/* 152 */     if ((this.closed) || (this.openTagDone)) {
/* 153 */       throw new IOException(String.format("Element %s already closed.", new Object[] { this.name }));
/*     */     }
/*     */ 
/* 156 */     this.writer.write(32);
/* 157 */     this.writer.write(name);
/* 158 */     this.writer.write(61);
/* 159 */     this.writer.write(34);
/* 160 */     quote(value);
/* 161 */     this.writer.write(34);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public XMLElement attr(String name, int value)
/*     */     throws IOException
/*     */   {
/* 181 */     return attr(name, String.valueOf(value));
/*     */   }
/*     */ 
/*     */   public XMLElement attr(String name, long value)
/*     */     throws IOException
/*     */   {
/* 200 */     return attr(name, String.valueOf(value));
/*     */   }
/*     */ 
/*     */   public XMLElement text(String text)
/*     */     throws IOException
/*     */   {
/* 213 */     if (this.closed) {
/* 214 */       throw new IOException(String.format("Element %s already closed.", new Object[] { this.name }));
/*     */     }
/* 216 */     finishOpenTag();
/* 217 */     if (this.lastchild != null) {
/* 218 */       this.lastchild.close();
/*     */     }
/* 220 */     quote(text);
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */   public XMLElement element(String name)
/*     */     throws IOException
/*     */   {
/* 234 */     XMLElement element = new XMLElement(this.writer, name);
/* 235 */     addChildElement(element);
/* 236 */     return element;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 246 */     if (!this.closed) {
/* 247 */       if (this.lastchild != null) {
/* 248 */         this.lastchild.close();
/*     */       }
/* 250 */       if (this.openTagDone) {
/* 251 */         this.writer.write(60);
/* 252 */         this.writer.write(47);
/* 253 */         this.writer.write(this.name);
/*     */       } else {
/* 255 */         this.writer.write(47);
/*     */       }
/* 257 */       this.writer.write(62);
/* 258 */       this.closed = true;
/* 259 */       this.openTagDone = true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.xml.XMLElement
 * JD-Core Version:    0.5.4
 */