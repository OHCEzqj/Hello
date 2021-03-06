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
package org.jacoco.core.internal.analysis;

import java.util.Collection;

import org.jacoco.core.analysis.CoverageNodeImpl;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.IPackageCoverage;
import org.jacoco.core.analysis.ISourceFileCoverage;

/**
 * Implementation of {@link IPackageCoverage}.
 */
public class PackageCoverageImpl extends CoverageNodeImpl implements
		IPackageCoverage {

	private final Collection<IClassCoverage> classes;

	private final Collection<ISourceFileCoverage> sourceFiles;

	/**
	 * Creates package node instance for a package with the given name.
	 * 
	 * @param name
	 *            vm name of the package
	 * @param classes
	 *            collection of all classes in this package
	 * @param sourceFiles
	 *            collection of all source files in this package
	 */
	public PackageCoverageImpl(final String name,
			final Collection<IClassCoverage> classes,
			final Collection<ISourceFileCoverage> sourceFiles) {
		super(ElementType.PACKAGE, name);
		this.classes = classes;
		this.sourceFiles = sourceFiles;
		System.out.println("PackageCoverageImpl increment(sourceFiles);");
		increment(sourceFiles);
		System.out.println("PackageCoverageImpl increment(sourceFiles); End");
		for (final IClassCoverage c : classes) {
			System.out.println("PackageCoverageImpl IClassCoverage c : classes");
			// We need to add only classes without a source file reference.
			// Classes associated with a source file are already included in the
			// SourceFileCoverage objects.
			if (c.getSourceFileName() == null) {
				increment(c);
			}
		}
		System.out.println("PackageCoverageImpl End");
	}

	// === IPackageCoverage implementation ===

	public Collection<IClassCoverage> getClasses() {
		System.out.println("PackageCoverageImpl getClasses()");
		return classes;
	}

	public Collection<ISourceFileCoverage> getSourceFiles() {
		System.out.println("PackageCoverageImpl getSourceFiles()");
		return sourceFiles;
	}

}
