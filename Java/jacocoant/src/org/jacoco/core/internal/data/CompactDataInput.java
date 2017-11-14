/*    */ package org.jacoco.core.internal.data;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class CompactDataInput extends DataInputStream
/*    */ {
/*    */   public CompactDataInput(InputStream in)
/*    */   {
/* 33 */     super(in);
/*    */   }
/*    */ 
/*    */   public int readVarInt()
/*    */     throws IOException
/*    */   {
/* 44 */     int value = 0xFF & readByte();
/* 45 */     if ((value & 0x80) == 0) {
/* 46 */       return value;
/*    */     }
/* 48 */     return value & 0x7F | readVarInt() << 7;
/*    */   }
/*    */ 
/*    */   public boolean[] readBooleanArray()
/*    */     throws IOException
/*    */   {
/* 59 */     boolean[] value = new boolean[readVarInt()];
/* 60 */     int buffer = 0;
/* 61 */     for (int i = 0; i < value.length; ++i) {
/* 62 */       if (i % 8 == 0) {
/* 63 */         buffer = readByte();
/*    */       }
/* 65 */       value[i] = (((buffer & 0x1) != 0) ? 1 : false);
/* 66 */       buffer >>>= 1;
/*    */     }
/* 68 */     return value;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.data.CompactDataInput
 * JD-Core Version:    0.5.4
 */