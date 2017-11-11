package com;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor{

	
	
	public ClassPrinter( ClassVisitor cv) {
		super(Opcodes.ASM4, cv);
		// TODO Auto-generated constructor stub
	}

	@Override
	 public void visit(int version, int access, String name, String signature,
	            String superName, String[] interfaces) {
		 if (cv != null) {
			 System.out.println("superName"+superName);
			 cv.visit(version, access, name, signature, superName, interfaces);
	        }
	    }
	
	 @Override
	    public void visitSource(String source, String debug) {
	    	 if (cv != null) {
	    		 System.out.println("开始visitSource");
	    		 cv.visitSource(source, debug);
	         }
	    }
	  
	    @Override
	
	    public void visitOuterClass(String owner, String name, String desc) {
	    	 if (cv != null) {
	    		 System.out.println("OuterClass"+name);
	    		 cv.visitOuterClass(owner, name, desc);
	         }
	    }

	    @Override

	    public void visitInnerClass(String name, String outerName,
	            String innerName, int access) {
	        if (cv != null) {
	        	 System.out.println("InnerName"+innerName); 
	        	 cv.visitInnerClass(name, outerName, innerName, access);
	        }
	    }

	    @Override
	    public FieldVisitor visitField(int access, String name, String desc,
	            String signature, Object value) {
	        if (cv != null) {
	        	 System.out.println("Field："+name);
	        	 return cv.visitField(access, name, desc, signature, value);
	        }
	        return null;
	    }
        @Override
	    public MethodVisitor visitMethod(int access, String name, String desc,
	            String signature, String[] exceptions) {
	     MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
	    	
	     if(mv!=null){
	    	mv = new MyMethodAdapter(mv);
	    	
	    	}
	     return mv;
	    }

@Override
	    public void visitEnd() {
	       System.out.println("分析结束");
	       cv.visitEnd();
	    }
}
