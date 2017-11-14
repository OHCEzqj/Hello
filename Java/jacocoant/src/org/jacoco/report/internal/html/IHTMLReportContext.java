package org.jacoco.report.internal.html;

import java.util.Locale;
import org.jacoco.report.ILanguageNames;
import org.jacoco.report.internal.html.index.IIndexUpdate;
import org.jacoco.report.internal.html.resources.Resources;
import org.jacoco.report.internal.html.table.Table;

public abstract interface IHTMLReportContext
{
  public abstract Resources getResources();

  public abstract ILanguageNames getLanguageNames();

  public abstract Table getTable();

  public abstract String getFooterText();

  public abstract ILinkable getSessionsPage();

  public abstract String getOutputEncoding();

  public abstract IIndexUpdate getIndexUpdate();

  public abstract Locale getLocale();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.internal.html.IHTMLReportContext
 * JD-Core Version:    0.5.4
 */