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
package org.jacoco.report.internal.html.page;

import java.io.IOException;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.IPackageCoverage;
import org.jacoco.report.ISourceFileLocator;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.IHTMLReportContext;

/**
 * Page showing coverage information for a bundle. The page contains a table
 * with all packages of the bundle.
 */
public class BundlePage extends TablePage<ICoverageNode> {

	private final ISourceFileLocator locator;

	private IBundleCoverage bundle;

	/**
	 * Creates a new visitor in the given context.
	 * 
	 * @param bundle
	 *            coverage date for the bundle
	 * @param parent
	 *            optional hierarchical parent
	 * @param locator
	 *            source locator
	 * @param folder
	 *            base folder for this bundle
	 * @param context
	 *            settings context
	 */
	public BundlePage(final IBundleCoverage bundle, final ReportPage parent,
			final ISourceFileLocator locator, final ReportOutputFolder folder,
			final IHTMLReportContext context) {
		
		super(bundle.getPlainCopy(), parent, folder, context);
		System.out.println("BundlePage 构造函数");
		System.out.println("BundlePage index.html 已调用父类");
		this.bundle = bundle;
		this.locator = locator;
	}

	@Override
	public void render() throws IOException {
		renderPackages();

		System.out.println("BundlePage renderPackages() End");

		super.render();
		System.out.println("BundlePage super.render() End");
		// Don't keep the bundle structure in memory
		bundle = null;
	}

	private void renderPackages() throws IOException {
		System.out.println("BundlePage renderPackages");
		for (final IPackageCoverage p : bundle.getPackages()) {
			System.out.println("BundlePage bundle.getPackages() for");


			final String packagename = p.getName();
			System.out.println("BundlePage bundle.getPackages() p.getName()；	"+packagename);


			final String foldername = packagename.length() == 0 ? "default"
					: packagename.replace('/', '.');
            System.out.println("BundlePage bundle.getPackages() p.getName()；	"+foldername);


			final PackagePage page = new PackagePage(p, this, locator,
					folder.subFolder(foldername), context);
		

			page.render();

			addItem(page);
		}
	}

	@Override
	protected String getOnload() {
		return "initialSort(['breadcrumb', 'coveragetable'])";
	}

	@Override
	protected String getFileName() {
		return "index.html";
	}

}
