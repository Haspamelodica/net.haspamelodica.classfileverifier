package net.haspamelodica.classfileverifier.checkingvisitors;

import static net.haspamelodica.classfileverifier.checkingvisitors.CheckingClassVisitor.throwUnimplemented;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

public class CheckingFieldVisitor extends FieldVisitor
{
	public CheckingFieldVisitor(FieldVisitor nextVisitor)
	{
		super(Opcodes.ASM9, nextVisitor);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor + " for a field");
		//return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor + " for typeRef " + typeRef + (typePath != null ? "; path " + typePath : null)
				+ " for a field");
		//return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitAttribute(Attribute attribute)
	{
		throwUnimplemented("Non-standard attribute: " + attribute + " for a field");
		super.visitAttribute(attribute);
	}

	@Override
	public void visitEnd()
	{
		//Secure: no information contained
		super.visitEnd();
	}
}
