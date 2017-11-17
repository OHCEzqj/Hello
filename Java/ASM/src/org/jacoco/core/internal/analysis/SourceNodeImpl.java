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

import org.jacoco.core.analysis.CoverageNodeImpl;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.analysis.ISourceNode;

/**
 * Implementation of {@link ISourceNode}.
 */
public class SourceNodeImpl extends CoverageNodeImpl implements ISourceNode {

	private LineImpl[] lines;

	/** first line number in {@link #lines} */
	private int offset;

	/**
	 * Create a new source node implementation instance.
	 * 
	 * @param elementType
	 *            element type
	 * @param name
	 *            name of the element
	 */
	public SourceNodeImpl(final ElementType elementType, final String name) {
		super(elementType, name);
		lines = null;
		offset = UNKNOWN_LINE;
	}

	/**
	 * Make sure that the internal buffer can keep lines from first to last.
	 * While the buffer is also incremented automatically, this method allows
	 * optimization in case the total range is known in advance.
	 * 
	 * @param first
	 *            first line number or {@link ISourceNode#UNKNOWN_LINE}
	 * @param last
	 *            last line number or {@link ISourceNode#UNKNOWN_LINE}
	 */
	public void ensureCapacity(final int first, final int last) {
		if (first == UNKNOWN_LINE || last == UNKNOWN_LINE) {
			return;
		}
		if (lines == null) {
			offset = first;
			lines = new LineImpl[last - first + 1];
		} else {
			System.out.println("SourceNodeImpl	ensureCapacity else");
			final int newFirst = Math.min(getFirstLine(), first);
			final int newLast = Math.max(getLastLine(), last);
			final int newLength = newLast - newFirst + 1;
			if (newLength > lines.length) {
				System.out.println("SourceNodeImpl	ensureCapacity 更新lines长度");
				final LineImpl[] newLines = new LineImpl[newLength];
				System.arraycopy(lines, 0, newLines, offset - newFirst,
						lines.length);
				offset = newFirst;
				lines = newLines;
				System.out.println("SourceNodeImpl	ensureCapacity 结束");
			}
		}
	}

	/**
	 * Increments all counters by the values of the given child. When
	 * incrementing the line counter it is assumed that the child refers to the
	 * same source file.
	 * 
	 * @param child
	 *            child node to add
	 */
	public void increment(final ISourceNode child) {
		System.out.println("SourceNodeImpl iincrement(final ISourceNode child)	");
		//child 是MEthodCoverageImpl,getInstructionCounter函数是来自其父类的父类的函数（本类是MethodCoverageImpl的父类）
		instructionCounter = instructionCounter.increment(child
				.getInstructionCounter());
		branchCounter = branchCounter.increment(child.getBranchCounter());
		complexityCounter = complexityCounter.increment(child
				.getComplexityCounter());
		methodCounter = methodCounter.increment(child.getMethodCounter());
		classCounter = classCounter.increment(child.getClassCounter());
		final int firstLine = child.getFirstLine();
		if (firstLine != UNKNOWN_LINE) {
			System.out.println("SourceNodeImpl iincrement(final ISourceNode child)	firstLine != UNKNOWN_LINE");
			final int lastLine = child.getLastLine();

			ensureCapacity(firstLine, lastLine);
			for (int i = firstLine; i <= lastLine; i++) {
				System.out.println("SourceNodeImpl iincrement(final ISourceNode child)	for");
				final ILine line = child.getLine(i);
				incrementLine(line.getInstructionCounter(),
						line.getBranchCounter(), i);
			}
		}
	}

	/**
	 * Increments instructions and branches by the given counter values. If a
	 * optional line number is specified the instructions and branches are added
	 * to the given line. The line counter is incremented accordingly.
	 * 
	 * @param instructions
	 *            instructions to add
	 * @param branches
	 *            branches to add
	 * @param line
	 *            optional line number or {@link ISourceNode#UNKNOWN_LINE}
	 */
	public void increment(final ICounter instructions, final ICounter branches,
			final int line) {
		System.out.println("SourceNodeImpl increment (ICounter instructions, ICounter branches,int line)");
		if (line != UNKNOWN_LINE) {
			System.out.println("SourceNodeImpl	line != UNKNOWN_LINE");
			incrementLine(instructions, branches, line);
		}
		System.out.println("SourceNodeImpl	instructionCounter.increment(instructions)");
		instructionCounter = instructionCounter.increment(instructions);
		System.out.println("SourceNodeImpl	branchCounter.increment(branches)");
		branchCounter = branchCounter.increment(branches);
	}

	private void incrementLine(final ICounter instructions,
			final ICounter branches, final int line) {
		System.out.println("SourceNodeImpl incrementLine");
		ensureCapacity(line, line);
		final LineImpl l = getLine(line);
		final int oldTotal = l.getInstructionCounter().getTotalCount();
		final int oldCovered = l.getInstructionCounter().getCoveredCount();
		
		//进入lineLmpl，会有log
		lines[line - offset] = l.increment(instructions, branches);

		// Increment line counter:
		if (instructions.getTotalCount() > 0) {
			if (instructions.getCoveredCount() == 0) {
				if (oldTotal == 0) {
					System.out.println("11");
					lineCounter = lineCounter
							.increment(CounterImpl.COUNTER_1_0);
				}
			} else {
				if (oldTotal == 0) {//如果是新行，
					System.out.println("22");
					lineCounter = lineCounter
							.increment(CounterImpl.COUNTER_0_1);
				} else {//如果是旧行
					if (oldCovered == 0) {//如果这行之前的指令没有覆盖
						System.out.println("33");
						lineCounter = lineCounter.increment(-1, +1);
					}
				}
			}
		}//getTotalCount>0
	}

	// === ISourceNode implementation ===

	public int getFirstLine() {
		System.out.println("SourceNodeImpl getFirstLine	"+offset);
		return offset;
	}

	public int getLastLine() {
		System.out.println("SourceNodeImpl getLastLine	");
		return lines == null ? UNKNOWN_LINE : (offset + lines.length - 1);
	}

	public LineImpl getLine(final int nr) {
		System.out.println("SourceNodeImpl getLine	"+nr);
		if (lines == null || nr < getFirstLine() || nr > getLastLine()) {
			return LineImpl.EMPTY;
		}
		final LineImpl line = lines[nr - offset];
		return line == null ? LineImpl.EMPTY : line;
	}

}
