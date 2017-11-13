package com;
import java.io.IOException;

import org.objectweb.asm.ClassReader;



public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//       ClassReader cr=new ClassReader("com.Hello");
//       ClassWriter cw=new ClassWriter(cr,0);
//       MyClassAdapter cp=new MyClassAdapter(cw);
//       cr.accept(cp,0);
//		
		 ClassReader cr=new ClassReader("com.Hello");
		 ClassPrinter cp=new ClassPrinter();
		 cr.accept(cp,0);
		
	}

}
