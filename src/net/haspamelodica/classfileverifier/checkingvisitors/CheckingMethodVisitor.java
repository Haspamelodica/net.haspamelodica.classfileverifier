package net.haspamelodica.classfileverifier.checkingvisitors;

import static net.haspamelodica.classfileverifier.checkingvisitors.CheckingClassVisitor.throwUnimplemented;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

public class CheckingMethodVisitor extends MethodVisitor
{
	public CheckingMethodVisitor(MethodVisitor nextVisitor)
	{
		super(Opcodes.ASM9, nextVisitor);
	}

	@Override
	public void visitParameter(String name, int access)
	{
		//Secure: Neither name nor access flags (which can only be FINAL, SYNTHETIC, MANDATED or combinations) affect security
		super.visitParameter(name, access);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault()
	{
		return throwUnimplemented("Annotation default value for a method");
		//return super.visitAnnotationDefault();
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitAnnotableParameterCount(int parameterCount, boolean visible)
	{
		// TODO Auto-generated method stub
		super.visitAnnotableParameterCount(parameterCount, visible);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitParameterAnnotation(parameter, descriptor, visible);
	}

	@Override
	public void visitAttribute(Attribute attribute)
	{
		// TODO Auto-generated method stub
		super.visitAttribute(attribute);
	}

	@Override
	public void visitCode()
	{
		// TODO Auto-generated method stub
		super.visitCode();
	}

	@Override
	public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack)
	{
		// TODO Auto-generated method stub
		super.visitFrame(type, numLocal, local, numStack, stack);
	}

	@Override
	public void visitInsn(int opcode)
	{
		// TODO Auto-generated method stub
		super.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand)
	{
		// TODO Auto-generated method stub
		super.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitVarInsn(int opcode, int var)
	{
		// TODO Auto-generated method stub
		super.visitVarInsn(opcode, var);
	}

	@Override
	public void visitTypeInsn(int opcode, String type)
	{
		// TODO Auto-generated method stub
		super.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor)
	{
		// TODO Auto-generated method stub
		super.visitFieldInsn(opcode, owner, name, descriptor);
	}

	@Override
	@Deprecated
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor)
	{
		// TODO Auto-generated method stub
		super.visitMethodInsn(opcode, owner, name, descriptor);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface)
	{
		// TODO Auto-generated method stub
		super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments)
	{
		// TODO Auto-generated method stub
		super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label)
	{
		// TODO Auto-generated method stub
		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLabel(Label label)
	{
		// TODO Auto-generated method stub
		super.visitLabel(label);
	}

	@Override
	public void visitLdcInsn(Object value)
	{
		// TODO Auto-generated method stub
		super.visitLdcInsn(value);
	}

	@Override
	public void visitIincInsn(int var, int increment)
	{
		// TODO Auto-generated method stub
		super.visitIincInsn(var, increment);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels)
	{
		// TODO Auto-generated method stub
		super.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
	{
		// TODO Auto-generated method stub
		super.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions)
	{
		// TODO Auto-generated method stub
		super.visitMultiANewArrayInsn(descriptor, numDimensions);
	}

	@Override
	public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type)
	{
		// TODO Auto-generated method stub
		super.visitTryCatchBlock(start, end, handler, type);
	}

	@Override
	public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index)
	{
		// TODO Auto-generated method stub
		super.visitLocalVariable(name, descriptor, signature, start, end, index);
	}

	@Override
	public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible)
	{
		// TODO Auto-generated method stub
		return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, descriptor, visible);
	}

	@Override
	public void visitLineNumber(int line, Label start)
	{
		// TODO Auto-generated method stub
		super.visitLineNumber(line, start);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals)
	{
		// TODO Auto-generated method stub
		super.visitMaxs(maxStack, maxLocals);
	}

	@Override
	public void visitEnd()
	{
		// TODO Auto-generated method stub
		super.visitEnd();
	}
}
