/*     */ package org.jacoco.report.csv;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ class DelimitedWriter
/*     */ {
/*     */   private static final String QUOTE = "\"";
/*     */   private static final String ESCAPED_QUOTE = "\"\"";
/*     */   private static final char DEFAULT_DELIMITER = ',';
/*  39 */   private static final String NEW_LINE = System.getProperty("line.separator");
/*     */   private final char delimiter;
/*     */   private final Writer delegate;
/*  42 */   private int fieldPosition = 0;
/*     */ 
/*     */   public DelimitedWriter(Writer delegate)
/*     */   {
/*  51 */     this(delegate, ',');
/*     */   }
/*     */ 
/*     */   public DelimitedWriter(Writer delegate, char delimiter)
/*     */   {
/*  63 */     this.delegate = delegate;
/*  64 */     this.delimiter = delimiter;
/*     */   }
/*     */ 
/*     */   public void write(String[] fields)
/*     */     throws IOException
/*     */   {
/*  77 */     for (String field : fields)
/*  78 */       write(field);
/*     */   }
/*     */ 
/*     */   public void write(String field)
/*     */     throws IOException
/*     */   {
/*  93 */     if (this.fieldPosition != 0) {
/*  94 */       this.delegate.write(this.delimiter);
/*     */     }
/*  96 */     this.delegate.write(escape(field));
/*  97 */     this.fieldPosition += 1;
/*     */   }
/*     */ 
/*     */   public void write(int value)
/*     */     throws IOException
/*     */   {
/* 109 */     write(Integer.toString(value));
/*     */   }
/*     */ 
/*     */   public void write(int[] values)
/*     */     throws IOException
/*     */   {
/* 121 */     for (int value : values)
/* 122 */       write(Integer.toString(value));
/*     */   }
/*     */ 
/*     */   public void nextLine()
/*     */     throws IOException
/*     */   {
/* 134 */     this.delegate.write(NEW_LINE);
/* 135 */     this.fieldPosition = 0;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 146 */     this.delegate.close();
/*     */   }
/*     */ 
/*     */   private String escape(String value)
/*     */   {
/* 159 */     String escapedValue = value;
/*     */ 
/* 163 */     if ((value.indexOf("\"") != -1) || (value.indexOf(this.delimiter) != -1)) {
/* 164 */       escapedValue = value.replace("\"", "\"\"");
/* 165 */       escapedValue = "\"" + escapedValue + "\"";
/*     */     }
/*     */ 
/* 168 */     return escapedValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.csv.DelimitedWriter
 * JD-Core Version:    0.5.4
 */