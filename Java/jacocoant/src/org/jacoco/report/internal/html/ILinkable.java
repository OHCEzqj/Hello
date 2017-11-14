package org.jacoco.report.internal.html;

import org.jacoco.report.internal.ReportOutputFolder;

public abstract interface ILinkable
{
  public abstract String getLink(ReportOutputFolder paramReportOutputFolder);

  public abstract String getLinkLabel();

  public abstract String getLinkStyle();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.ILinkable
 * JD-Core Version:    0.5.4
 */