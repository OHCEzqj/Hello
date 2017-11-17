/*******************************************************************************
 * Copyright (c) 2009, 2017 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 *******************************************************************************/
package org.jacoco.report.html;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.SessionInfo;
import org.jacoco.report.ILanguageNames;
import org.jacoco.report.IMultiReportOutput;
import org.jacoco.report.IReportGroupVisitor;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.ISourceFileLocator;
import org.jacoco.report.JavaNames;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.HTMLGroupVisitor;
import org.jacoco.report.internal.html.IHTMLReportContext;
import org.jacoco.report.internal.html.ILinkable;
import org.jacoco.report.internal.html.index.ElementIndex;
import org.jacoco.report.internal.html.index.IIndexUpdate;
import org.jacoco.report.internal.html.page.BundlePage;
import org.jacoco.report.internal.html.page.ReportPage;
import org.jacoco.report.internal.html.page.SessionsPage;
import org.jacoco.report.internal.html.resources.Resources;
import org.jacoco.report.internal.html.resources.Styles;
import org.jacoco.report.internal.html.table.BarColumn;
import org.jacoco.report.internal.html.table.CounterColumn;
import org.jacoco.report.internal.html.table.LabelColumn;
import org.jacoco.report.internal.html.table.PercentageColumn;
import org.jacoco.report.internal.html.table.Table;

/**
 * Formatter for coverage reports in multiple HTML pages.
 */
public class HTMLFormatter implements IHTMLReportContext {

	private ILanguageNames languageNames = new JavaNames();

	private Locale locale = Locale.getDefault();

	private String footerText = "";

	private String outputEncoding = "UTF-8";

	private Resources resources;

	private ElementIndex index;

	private SessionsPage sessionsPage;

	private Table table;

	/**
	 * New instance with default settings.
	 */
	public HTMLFormatter() {
	}

	/**
	 * Sets the implementation for language name display. Java language names
	 * are defined by default.
	 * 
	 * @param languageNames
	 *            converter for language specific names
	 */
	public void setLanguageNames(final ILanguageNames languageNames) {
		this.languageNames = languageNames;
	}

	/**
	 * Sets the locale used for report rendering. The current default locale is
	 * used by default.
	 * 
	 * @param locale
	 *            locale used for report rendering
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	/**
	 * Sets the optional text that should be included in every footer page.
	 * 
	 * @param footerText
	 *            footer text
	 */
	public void setFooterText(final String footerText) {
		this.footerText = footerText;
	}

	/**
	 * Sets the encoding used for generated HTML pages. Default is UTF-8.
	 * 
	 * @param outputEncoding
	 *            HTML output encoding
	 */
	public void setOutputEncoding(final String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}

	// === IHTMLReportContext ===

	public ILanguageNames getLanguageNames() {
		System.out.println("HTMLFormatter getLanguageNames()");
		return languageNames;
	}

	public Resources getResources() {
		System.out.println("HTMLFormatter getResources()");
		return resources;
	}

	public Table getTable() {
		System.out.println("HTMLFormatter getTable()");
		if (table == null) {
			table = createTable();
		}
		return table;
	}

