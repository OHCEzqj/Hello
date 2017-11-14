package org.jacoco.core.analysis;

public abstract interface ISourceNode extends ICoverageNode
{
  public static final int UNKNOWN_LINE = -1;

  public abstract int getFirstLine();

  public abstract int getLastLine();

  public abstract ILine getLine(int paramInt);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.ISourceNode
 * JD-Core Version:    0.5.4
 */