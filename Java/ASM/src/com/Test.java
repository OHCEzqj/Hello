package com;
import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
       ClassReader cr=new ClassReader("com.Hello");
       ClassWriter cw=new ClassWriter(cr,0);
       ClassPrinter cp=new ClassPrinter(cw);
       cr.accept(cp,0);
	}

}
