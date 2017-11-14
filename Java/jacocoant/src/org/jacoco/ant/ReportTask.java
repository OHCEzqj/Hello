/*     */ package org.jacoco.ant;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.tools.ant.BuildException;
/*     */ import org.apache.tools.ant.Project;
/*     */ import org.apache.tools.ant.Task;
/*     */ import org.apache.tools.ant.types.Resource;
/*     */ import org.apache.tools.ant.types.resources.FileResource;
/*     */ import org.apache.tools.ant.types.resources.Union;
/*     */ import org.apache.tools.ant.util.FileUtils;
/*     */ import org.jacoco.core.analysis.Analyzer;
/*     */ import org.jacoco.core.analysis.CoverageBuilder;
/*     */ import org.jacoco.core.analysis.IBundleCoverage;
/*     */ import org.jacoco.core.analysis.IClassCoverage;
/*     */ import org.jacoco.core.analysis.ICounter;
/*     */ import org.jacoco.core.analysis.ICoverageNode;
/*     */ import org.jacoco.core.data.ExecutionDataStore;
/*     */ import org.jacoco.core.data.SessionInfoStore;
/*     */ import org.jacoco.core.tools.ExecFileLoader;
/*     */ import org.jacoco.report.FileMultiReportOutput;
/*     */ import org.jacoco.report.IMultiReportOutput;
/*     */ import org.jacoco.report.IReportGroupVisitor;
/*     */ import org.jacoco.report.IReportVisitor;
/*     */ import org.jacoco.report.MultiReportVisitor;
/*     */ import org.jacoco.report.ZipMultiReportOutput;
/*     */ import org.jacoco.report.check.IViolationsOutput;
/*     */ import org.jacoco.report.check.Limit;
/*     */ import org.jacoco.report.check.Rule;
/*     */ import org.jacoco.report.check.RulesChecker;
/*     */ import org.jacoco.report.csv.CSVFormatter;
/*     */ import org.jacoco.report.html.HTMLFormatter;
/*     */ import org.jacoco.report.xml.XMLFormatter;
/*     */ 
/*     */ public class ReportTask extends Task
/*     */ {
/*     */   private final Union executiondataElement;
/*     */   private SessionInfoStore sessionInfoStore;
/*     */   private ExecutionDataStore executionDataStore;
/*     */   private final GroupElement structure;
/*     */   private final List<FormatterElement> formatters;
/*     */ 
/*     */   public ReportTask()
/*     */   {
/* 416 */     this.executiondataElement = new Union();
/*     */ 
/* 422 */     this.structure = new GroupElement();
/*     */ 
/* 424 */     this.formatters = new ArrayList();
/*     */   }
/*     */ 
/*     */   public Union createExecutiondata()
/*     */   {
/* 432 */     return this.executiondataElement;
/*     */   }
/*     */ 
/*     */   public GroupElement createStructure()
/*     */   {
/* 441 */     return this.structure;
/*     */   }
/*     */ 
/*     */   public HTMLFormatterElement createHtml()
/*     */   {
/* 450 */     HTMLFormatterElement element = new HTMLFormatterElement();
/* 451 */     this.formatters.add(element);
/* 452 */     return element;
/*     */   }
/*     */ 
/*     */   public CSVFormatterElement createCsv()
/*     */   {
/* 461 */     CSVFormatterElement element = new CSVFormatterElement();
/* 462 */     this.formatters.add(element);
/* 463 */     return element;
/*     */   }
/*     */ 
/*     */   public CheckFormatterElement createCheck()
/*     */   {
/* 472 */     CheckFormatterElement element = new CheckFormatterElement();
/* 473 */     this.formatters.add(element);
/* 474 */     return element;
/*     */   }
/*     */ 
/*     */   public XMLFormatterElement createXml()
/*     */   {
/* 483 */     XMLFormatterElement element = new XMLFormatterElement();
/* 484 */     this.formatters.add(element);
/* 485 */     return element;
/*     */   }
/*     */ 
/*     */   public void execute() throws BuildException
/*     */   {
/* 490 */     loadExecutionData();
/*     */     try {
/* 492 */       IReportVisitor visitor = createVisitor();
/* 493 */       visitor.visitInfo(this.sessionInfoStore.getInfos(), this.executionDataStore.getContents());
/*     */ 
/* 495 */       createReport(visitor, this.structure);
/* 496 */       visitor.visitEnd();
/* 497 */       for (FormatterElement f : this.formatters)
/* 498 */         f.finish();
/*     */     }
/*     */     catch (IOException e) {
/* 501 */       throw new BuildException("Error while creating report", e, getLocation());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadExecutionData()
/*     */   {
/* 507 */     ExecFileLoader loader = new ExecFileLoader();
/* 508 */     for (Iterator i = this.executiondataElement.iterator(); i.hasNext(); ) {
/* 509 */       Resource resource = (Resource)i.next();
/* 510 */       log(String.format("Loading execution data file %s", new Object[] { resource }));
/* 511 */       InputStream in = null;
/*     */       try {
/* 513 */         in = resource.getInputStream();
/* 514 */         loader.load(in);
/*     */       } catch (IOException e) {
/* 516 */         throw new BuildException(String.format("Unable to read execution data file %s", new Object[1]), e, getLocation());
/*     */       }
/*     */       finally
/*     */       {
/* 520 */         FileUtils.close(in);
/*     */       }
/*     */     }
/* 523 */     this.sessionInfoStore = loader.getSessionInfoStore();
/* 524 */     this.executionDataStore = loader.getExecutionDataStore();
/*     */   }
/*     */ 
/*     */   private IReportVisitor createVisitor() throws IOException {
/* 528 */     List visitors = new ArrayList();
/* 529 */     for (FormatterElement f : this.formatters) {
/* 530 */       visitors.add(f.createVisitor());
/*     */     }
/* 532 */     return new MultiReportVisitor(visitors);
/*     */   }
/*     */ 
/*     */   private void createReport(IReportGroupVisitor visitor, GroupElement group) throws IOException
/*     */   {
/* 537 */     if (group.name == null)
/* 538 */       throw new BuildException("Group name must be supplied", getLocation());
/*     */     IReportGroupVisitor groupVisitor;
/* 541 */     if (group.children.isEmpty()) {
/* 542 */       IBundleCoverage bundle = createBundle(group);
/* 543 */       SourceFilesElement sourcefiles = group.sourcefiles;
/* 544 */       AntResourcesLocator locator = new AntResourcesLocator(sourcefiles.encoding, sourcefiles.tabWidth);
/*     */ 
/* 546 */       locator.addAll(sourcefiles.iterator());
/* 547 */       if (!locator.isEmpty()) {
/* 548 */         checkForMissingDebugInformation(bundle);
/*     */       }
/* 550 */       visitor.visitBundle(bundle, locator);
/*     */     } else {
/* 552 */       groupVisitor = visitor.visitGroup(group.name);
/*     */ 
/* 554 */       for (GroupElement child : group.children)
/* 555 */         createReport(groupVisitor, child);
/*     */     }
/*     */   }
/*     */ 
/*     */   private IBundleCoverage createBundle(GroupElement group)
/*     */     throws IOException
/*     */   {
/* 562 */     CoverageBuilder builder = new CoverageBuilder();
/* 563 */     Analyzer analyzer = new Analyzer(this.executionDataStore, builder);
/* 564 */     for (Iterator i = group.classfiles.iterator(); i.hasNext(); ) {
/* 565 */       Resource resource = (Resource)i.next();
/* 566 */       if ((resource.isDirectory()) && (resource instanceof FileResource)) {
/* 567 */         analyzer.analyzeAll(((FileResource)resource).getFile());
/*     */       } else {
/* 569 */         InputStream in = resource.getInputStream();
/* 570 */         analyzer.analyzeAll(in, resource.getName());
/* 571 */         in.close();
/*     */       }
/*     */     }
/* 574 */     IBundleCoverage bundle = builder.getBundle(group.name);
/* 575 */     logBundleInfo(bundle, builder.getNoMatchClasses());
/* 576 */     return bundle;
/*     */   }
/*     */ 
/*     */   private void logBundleInfo(IBundleCoverage bundle, Collection<IClassCoverage> nomatch)
/*     */   {
/* 581 */     log(String.format("Writing bundle '%s' with %s classes", new Object[] { bundle.getName(), Integer.valueOf(bundle.getClassCounter().getTotalCount()) }));
/*     */ 
/* 583 */     if (!nomatch.isEmpty()) {
/* 584 */       log(String.format("Classes in bundle '%s' do no match with execution data. For report generation the same class files must be used as at runtime.", new Object[] { bundle.getName() }), 1);
/*     */ 
/* 588 */       for (IClassCoverage c : nomatch)
/* 589 */         log(String.format("Execution data for class %s does not match.", new Object[] { c.getName() }), 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkForMissingDebugInformation(ICoverageNode node)
/*     */   {
/* 596 */     if ((node.getClassCounter().getTotalCount() <= 0) || (node.getLineCounter().getTotalCount() != 0))
/*     */       return;
/* 598 */     log(String.format("To enable source code annotation class files for bundle '%s' have to be compiled with debug information.", new Object[] { node.getName() }), 1);
/*     */   }
/*     */ 
/*     */   static Locale parseLocale(String locale)
/*     */   {
/* 614 */     StringTokenizer st = new StringTokenizer(locale, "_");
/* 615 */     String language = (st.hasMoreTokens()) ? st.nextToken() : "";
/* 616 */     String country = (st.hasMoreTokens()) ? st.nextToken() : "";
/* 617 */     String variant = (st.hasMoreTokens()) ? st.nextToken() : "";
/* 618 */     return new Locale(language, country, variant);
/*     */   }
/*     */ 
/*     */   public class CheckFormatterElement extends ReportTask.FormatterElement
/*     */     implements IViolationsOutput
/*     */   {
/*     */     private final List<Rule> rules;
/*     */     private boolean violations;
/*     */     private boolean failOnViolation;
/*     */     private String violationsPropery;
/*     */ 
/*     */     public CheckFormatterElement()
/*     */     {
/* 347 */       super(ReportTask.this, null);
/*     */ 
/* 350 */       this.rules = new ArrayList();
/* 351 */       this.violations = false;
/* 352 */       this.failOnViolation = true;
/* 353 */       this.violationsPropery = null;
/*     */     }
/*     */ 
/*     */     public Rule createRule()
/*     */     {
/* 361 */       Rule rule = new Rule();
/* 362 */       this.rules.add(rule);
/* 363 */       return rule;
/*     */     }
/*     */ 
/*     */     public void setFailOnViolation(boolean flag)
/*     */     {
/* 374 */       this.failOnViolation = flag;
/*     */     }
/*     */ 
/*     */     public void setViolationsProperty(String property)
/*     */     {
/* 384 */       this.violationsPropery = property;
/*     */     }
/*     */ 
/*     */     public IReportVisitor createVisitor() throws IOException
/*     */     {
/* 389 */       RulesChecker formatter = new RulesChecker();
/* 390 */       formatter.setRules(this.rules);
/* 391 */       return formatter.createVisitor(this);
/*     */     }
/*     */ 
/*     */     public void onViolation(ICoverageNode node, Rule rule, Limit limit, String message)
/*     */     {
/* 396 */       ReportTask.this.log(message, 0);
/* 397 */       this.violations = true;
/* 398 */       if (this.violationsPropery != null) {
/* 399 */         String old = ReportTask.this.getProject().getProperty(this.violationsPropery);
/* 400 */         String value = (old == null) ? message : String.format("%s\n%s", new Object[] { old, message });
/*     */ 
/* 402 */         ReportTask.this.getProject().setProperty(this.violationsPropery, value);
/*     */       }
/*     */     }
/*     */ 
/*     */     void finish()
/*     */     {
/* 408 */       if ((this.violations) && (this.failOnViolation))
/* 409 */         throw new BuildException("Coverage check failed due to violated rules.", ReportTask.this.getLocation());
/*     */     }
/*     */   }
/*     */ 
/*     */   public class XMLFormatterElement extends ReportTask.FormatterElement
/*     */   {
/*     */     private File destfile;
/*     */     private String encoding;
/*     */ 
/*     */     public XMLFormatterElement()
/*     */     {
/* 304 */       super(ReportTask.this, null);
/*     */ 
/* 308 */       this.encoding = "UTF-8";
/*     */     }
/*     */ 
/*     */     public void setDestfile(File destfile)
/*     */     {
/* 317 */       this.destfile = destfile;
/*     */     }
/*     */ 
/*     */     public void setEncoding(String encoding)
/*     */     {
/* 327 */       this.encoding = encoding;
/*     */     }
/*     */ 
/*     */     public IReportVisitor createVisitor() throws IOException
/*     */     {
/* 332 */       if (this.destfile == null) {
/* 333 */         throw new BuildException("Destination file must be supplied for xml report", ReportTask.this.getLocation());
/*     */       }
/*     */ 
/* 337 */       XMLFormatter formatter = new XMLFormatter();
/* 338 */       formatter.setOutputEncoding(this.encoding);
/* 339 */       return formatter.createVisitor(new FileOutputStream(this.destfile));
/*     */     }
/*     */   }
/*     */ 
/*     */   public class CSVFormatterElement extends ReportTask.FormatterElement
/*     */   {
/*     */     private File destfile;
/*     */     private String encoding;
/*     */ 
/*     */     public CSVFormatterElement()
/*     */     {
/* 261 */       super(ReportTask.this, null);
/*     */ 
/* 265 */       this.encoding = "UTF-8";
/*     */     }
/*     */ 
/*     */     public void setDestfile(File destfile)
/*     */     {
/* 274 */       this.destfile = destfile;
/*     */     }
/*     */ 
/*     */     public IReportVisitor createVisitor() throws IOException
/*     */     {
/* 279 */       if (this.destfile == null) {
/* 280 */         throw new BuildException("Destination file must be supplied for csv report", ReportTask.this.getLocation());
/*     */       }
/*     */ 
/* 284 */       CSVFormatter formatter = new CSVFormatter();
/* 285 */       formatter.setOutputEncoding(this.encoding);
/* 286 */       return formatter.createVisitor(new FileOutputStream(this.destfile));
/*     */     }
/*     */ 
/*     */     public void setEncoding(String encoding)
/*     */     {
/* 296 */       this.encoding = encoding;
/*     */     }
/*     */   }
/*     */ 
/*     */   public class HTMLFormatterElement extends ReportTask.FormatterElement
/*     */   {
/*     */     private File destdir;
/*     */     private File destfile;
/*     */     private String footer;
/*     */     private String encoding;
/*     */     private Locale locale;
/*     */ 
/*     */     public HTMLFormatterElement()
/*     */     {
/* 165 */       super(ReportTask.this, null);
/*     */ 
/* 171 */       this.footer = "";
/*     */ 
/* 173 */       this.encoding = "UTF-8";
/*     */ 
/* 175 */       this.locale = Locale.getDefault();
/*     */     }
/*     */ 
/*     */     public void setDestdir(File destdir)
/*     */     {
/* 184 */       this.destdir = destdir;
/*     */     }
/*     */ 
/*     */     public void setDestfile(File destfile)
/*     */     {
/* 194 */       this.destfile = destfile;
/*     */     }
/*     */ 
/*     */     public void setFooter(String text)
/*     */     {
/* 205 */       this.footer = text;
/*     */     }
/*     */ 
/*     */     public void setEncoding(String encoding)
/*     */     {
/* 215 */       this.encoding = encoding;
/*     */     }
/*     */ 
/*     */     public void setLocale(String locale)
/*     */     {
/* 226 */       this.locale = ReportTask.parseLocale(locale);
/*     */     }
/*     */ 
/*     */     public IReportVisitor createVisitor()
/*     */       throws IOException
/*     */     {
/*     */       IMultiReportOutput output;
/*     */       IMultiReportOutput output;
/* 232 */       if (this.destfile != null) {
/* 233 */         if (this.destdir != null) {
/* 234 */           throw new BuildException("Either destination directory or file must be supplied, not both", ReportTask.this.getLocation());
/*     */         }
/*     */ 
/* 238 */         FileOutputStream stream = new FileOutputStream(this.destfile);
/* 239 */         output = new ZipMultiReportOutput(stream);
/*     */       }
/*     */       else {
/* 242 */         if (this.destdir == null) {
/* 243 */           throw new BuildException("Destination directory or file must be supplied for html report", ReportTask.this.getLocation());
/*     */         }
/*     */ 
/* 247 */         output = new FileMultiReportOutput(this.destdir);
/*     */       }
/* 249 */       HTMLFormatter formatter = new HTMLFormatter();
/* 250 */       formatter.setFooterText(this.footer);
/* 251 */       formatter.setOutputEncoding(this.encoding);
/* 252 */       formatter.setLocale(this.locale);
/* 253 */       return formatter.createVisitor(output);
/*     */     }
/*     */   }
/*     */ 
/*     */   private abstract class FormatterElement
/*     */   {
/*     */     private FormatterElement()
/*     */     {
/*     */     }
/*     */ 
/*     */     abstract IReportVisitor createVisitor()
/*     */       throws IOException;
/*     */ 
/*     */     void finish()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class GroupElement
/*     */   {
/*     */     private final List<GroupElement> children;
/*     */     private final Union classfiles;
/*     */     private final ReportTask.SourceFilesElement sourcefiles;
/*     */     private String name;
/*     */ 
/*     */     public GroupElement()
/*     */     {
/* 102 */       this.children = new ArrayList();
/*     */ 
/* 104 */       this.classfiles = new Union();
/*     */ 
/* 106 */       this.sourcefiles = new ReportTask.SourceFilesElement();
/*     */     }
/*     */ 
/*     */     public void setName(String name)
/*     */     {
/* 117 */       this.name = name;
/*     */     }
/*     */ 
/*     */     public GroupElement createGroup()
/*     */     {
/* 126 */       GroupElement group = new GroupElement();
/* 127 */       this.children.add(group);
/* 128 */       return group;
/*     */     }
/*     */ 
/*     */     public Union createClassfiles()
/*     */     {
/* 137 */       return this.classfiles;
/*     */     }
/*     */ 
/*     */     public ReportTask.SourceFilesElement createSourcefiles()
/*     */     {
/* 146 */       return this.sourcefiles;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class SourceFilesElement extends Union
/*     */   {
/*     */     String encoding;
/*     */     int tabWidth;
/*     */ 
/*     */     public SourceFilesElement()
/*     */     {
/*  67 */       this.encoding = null;
/*     */ 
/*  69 */       this.tabWidth = 4;
/*     */     }
/*     */ 
/*     */     public void setEncoding(String encoding)
/*     */     {
/*  79 */       this.encoding = encoding;
/*     */     }
/*     */ 
/*     */     public void setTabwidth(int tabWidth)
/*     */     {
/*  89 */       if (tabWidth <= 0) {
/*  90 */         throw new BuildException("Tab width must be greater than 0");
/*     */       }
/*  92 */       this.tabWidth = tabWidth;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\zqj\Desktop\jacocoant.jar
 * Qualified Name:     org.jacoco.ant.ReportTask
 * JD-Core Version:    0.5.4
 */