/*    */ package org.jacoco.core.internal.instr;
/*    */ 
/*    */ import org.jacoco.asm.ClassReader;
/*    */ import org.jacoco.core.internal.data.CRC64;
/*    */ import org.jacoco.core.internal.flow.ClassProbesAdapter;
/*    */ import org.jacoco.core.runtime.IExecutionDataAccessorGenerator;
/*    */ 
/*    */ public final class ProbeArrayStrategyFactory
/*    */ {
/*    */   public static IProbeArrayStrategy createFor(ClassReader reader, IExecutionDataAccessorGenerator accessorGenerator)
/*    */   {
/* 43 */     String className = reader.getClassName();
/* 44 */     int version = getVersion(reader);
/* 45 */     long classId = CRC64.checksum(reader.b);
/* 46 */     boolean withFrames = version >= 50;
/*    */ 
/* 48 */     if (isInterface(reader)) {
/* 49 */       ProbeCounter counter = getProbeCounter(reader);
/* 50 */       if (counter.getCount() == 0) {
/* 51 */         return new NoneProbeArrayStrategy();
/*    */       }
/* 53 */       if ((version >= 52) && (counter.hasMethods())) {
/* 54 */         return new InterfaceFieldProbeArrayStrategy(className, classId, counter.getCount(), accessorGenerator);
/*    */       }
/*    */ 
/* 57 */       return new LocalProbeArrayStrategy(className, classId, counter.getCount(), accessorGenerator);
/*    */     }
/*    */ 
/* 61 */     return new ClassFieldProbeArrayStrategy(className, classId, withFrames, accessorGenerator);
/*    */   }
/*    */ 
/*    */   private static boolean isInterface(ClassReader reader)
/*    */   {
/* 67 */     return (reader.getAccess() & 0x200) != 0;
/*    */   }
/*    */ 
/*    */   private static int getVersion(ClassReader reader) {
/* 71 */     return reader.readShort(6);
/*    */   }
/*    */ 
/*    */   private static ProbeCounter getProbeCounter(ClassReader reader) {
/* 75 */     ProbeCounter counter = new ProbeCounter();
/* 76 */     reader.accept(new ClassProbesAdapter(counter, false), 0);
/* 77 */     return counter;
/*    */   }
/*    */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.ProbeArrayStrategyFactory
 * JD-Core Version:    0.5.4
 */