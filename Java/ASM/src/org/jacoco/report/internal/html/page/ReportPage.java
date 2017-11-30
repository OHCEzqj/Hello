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

import org.jacoco.core.JaCoCo;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.HTMLDocument;
import org.jacoco.report.internal.html.HTMLElement;
import org.jacoco.report.internal.html.IHTMLReportContext;
import org.jacoco.report.internal.html.ILinkable;
import org.jacoco.report.internal.html.resources.Resources;
import org.jacoco.report.internal.html.resources.Styles;

/**
 * Base class for HTML page generators. It renders the page skeleton with the
 * breadcrumb, the title and the footer. Every report page is part of a
 * hierarchy and has a parent page (except the root page).
 */
public abstract class ReportPage implements ILinkable {

	private final ReportPage parent;

	/** output folder for this node */
	protected final ReportOutputFolder folder;

	/** context for this report */
	protected final IHTMLReportContext context;

	/**
	 * Creates a new report page.
	 * 
	 * @param parent
	 *            optional hierarchical parent
	 * @param folder
	 *            base folder to create this report in
	 * @param context
	 *            settings context
	 */
	protected ReportPage(final ReportPage parent,
			final ReportOutputFolder folder, final IHTMLReportContext context) {
		System.out.println("ReportPage 构造函数");
		this.parent = parent;
		this.context = context;
		this.folder = folder;
	}

	/**
	 * Checks whether this is the root page of the report.
	 * 
	 * @return <code>true</code> if this is the root page
	 */
	protected final boolean isRootPage() {
		return parent == null;
	}

	/**
	 * Renders this page's content and optionally additional pages. This method
	 * must be called at most once.
	 * 
	 * @throws IOException
	 *             if the page can't be written
	 */
	public void render() throws IOException {

		System.out.println("ReportPage render");

		final HTMLDocument doc = new HTMLDocument(
				folder.createFile(getFileName()), context.getOutputEncoding());
        System.out.println("ReportPage HTMLDocument doc");

		doc.attr("lang", context.getLocale().getLanguage());
		System.out.println("ReportPage doc.attr End");

		head(doc.head());
		System.out.println("ReportPage head(doc.head()) End");

		body(doc.body());
		System.out.println("ReportPage body(doc.body())  End");

		doc.close();
		System.out.println("ReportPage render End");
	}

	/**
	 * Creates the elements within the head element.
	 * 
	 * @param head
	 *            head tag of the page
	 * @throws IOException
	 *             in case of IO problems with the report writer
	 */
	//头部
	protected void head(final HTMLElement head) throws IOException {
		head.meta("Content-Type", "text/html;charset=UTF-8");
		head.link("stylesheet",
				context.getResources().getLink(folder, Resources.STYLESHEET),
				"text/css");
		head.link("shortcut icon",
				context.getResources().getLink(folder, "report.gif"),
				"image/gif");
		head.title().text(getLinkLabel());
	}

	private void body(final HTMLElement body) throws IOException {
       System.out.println("ReportPage body");

		body.attr("onload", getOnload());
		System.out.println("1 ReportPage body body.attr End");

		final HTMLElement navigation = body.div(Styles.BREADCRUMB);
		System.out.println("2 ReportPage body HTMLElement navigation end");

		navigation.attr("id", "breadcrumb");
		System.out.println("3 ReportPage body navigation.attr end");

		infoLinks(navigation.span(Styles.INFO));
		System.out.println("4 ReportPage body infoLinks end");

		breadcrumb(navigation, folder);
		System.out.println("5 ReportPage body breadcrumb end");

		body.h1().text(getLinkLabel());
		System.out.println("6 ReportPage body body.h1().text(getLinkLabel()) end");

		content(body);//keypoint
		System.out.println("7 ReportPage body content(body) end");

		footer(body);

		System.out.println("ReportPage body End");
	}

	/**
	 * Returns the onload handler for this page.
	 * 
	 * @return handler or <code>null</code>
	 */
	protected String getOnload() {
		return null;
	}

	/**
	 * Inserts additional links on the top right corner.
	 * 
	 * @param span
	 *            parent element
	 * @throws IOException
	 *             in case of IO problems with the report writer
	 */
	protected void infoLinks(final HTMLElement span) throws IOException {
		span.a(context.getSessionsPage(), folder);
	}

	private void breadcrumb(final HTMLElement div, final ReportOutputFolder base)
			throws IOException {
		breadcrumbParent(parent, div, base);
		div.span(getLinkStyle()).text(getLinkLabel());
	}

	private static void breadcrumbParent(final ReportPage page,
			final HTMLElement div, final ReportOutputFolder base)
			throws IOException {
		if (page != null) {
			breadcrumbParent(page.parent, div, base);
			div.a(page, base);
			div.text(" > ");
		}
	}
//脚注
	private void footer(final HTMLElement body) throws IOException {
		final HTMLElement footer = body.div(Styles.FOOTER);
		final HTMLElement versioninfo = footer.span(Styles.RIGHT);
		versioninfo.text(" ReportPage Created with ");
		versioninfo.a(JaCoCo.HOMEURL).text("JaCoCo");
		versioninfo.text(" ").text(JaCoCo.VERSION);
		footer.text(context.getFooterText());
	}

	/**
	 * Specifies the local file name of this page.
	 * 
	 * @return local file name
	 */
	protected abstract String getFileName();

	/**
	 * Creates the actual content of the page.
	 * 
	 * @param body
	 *            body tag of the page
	 * @throws IOException
	 *             in case of IO problems with the report writer
	 */
	protected abstract void content(final HTMLElement body) throws IOException;

	// === ILinkable ===

	public final String getLink(final ReportOutputFolder base) {
		return folder.getLink(base, getFileName());
	}

}
