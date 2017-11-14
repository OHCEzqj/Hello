package org.jacoco.report.internal.html.table;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.HTMLElement;
import org.jacoco.report.internal.html.resources.Resources;

public abstract interface IColumnRenderer
{
  public abstract boolean init(List<? extends ITableItem> paramList, ICoverageNode paramICoverageNode);

  public abstract void footer(HTMLElement paramHTMLElement, ICoverageNode paramICoverageNode, Resources paramResources, ReportOutputFolder paramReportOutputFolder)
    throws IOException;

  public abstract void item(HTMLElement paramHTMLElement, ITableItem paramITableItem, Resources paramResources, ReportOutputFolder paramReportOutputFolder)
    throws IOException;

  public abstract Comparator<ITableItem> getComparator();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.table.IColumnRenderer
 * JD-Core Version:    0.5.4
 */