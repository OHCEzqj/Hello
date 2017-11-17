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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.analysis.ISourceNode;
import org.jacoco.core.internal.analysis.filter.EnumFilter;
import org.jacoco.core.internal.analysis.filter.IFilter;
import org.jacoco.core.internal.analysis.filter.IFilterOutput;
import org.jacoco.core.internal.analysis.filter.LombokGeneratedFilter;
import org.jacoco.core.internal.analysis.filter.PrivateEmptyNoArgConstructorFilter;
import org.jacoco.core.internal.analysis.filter.SynchronizedFilter;
import org.jacoco.core.internal.analysis.filter.SyntheticFilter;
import org.jacoco.core.internal.analysis.filter.TryWithResourcesEcjFilter;
import org.jacoco.core.internal.analysis.filter.TryWithResourcesJavacFilter;
import org.jacoco.core.internal.flow.IFrame;
import org.jacoco.core.internal.flow.Instruction;
import org.jacoco.core.internal.flow.LabelInfo;
import org.jacoco.core.internal.flow.MethodProbesVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

/**
 * A {@link MethodProbesVisitor} that analyzes which statements and branches of
 * a method have been executed based on given probe data.
 */
public class MethodAnalyzer extends MethodProbesVisitor
		implements IFilterOutput {

	private static final IFilter[] FILTERS = new IFilter[] { new EnumFilter(),
			new SyntheticFilter(), new SynchronizedFilter(),
			new TryWithResourcesJavacFilter(), new TryWithResourcesEcjFilter(),
			new PrivateEmptyNoArgConstructorFilter(), new LombokGeneratedFilter() };

	private final String className;

	private final String superClassName;

	private final boolean[] probes;

	private final MethodCoverageImpl coverage;

	private int currentLine = ISourceNode.UNKNOWN_LINE;

	private int firstLine = ISourceNode.UNKNOWN_LINE;

	private int lastLine = ISourceNode.UNKNOWN_LINE;

	// Due to ASM issue #315745 there can be more than one label per instruction
	private final List<Label> currentLabel = new ArrayList<Label>(2);

	/** List of all analyzed instructions */
	private final List<Instruction> instructions = new ArrayList<Instruction>();

	/** List of all predecessors of covered probes */
	private final List<Instruction> coveredProbes = new ArrayList<Instruction>();

	/** List of all jumps encountered */
	private final List<Jump> jumps = new ArrayList<Jump>();

	/** Last instruction in byte code sequence */
	private Instruction lastInsn;

	/**
	 * New Method analyzer for the given probe data.
	 * 
	 * @param className
	 *            class name
	 * @param superClassName
	 *            superclass name
	 * @param name
	 *            method name
	 * @param desc
	 *            method descriptor
	 * @param signature
	 *            optional parameterized signature
	 * 
	 * @param probes
	 *            recorded probe date of the containing class or
	 *            <code>null</code> if the class is not executed at all
	 */
	public MethodAnalyzer(final String className, final String superClassName,
			final String name, final String desc, final String signature,
			final boolean[] probes) {
		super();
		System.out.println("MethodAnalyzer MethodName:"+name);
		this.className = className;
		this.superClassName = superClassName;
		this.probes = probes;
		this.coverage = new MethodCoverageImpl(name, desc, signature);
	}

	/**
	 * Returns the coverage data for this method after this visitor has been
	 * processed.
	 * 
	 * @return coverage data for this method
	 */
	public IMethodCoverage getCoverage() {
		System.out.println("MethodAnalyzer getCoverage");
		return coverage;
	}

	/**
	 * {@link MethodNode#accept(MethodVisitor)}
	 */
	@Override
	public void accept(final MethodNode methodNode,
			final MethodVisitor methodVisitor) {
		this.ignored.clear();
		System.out.println("MethodAnalyzer accept");
		for (final IFilter filter : FILTERS) {
			filter.filter(className, superClassName, methodNode, this);
		}

		for (final TryCatchBlockNode n : methodNode.tryCatchBlocks) {
			n.accept(methodVisitor);
		}
		currentNode = methodNode.instructions.getFirst();
		while (currentNode != null) {
			currentNode.accept(methodVisitor);
			currentNode = currentNode.getNext();
		}
		methodVisitor.visitEnd();
	}

	private final Set<AbstractInsnNode> ignored = new HashSet<AbstractInsnNode>();
	private AbstractInsnNode currentNode;

	public void ignore(final AbstractInsnNode fromInclusive,
			final AbstractInsnNode toInclusive) {
		for (AbstractInsnNode i = fromInclusive; i != toInclusive; i = i
				.getNext()) {
			ignored.add(i);
		}
		ignored.add(toInclusive);
	}

	@Override
	public void visitLabel(final Label label) {
		System.out.println("MethodAnalyzer visitLabel");
		currentLabel.add(label);
		if (!LabelInfo.isSuccessor(label)) {
			lastInsn = null;
		}
	}

	@Override
	public void visitLineNumber(final int line, final Label start) {
		System.out.println("MethodAnalyzer visitLineNumber"+line);
		currentLine = line;
		if (firstLine > line || lastLine == ISourceNode.UNKNOWN_LINE) {
			firstLine = line;
		}
		if (lastLine < line) {
			lastLine = line;
		}
	}

	private void visitInsn() {
		final Instruction insn = new Instruction(currentNode, currentLine);
		System.out.println("MethodAnalyzer 自带visitInsn:"+insn);
		instructions.add(insn);
		if (lastInsn != null) {
			insn.setPredecessor(lastInsn);
		}
		final int labelCount = currentLabel.size();
		if (labelCount > 0) {
			for (int i = labelCount; --i >= 0;) {
				LabelInfo.setInstruction(currentLabel.get(i), insn);
			}
			currentLabel.clear();
		}
		lastInsn = insn;
	}

	@Override
	public void visitInsn(final int opcode) {
		System.out.println("MethodAnalyzer visitInsn:"+opcode);
		visitInsn();
	}

	@Override
	public void visitIntInsn(final int opcode, final int operand) {
		System.out.println("MethodAnalyzer visitIntInsn:	"+"opcode:"+opcode+"operand"+operand);
		visitInsn();
	}

	@Override
	public void visitVarInsn(final int opcode, final int var) {
		System.out.println("MethodAnalyzer visitVarInsn:	"+"opcode"+opcode+"var"+var);
		visitInsn();
	}

	@Override
	public void visitTypeInsn(final int opcode, final String type) {
		System.out.println("MethodAnalyzer visitTypeInsn:"+"opcode"+opcode+"type"+type);
		visitInsn();
	}

	@Override
	public void visitFieldInsn(final int opcode, final String owner,
			final String name, final String desc) {
		System.out.println("MethodAnalyzer visitFieldInsn:"+"opcode, owner,name, desc"+opcode+"	"+owner+"	"+name+"	"+desc);
		visitInsn();
	}

	@Override
	public void visitMethodInsn(final int opcode, final String owner,
			final String name, final String desc, final boolean itf) {
		System.out.println("MethodAnalyzer visitMethodInsn:"+"opcode, owner,name, desc"+opcode+"	"+owner+"	"+name+"	"+desc);
		visitInsn();
	}

	@Override
	public void visitInvokeDynamicInsn(final String name, final String desc,
			final Handle bsm, final Object... bsmArgs) {
		System.out.println("MethodAnalyzer visitInvokeDynamicInsn:"+" name, desc"+"	"+"	"+name+"	"+desc);
		visitInsn();
	}

	@Override
	public void visitJumpInsn(final int opcode, final Label label) {
		System.out.println("MethodAnalyzer visitJumpInsn:"+"opcode"+opcode+"label"+label);
		visitInsn();
		jumps.add(new Jump(lastInsn, label));
	}

	@Override
	public void visitLdcInsn(final Object cst) {
		System.out.println("MethodAnalyzer visitLdcInsn:"+"object"+cst);
		visitInsn();
	}

	@Override
	public void visitIincInsn(final int var, final int increment) {
		System.out.println("MethodAnalyzer visitIincInsn:"+"var"+var+"increment"+increment);
		visitInsn();
	}

	@Override
	public void visitTableSwitchInsn(final int min, final int max,
			final Label dflt, final Label... labels) {
		System.out.println("MethodAnalyzer visitTableSwitchInsn:");
		visitSwitchInsn(dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(final Label dflt, final int[] keys,
			final Label[] labels) {
		System.out.println("MethodAnalyzer visitLookupSwitchInsn:");
		visitSwitchInsn(dflt, labels);
	}

	private void visitSwitchInsn(final Label dflt, final Label[] labels) {
		System.out.println("MethodAnalyzer visitSwitchInsn:");
		visitInsn();
		LabelInfo.resetDone(labels);
		jumps.add(new Jump(lastInsn, dflt));
		LabelInfo.setDone(dflt);
		for (final Label l : labels) {
			if (!LabelInfo.isDone(l)) {
				jumps.add(new Jump(lastInsn, l));
				LabelInfo.setDone(l);
			}
		}
	}

	@Override
	public void visitMultiANewArrayInsn(final String desc, final int dims) {
		System.out.println("MethodAnalyzer visitMultiANewArrayInsn:");
		visitInsn();
	}

	@Override
	public void visitProbe(final int probeId) {
		System.out.println("MethodAnalyzer visitProbe:");
		addProbe(probeId);
		lastInsn = null;
	}

	@Override
	public void visitJumpInsnWithProbe(final int opcode, final Label label,
			final int probeId, final IFrame frame) {
		System.out.println("MethodAnalyzer visitJumpInsnWithProbe:");
		visitInsn();
		addProbe(probeId);
	}

	@Override
	public void visitInsnWithProbe(final int opcode, final int probeId) {
		System.out.println("MethodAnalyzer visitInsnWithProbe:");
		visitInsn();
		addProbe(probeId);
	}

	@Override
	public void visitTableSwitchInsnWithProbes(final int min, final int max,
			final Label dflt, final Label[] labels, final IFrame frame) {
		System.out.println("MethodAnalyzer visitTableSwitchInsnWithProbes:");
		visitSwitchInsnWithProbes(dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsnWithProbes(final Label dflt,
			final int[] keys, final Label[] labels, final IFrame frame) {
		System.out.println("MethodAnalyzer visitLookupSwitchInsnWithProbes:");
		visitSwitchInsnWithProbes(dflt, labels);
	}

	private void visitSwitchInsnWithProbes(final Label dflt,
			final Label[] labels) {
		System.out.println("MethodAnalyzer visitSwitchInsnWithProbes:");
		visitInsn();
		LabelInfo.resetDone(dflt);
		LabelInfo.resetDone(labels);
		visitSwitchTarget(dflt);
		for (final Label l : labels) {
			visitSwitchTarget(l);
		}
	}

	private void visitSwitchTarget(final Label label) {
		System.out.println("MethodAnalyzer visitSwitchTarget:");
		final int id = LabelInfo.getProbeId(label);
		if (!LabelInfo.isDone(label)) {
			if (id == LabelInfo.NO_PROBE) {
				jumps.add(new Jump(lastInsn, label));
			} else {
				addProbe(id);
			}
			LabelInfo.setDone(label);
		}
	}

	@Override
	public void visitEnd() {
		System.out.println("MethodAnalyzer visitEnd:");
		// Wire jumps:
		for (final Jump j : jumps) {
			LabelInfo.getInstruction(j.target).setPredecessor(j.source);
		}
		// Propagate probe values:
		for (final Instruction p : coveredProbes) {
			p.setCovered();
		}
		// Report result:
		coverage.ensureCapacity(firstLine, lastLine);
		for (final Instruction i : instructions) {
			if (ignored.contains(i.getNode())) {
				continue;
			}

			final int total = i.getBranches();
			System.out.println("MethodAnalyzer i.getBranches():	"+total);
			final int covered = i.getCoveredBranches();
			System.out.println("MethodAnalyzer i.getCoveredBranches():	"+covered);
			final ICounter instrCounter = covered == 0 ? CounterImpl.COUNTER_1_0
					: CounterImpl.COUNTER_0_1;

			//是否是if，有大于一个分支
			final ICounter branchCounter = total > 1
					? CounterImpl.getInstance(total - covered, covered)
					: CounterImpl.COUNTER_0_0;

			coverage.increment(instrCounter, branchCounter, i.getLine());
		}
		coverage.incrementMethodCounter();
	}

	private void addProbe(final int probeId) {
		System.out.println("MethodAnalyzer addProbe");
		lastInsn.addBranch();
		if (probes != null && probes[probeId]) {
			coveredProbes.add(lastInsn);
		}
	}

	private static class Jump {
		
		final Instruction source;
		final Label target;

		Jump(final Instruction source, final Label target) {
			System.out.println("MethodAnalyzer Jump");
			this.source = source;
			this.target = target;
		}
	}

}
