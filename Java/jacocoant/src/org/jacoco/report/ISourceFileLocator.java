package org.jacoco.report;

import java.io.IOException;
import java.io.Reader;

public abstract interface ISourceFileLocator
{
  public abstract Reader getSourceFile(String paramString1, String paramString2)
    throws IOException;

  public abstract int getTabWidth();
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.ISourceFileLocator
 * JD-Core Version:    0.5.4
 */