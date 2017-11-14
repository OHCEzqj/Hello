package org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr;

import org.jacoco.agent.rt.internal_8ff85ea.asm.ClassVisitor;
import org.jacoco.agent.rt.internal_8ff85ea.asm.MethodVisitor;

public abstract interface IProbeArrayStrategy
{
  public abstract int storeInstance(MethodVisitor paramMethodVisitor, boolean paramBoolean, int paramInt);

  public abstract void addMembers(ClassVisitor paramClassVisitor, int paramInt);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.src\jacocoagent.jar
 * Qualified Name:     org.jacoco.agent.rt.internal_8ff85ea.core.internal.instr.IProbeArrayStrategy
 * JD-Core Version:    0.5.4
 */