/*     */ package org.jacoco.report.internal.html;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.jacoco.report.internal.ReportOutputFolder;
/*     */ import org.jacoco.report.internal.xml.XMLElement;
/*     */ 
/*     */ public class HTMLElement extends XMLElement
/*     */ {
/*     */   protected HTMLElement(Writer writer, String name)
/*     */   {
/*  35 */     super(writer, name);
/*     */   }
/*     */ 
/*     */   public HTMLElement element(String name) throws IOException
/*     */   {
/*  40 */     HTMLElement element = new HTMLElement(this.writer, name);
/*  41 */     addChildElement(element);
/*  42 */     return element;
/*     */   }
/*     */ 
/*     */   private void classattr(String classattr) throws IOException {
/*  46 */     attr("class", classattr);
/*     */   }
/*     */ 
/*     */   public HTMLElement meta(String httpequivattr, String contentattr)
/*     */     throws IOException
/*     */   {
/*  62 */     HTMLElement meta = element("meta");
/*  63 */     meta.attr("http-equiv", httpequivattr);
/*  64 */     meta.attr("content", contentattr);
/*  65 */     return meta;
/*     */   }
/*     */ 
/*     */   public HTMLElement link(String relattr, String hrefattr, String typeattr)
/*     */     throws IOException
/*     */   {
/*  83 */     HTMLElement link = element("link");
/*  84 */     link.attr("rel", relattr);
/*  85 */     link.attr("href", hrefattr);
/*  86 */     link.attr("type", typeattr);
/*  87 */     return link;
/*     */   }
/*     */ 
/*     */   public HTMLElement title()
/*     */     throws IOException
/*     */   {
/*  98 */     return element("title");
/*     */   }
/*     */ 
/*     */   public HTMLElement h1()
/*     */     throws IOException
/*     */   {
/* 109 */     return element("h1");
/*     */   }
/*     */ 
/*     */   public HTMLElement p()
/*     */     throws IOException
/*     */   {
/* 120 */     return element("p");
/*     */   }
/*     */ 
/*     */   public HTMLElement span()
/*     */     throws IOException
/*     */   {
/* 131 */     return element("span");
/*     */   }
/*     */ 
/*     */   public HTMLElement span(String classattr)
/*     */     throws IOException
/*     */   {
/* 144 */     HTMLElement span = span();
/* 145 */     span.classattr(classattr);
/* 146 */     return span;
/*     */   }
/*     */ 
/*     */   public HTMLElement span(String classattr, String idattr)
/*     */     throws IOException
/*     */   {
/* 162 */     HTMLElement span = span(classattr);
/* 163 */     span.attr("id", idattr);
/* 164 */     return span;
/*     */   }
/*     */ 
/*     */   public HTMLElement div(String classattr)
/*     */     throws IOException
/*     */   {
/* 177 */     HTMLElement div = element("div");
/* 178 */     div.classattr(classattr);
/* 179 */     return div;
/*     */   }
/*     */ 
/*     */   public HTMLElement code()
/*     */     throws IOException
/*     */   {
/* 190 */     return element("code");
/*     */   }
/*     */ 
/*     */   public HTMLElement pre(String classattr)
/*     */     throws IOException
/*     */   {
/* 203 */     HTMLElement pre = element("pre");
/* 204 */     pre.classattr(classattr);
/* 205 */     return pre;
/*     */   }
/*     */ 
/*     */   public HTMLElement a(String hrefattr)
/*     */     throws IOException
/*     */   {
/* 218 */     HTMLElement a = element("a");
/* 219 */     a.attr("href", hrefattr);
/* 220 */     return a;
/*     */   }
/*     */ 
/*     */   public HTMLElement a(String hrefattr, String classattr)
/*     */     throws IOException
/*     */   {
/* 236 */     HTMLElement a = a(hrefattr);
/* 237 */     a.classattr(classattr);
/* 238 */     return a;
/*     */   }
/*     */ 
/*     */   public HTMLElement a(ILinkable linkable, ReportOutputFolder base)
/*     */     throws IOException
/*     */   {
/* 255 */     String link = linkable.getLink(base);
/*     */     HTMLElement a;
/*     */     HTMLElement a;
/* 256 */     if (link == null)
/* 257 */       a = span(linkable.getLinkStyle());
/*     */     else {
/* 259 */       a = a(link, linkable.getLinkStyle());
/*     */     }
/* 261 */     a.text(linkable.getLinkLabel());
/* 262 */     return a;
/*     */   }
/*     */ 
/*     */   public HTMLElement table(String classattr)
/*     */     throws IOException
/*     */   {
/* 275 */     HTMLElement table = element("table");
/* 276 */     table.classattr(classattr);
/* 277 */     table.attr("cellspacing", "0");
/* 278 */     return table;
/*     */   }
/*     */ 
/*     */   public HTMLElement thead()
/*     */     throws IOException
/*     */   {
/* 289 */     return element("thead");
/*     */   }
/*     */ 
/*     */   public HTMLElement tfoot()
/*     */     throws IOException
/*     */   {
/* 300 */     return element("tfoot");
/*     */   }
/*     */ 
/*     */   public HTMLElement tbody()
/*     */     throws IOException
/*     */   {
/* 311 */     return element("tbody");
/*     */   }
/*     */ 
/*     */   public HTMLElement tr()
/*     */     throws IOException
/*     */   {
/* 322 */     return element("tr");
/*     */   }
/*     */ 
/*     */   public HTMLElement td()
/*     */     throws IOException
/*     */   {
/* 333 */     return element("td");
/*     */   }
/*     */ 
/*     */   public HTMLElement td(String classattr)
/*     */     throws IOException
/*     */   {
/* 346 */     HTMLElement td = td();
/* 347 */     td.classattr(classattr);
/* 348 */     return td;
/*     */   }
/*     */ 
/*     */   public void img(String srcattr, int widthattr, int heightattr, String titleattr)
/*     */     throws IOException
/*     */   {
/* 367 */     HTMLElement img = element("img");
/* 368 */     img.attr("src", srcattr);
/* 369 */     img.attr("width", widthattr);
/* 370 */     img.attr("height", heightattr);
/* 371 */     img.attr("title", titleattr);
/* 372 */     img.attr("alt", titleattr);
/* 373 */     img.close();
/*     */   }
/*     */ 
/*     */   public void script(String typeattr, String srcattr)
/*     */     throws IOException
/*     */   {
/* 388 */     HTMLElement script = element("script");
/* 389 */     script.attr("type", typeattr);
/* 390 */     script.attr("src", srcattr);
/*     */ 
/* 392 */     script.text("");
/* 393 */     script.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.HTMLElement
 * JD-Core Version:    0.5.4
 */