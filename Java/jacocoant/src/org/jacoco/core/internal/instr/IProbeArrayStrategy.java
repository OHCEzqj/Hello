package org.jacoco.core.internal.instr;

import org.jacoco.asm.ClassVisitor;
import org.jacoco.asm.MethodVisitor;

public abstract interface IProbeArrayStrategy
{
  public abstract int storeInstance(MethodVisitor paramMethodVisitor, boolean paramBoolean, int paramInt);

  public abstract void addMembers(ClassVisitor paramClassVisitor, int paramInt);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.instr.IProbeArrayStrategy
 * JD-Core Version:    0.5.4
 */