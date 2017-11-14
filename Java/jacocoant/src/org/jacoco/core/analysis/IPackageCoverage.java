package org.jacoco.core.analysis;

import java.util.Collection;

public abstract interface IPackageCoverage extends ICoverageNode
{
  public abstract Collection<IClassCoverage> getClasses();

  public abstract Collection<ISourceFileCoverage> getSourceFiles();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.core.analysis.IPackageCoverage
 * JD-Core Version:    0.5.4
 */