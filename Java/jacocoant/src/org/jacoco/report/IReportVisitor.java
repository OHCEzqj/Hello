package org.jacoco.report;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.SessionInfo;

public abstract interface IReportVisitor extends IReportGroupVisitor
{
  public abstract void visitInfo(List<SessionInfo> paramList, Collection<ExecutionData> paramCollection)
    throws IOException;

  public abstract void visitEnd()
    throws IOException;
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.IReportVisitor
 * JD-Core Version:    0.5.4
 */