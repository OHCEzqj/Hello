package com;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyMethodAdapter extends MethodVisitor{

    public MyMethodAdapter(MethodVisitor mv) {
		super(Opcodes.ASM4, mv);
		// TODO Auto-generated constructor stub
	}


	public void visitCode() {
        if (mv != null) {
        	System.out.println("函数解析开始");
            mv.visitCode();
        }
    }


    public void visitInsn(int opcode) {
        if (mv != null) {
         	
            mv.visitInsn(opcode);
        }
    }


    public void visitIntInsn(int opcode, int operand) {
        if (mv != null) {
            mv.visitIntInsn(opcode, operand);
        }
    }


    public void visitVarInsn(int opcode, int var) {
        if (mv != null) {
            mv.visitVarInsn(opcode, var);
        }
    }


    public void visitTypeInsn(int opcode, String type) {
        if (mv != null) {
            mv.visitTypeInsn(opcode, type);
        }
    }

   
    public void visitFieldInsn(int opcode, String owner, String name,
            String desc) {
        if (mv != null) {
            mv.visitFieldInsn(opcode, owner, name, desc);
        }
    }


    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name,
            String desc) {
        if (api >= Opcodes.ASM5) {
            boolean itf = opcode == Opcodes.INVOKEINTERFACE;
            visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        if (mv != null) {
            mv.visitMethodInsn(opcode, owner, name, desc);
        }
    }

 
    public void visitMethodInsn(int opcode, String owner, String name,
            String desc, boolean itf) {
        if (api < Opcodes.ASM5) {
            if (itf != (opcode == Opcodes.INVOKEINTERFACE)) {
                throw new IllegalArgumentException(
                        "INVOKESPECIAL/STATIC on interfaces require ASM 5");
            }
            visitMethodInsn(opcode, owner, name, desc);
            return;
        }
        if (mv != null) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }


  

    public void visitJumpInsn(int opcode, Label label) {
        if (mv != null) {
        	System.out.println("*VisitJumpInsn "+"label:  "+label.toString());
            mv.visitJumpInsn(opcode, label);
        }
    }

 
    public void visitLabel(Label label) {
        if (mv != null) {
        	System.out.println("visitLabel:	"+label);
            mv.visitLabel(label);
        }
    }


    public void visitLdcInsn(Object cst) {
        if (mv != null) {
            mv.visitLdcInsn(cst);
        }
    }

    public void visitIincInsn(int var, int increment) {
        if (mv != null) {
            mv.visitIincInsn(var, increment);
        }
    }


    public void visitTableSwitchInsn(int min, int max, Label dflt,
            Label... labels) {
        if (mv != null) {
            mv.visitTableSwitchInsn(min, max, dflt, labels);
        }
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        if (mv != null) {
            mv.visitLookupSwitchInsn(dflt, keys, labels);
        }
    }


    public void visitMultiANewArrayInsn(String desc, int dims) {
        if (mv != null) {
            mv.visitMultiANewArrayInsn(desc, dims);
        }
    }

    // -------------------------------------------------------------------------
    // Exceptions table entries, debug information, max stack and max locals
    // -------------------------------------------------------------------------


    public void visitTryCatchBlock(Label start, Label end, Label handler,
            String type) {
        if (mv != null) {
            mv.visitTryCatchBlock(start, end, handler, type);
        }
    }

 


    public void visitLocalVariable(String name, String desc, String signature,
            Label start, Label end, int index) {
        if (mv != null) {
            mv.visitLocalVariable(name, desc, signature, start, end, index);
        }
    }


    public void visitLineNumber(int line, Label start) {
        if (mv != null) {
        	System.out.println("visitLineNumber："+line+"	"+start.toString());
            mv.visitLineNumber(line, start);
        }
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        if (mv != null) {
            mv.visitMaxs(maxStack, maxLocals);
        }
    }


    public void visitEnd() {
        if (mv != null) {
        	System.out.println("函数解析结束");
            mv.visitEnd();
        }
    }
}
