package org.jacoco.report;

public abstract interface ILanguageNames
{
  public abstract String getPackageName(String paramString);

  public abstract String getClassName(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString);

  public abstract String getQualifiedClassName(String paramString);

  public abstract String getMethodName(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract String getQualifiedMethodName(String paramString1, String paramString2, String paramString3, String paramString4);
}

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.report.ILanguageNames
 * JD-Core Version:    0.5.4
 */