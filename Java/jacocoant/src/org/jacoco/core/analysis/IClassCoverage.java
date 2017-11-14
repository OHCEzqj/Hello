package org.jacoco.core.analysis;

import java.util.Collection;

public abstract interface IClassCoverage extends ISourceNode
{
  public abstract long getId();

  public abstract boolean isNoMatch();

  public abstract String getSignature();

  public abstract String getSuperName();

  public abstract String[] getInterfaceNames();

  public abstract String getPackageName();

  public abstract String getSourceFileName();

  public abstract Collection<IMethodCoverage> getMethods();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.IClassCoverage
 * JD-Core Version:    0.5.4
 */