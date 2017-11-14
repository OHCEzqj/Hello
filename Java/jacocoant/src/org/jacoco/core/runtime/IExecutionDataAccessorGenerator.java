package org.jacoco.core.runtime;

import org.jacoco.asm.MethodVisitor;

public abstract interface IExecutionDataAccessorGenerator
{
  public abstract int generateDataAccessor(long paramLong, String paramString, int paramInt, MethodVisitor paramMethodVisitor);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.runtime.IExecutionDataAccessorGenerator
 * JD-Core Version:    0.5.4
 */