package org.jacoco.core.analysis;

import java.util.Collection;

public abstract interface IBundleCoverage extends ICoverageNode
{
  public abstract Collection<IPackageCoverage> getPackages();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.IBundleCoverage
 * JD-Core Version:    0.5.4
 */