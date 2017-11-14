package org.jacoco.report;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface IMultiReportOutput
{
  public abstract OutputStream createFile(String paramString)
    throws IOException;

  public abstract void close()
    throws IOException;
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.IMultiReportOutput
 * JD-Core Version:    0.5.4
 */