package org.jacoco.report.check;

import org.jacoco.core.analysis.ICoverageNode;

public abstract interface IViolationsOutput
{
  public abstract void onViolation(ICoverageNode paramICoverageNode, Rule paramRule, Limit paramLimit, String paramString);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.check.IViolationsOutput
 * JD-Core Version:    0.5.4
 */