package org.jacoco.core.internal.flow;

import org.jacoco.asm.MethodVisitor;

public abstract interface IFrame
{
  public abstract void accept(MethodVisitor paramMethodVisitor);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.internal.flow.IFrame
 * JD-Core Version:    0.5.4
 */