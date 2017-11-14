/*     */ package org.jacoco.agent.rt.internal_8ff85ea.core.runtime;
/*     */ 
/*     */ import java.lang.instrument.ClassFileTransformer;
/*     */ import java.lang.instrument.IllegalClassFormatException;
/*     */ import java.lang.instrument.Instrumentation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.ProtectionDomain;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassReader;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassWriter;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;
/*     */ import org.jacoco.agent.rt.internal_8ff85ea.core.internal.Java9Support;
/*     */ 
/*     */ public class ModifiedSystemClassRuntime extends AbstractRuntime
/*     */ {
/*     */   private static final String ACCESS_FIELD_TYPE = "Ljava/lang/Object;";
/*     */   private final Class<?> systemClass;
/*     */   private final String systemClassName;
/*     */   private final String accessFieldName;
/*     */ 
/*     */   public ModifiedSystemClassRuntime(Class<?> systemClass, String accessFieldName)
/*     */   {
/*  58 */     this.systemClass = systemClass;
/*  59 */     this.systemClassName = systemClass.getName().replace('.', '/');
/*  60 */     this.accessFieldName = accessFieldName;
/*     */   }
/*     */ 
/*     */   public void startup(RuntimeData data) throws Exception
/*     */   {
/*  65 */     super.startup(data);
/*  66 */     Field field = this.systemClass.getField(this.accessFieldName);
/*  67 */     field.set(null, data);
/*     */   }
/*     */ 
/*     */   public void shutdown()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int generateDataAccessor(long classid, String classname, int probecount, MethodVisitor mv)
/*     */   {
/*  77 */     mv.visitFieldInsn(178, this.systemClassName, this.accessFieldName, "Ljava/lang/Object;");
/*     */ 
/*  80 */     RuntimeData.generateAccessCall(classid, classname, probecount, mv);
/*     */ 
/*  82 */     return 6;
/*     */   }
/*     */ 
/*     */   public static IRuntime createFor(Instrumentation inst, String className)
/*     */     throws ClassNotFoundException
/*     */   {
/* 101 */     return createFor(inst, className, "$jacocoAccess");
/*     */   }
/*     */ 
/*     */   public static IRuntime createFor(Instrumentation inst, String className, String accessFieldName)
/*     */     throws ClassNotFoundException
/*     */   {
/* 123 */     ClassFileTransformer transformer = new ClassFileTransformer(className, accessFieldName)
/*     */     {
/*     */       public byte[] transform(ClassLoader loader, String name, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] source)
/*     */         throws IllegalClassFormatException
/*     */       {
/* 128 */         if (name.equals(this.val$className)) {
/* 129 */           return ModifiedSystemClassRuntime.instrument(source, this.val$accessFieldName);
/*     */         }
/* 131 */         return null;
/*     */       }
/*     */     };
/* 134 */     inst.addTransformer(transformer);
/* 135 */     Class clazz = Class.forName(className.replace('/', '.'));
/* 136 */     inst.removeTransformer(transformer);
/*     */     try {
/* 138 */       clazz.getField(accessFieldName);
/*     */     } catch (NoSuchFieldException e) {
/* 140 */       throw new RuntimeException(String.format("Class %s could not be instrumented.", new Object[] { className }), e);
/*     */     }
/*     */ 
/* 143 */     return new ModifiedSystemClassRuntime(clazz, accessFieldName);
/*     */   }
/*     */ 
/*     */   public static byte[] instrument(byte[] source, String accessFieldName)
/*     */   {
/* 157 */     ClassReader reader = new ClassReader(Java9Support.downgradeIfRequired(source));
/* 158 */     ClassWriter writer = new ClassWriter(reader, 0);
/* 159 */     reader.accept(new ClassVisitor(327680, writer, accessFieldName)
/*     */     {
/*     */       public void visitEnd()
/*     */       {
/* 163 */         ModifiedSystemClassRuntime.access$000(this.cv, this.val$accessFieldName);
/* 164 */         super.visitEnd();
/*     */       }
/*     */     }
/*     */     , 8);
/*     */ 
/* 168 */     return writer.toByteArray();
/*     */   }
/*     */ 
/*     */   private static void createDataField(ClassVisitor visitor, String dataField)
/*     */   {
/* 173 */     visitor.visitField(4233, dataField, "Ljava/lang/Object;", null, null);
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.runtime.ModifiedSystemClassRuntime
 * JD-Core Version:    0.5.4
 */