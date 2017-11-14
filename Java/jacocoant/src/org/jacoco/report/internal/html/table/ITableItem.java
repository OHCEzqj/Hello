package org.jacoco.report.internal.html.table;

import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.report.internal.html.ILinkable;

public abstract interface ITableItem extends ILinkable
{
  public abstract ICoverageNode getNode();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.ITableItem
 * JD-Core Version:    0.5.4
 */