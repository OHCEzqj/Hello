package org.jacoco.report;

import java.io.IOException;
import org.jacoco.core.analysis.IBundleCoverage;

public abstract interface IReportGroupVisitor
{
  public abstract void visitBundle(IBundleCoverage paramIBundleCoverage, ISourceFileLocator paramISourceFileLocator)
    throws IOException;

  public abstract IReportGroupVisitor visitGroup(String paramString)
    throws IOException;
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.IReportGroupVisitor
 * JD-Core Version:    0.5.4
 */