	private Table createTable() {
		final Table t = new Table();
		t.add("Element", null, new LabelColumn(), false);
		t.add("Missed Instructions", Styles.BAR, new BarColumn(CounterEntity.INSTRUCTION,
				locale), true);
		System.out.println("HTMLFormatter createTable Missed Instructions	"+CounterEntity.INSTRUCTION);
		
		
		t.add("Cov.", Styles.CTR2,
				new PercentageColumn(CounterEntity.INSTRUCTION, locale), false);

		t.add("Missed Branches", Styles.BAR, new BarColumn(CounterEntity.BRANCH, locale),
				false);
		System.out.println("HTMLFormatter createTable Missed Branches	"+CounterEntity.BRANCH);
		
		
		t.add("Cov.", Styles.CTR2, new PercentageColumn(CounterEntity.BRANCH, locale),
				false);

		addMissedTotalColumns(t, "Cxty", CounterEntity.COMPLEXITY);
		System.out.println("HTMLFormatter createTable Missed COMPLEXITY	"+CounterEntity.COMPLEXITY);
		

		addMissedTotalColumns(t, "Lines", CounterEntity.LINE);
		System.out.println("HTMLFormatter createTable Missed LINE	"+CounterEntity.LINE);
		

		addMissedTotalColumns(t, "Methods", CounterEntity.METHOD);
		System.out.println("HTMLFormatter createTable Missed METHOD	"+CounterEntity.METHOD);
		

		addMissedTotalColumns(t, "Classes", CounterEntity.CLASS);
		System.out.println("HTMLFormatter createTable Missed CLASS	"+CounterEntity.CLASS);
		return t;
	}

	private void addMissedTotalColumns(final Table table, final String label,
			final CounterEntity entity) {
		table.add("Missed", Styles.CTR1,
				CounterColumn.newMissed(entity, locale), false);
		table.add(label, Styles.CTR2, CounterColumn.newTotal(entity, locale),
				false);
	}

	public String getFooterText() {
		System.out.println("HTMLFormatter getFooterText()");
		return footerText;
	}

	public ILinkable getSessionsPage() {
		System.out.println("HTMLFormatter getSessionsPage()");
		return sessionsPage;
	}

	public String getOutputEncoding() {
		System.out.println("HTMLFormatter getOutputEncoding()");
		return outputEncoding;
	}

	public IIndexUpdate getIndexUpdate() {
		System.out.println("HTMLFormatter getIndexUpdate()");
		return index;
	}

	public Locale getLocale() {
		System.out.println("HTMLFormatter getLocale()");
		return locale;
	}

	/**
	 * Creates a new visitor to write a report to the given output.
	 * 
	 * @param output
	 *            output to write the report to
	 * @return visitor to emit the report data to
	 * @throws IOException
	 *             in case of problems with the output stream
	 */
	public IReportVisitor createVisitor(final IMultiReportOutput output)
			throws IOException {
		final ReportOutputFolder root = new ReportOutputFolder(output);
		resources = new Resources(root);
		resources.copyResources();
		index = new ElementIndex(root);
		return new IReportVisitor() {

			private List<SessionInfo> sessionInfos;
			private Collection<ExecutionData> executionData;

			private HTMLGroupVisitor groupHandler;

			public void visitInfo(final List<SessionInfo> sessionInfos,
					final Collection<ExecutionData> executionData)
					throws IOException {
				this.sessionInfos = sessionInfos;
				this.executionData = executionData;
			}

			public void visitBundle(final IBundleCoverage bundle,
					final ISourceFileLocator locator) throws IOException {
				System.out.println("HTMLFormatter visitBundle");
				final BundlePage page = new BundlePage(bundle, null, locator,
						root, HTMLFormatter.this);
				createSessionsPage(page);
				page.render();
			}

			public IReportGroupVisitor visitGroup(final String name)
					throws IOException {
					System.out.println("HTMLFormatter visitGroup");	
				groupHandler = new HTMLGroupVisitor(null, root,
						HTMLFormatter.this, name);
				createSessionsPage(groupHandler.getPage());
				return groupHandler;

			}

			private void createSessionsPage(final ReportPage rootpage) {
				System.out.println("HTMLFormatter createSessionsPage");
				sessionsPage = new SessionsPage(sessionInfos, executionData,
						index, rootpage, root, HTMLFormatter.this);
				System.out.println("HTMLFormatter createSessionsPage End");
			}

			public void visitEnd() throws IOException {
				if (groupHandler != null) {
					groupHandler.visitEnd();
				}

				System.out.println("HTMLFormatter sessionsPage.render()");
				sessionsPage.render();
				System.out.println("HTMLFormatter sessionsPage.render() End");

				output.close();
			}
		};
	}
}
