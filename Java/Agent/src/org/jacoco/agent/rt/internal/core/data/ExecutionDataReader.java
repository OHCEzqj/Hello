/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.data;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.data.CompactDataInput;
/*     */ 
/*     */ public class ExecutionDataReader
/*     */ {
/*     */   protected final CompactDataInput in;
/*  29 */   private ISessionInfoVisitor sessionInfoVisitor = null;
/*     */ 
/*  31 */   private IExecutionDataVisitor executionDataVisitor = null;
/*     */ 
/*  33 */   private boolean firstBlock = true;
/*     */ 
/*     */   public ExecutionDataReader(InputStream input)
/*     */   {
/*  44 */     this.in = new CompactDataInput(input);
/*     */   }
/*     */ 
/*     */   public void setSessionInfoVisitor(ISessionInfoVisitor visitor)
/*     */   {
/*  54 */     this.sessionInfoVisitor = visitor;
/*     */   }
/*     */ 
/*     */   public void setExecutionDataVisitor(IExecutionDataVisitor visitor)
/*     */   {
/*  64 */     this.executionDataVisitor = visitor;
/*     */   }
/*     */ 
/*     */   public boolean read()
/*     */     throws IOException, IncompatibleExecDataVersionException
/*     */   {
/*     */     byte type;
/*     */     do
/*     */     {
/*  83 */       int i = this.in.read();
/*  84 */       if (i == -1) {
/*  85 */         return false;
/*     */       }
/*  87 */       type = (byte)i;
/*  88 */       if ((this.firstBlock) && (type != 1)) {
/*  89 */         throw new IOException("Invalid execution data file.");
/*     */       }
/*  91 */       this.firstBlock = false;
/*  92 */     }while (readBlock(type));
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean readBlock(byte blocktype)
/*     */     throws IOException
/*     */   {
/* 107 */     switch (blocktype)
/*     */     {
/*     */     case 1:
/* 109 */       readHeader();
/* 110 */       return true;
/*     */     case 16:
/* 112 */       readSessionInfo();
/* 113 */       return true;
/*     */     case 17:
/* 115 */       readExecutionData();
/* 116 */       return true;
/*     */     }
/* 118 */     throw new IOException(String.format("Unknown block type %x.", new Object[] { Byte.valueOf(blocktype) }));
/*     */   }
/*     */ 
/*     */   private void readHeader()
/*     */     throws IOException
/*     */   {
/* 124 */     if (this.in.readChar() != 49344) {
/* 125 */       throw new IOException("Invalid execution data file.");
/*     */     }
/* 127 */     char version = this.in.readChar();
/* 128 */     if (version != ExecutionDataWriter.FORMAT_VERSION)
/* 129 */       throw new IncompatibleExecDataVersionException(version);
/*     */   }
/*     */ 
/*     */   private void readSessionInfo() throws IOException
/*     */   {
/* 134 */     if (this.sessionInfoVisitor == null) {
/* 135 */       throw new IOException("No session info visitor.");
/*     */     }
/* 137 */     String id = this.in.readUTF();
/* 138 */     long start = this.in.readLong();
/* 139 */     long dump = this.in.readLong();
/* 140 */     this.sessionInfoVisitor.visitSessionInfo(new SessionInfo(id, start, dump));
/*     */   }
/*     */ 
/*     */   private void readExecutionData() throws IOException {
/* 144 */     if (this.executionDataVisitor == null) {
/* 145 */       throw new IOException("No execution data visitor.");
/*     */     }
/* 147 */     long id = this.in.readLong();
/* 148 */     String name = this.in.readUTF();
/* 149 */     boolean[] probes = this.in.readBooleanArray();
/* 150 */     this.executionDataVisitor.visitClassExecution(new ExecutionData(id, name, probes));
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.data.ExecutionDataReader
 * JD-Core Version:    0.5.4
 */