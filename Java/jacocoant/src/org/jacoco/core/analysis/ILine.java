package org.jacoco.core.analysis;

public abstract interface ILine
{
  public abstract ICounter getInstructionCounter();

  public abstract ICounter getBranchCounter();

  public abstract int getStatus();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.ILine
 * JD-Core Version:    0.5.4
 */