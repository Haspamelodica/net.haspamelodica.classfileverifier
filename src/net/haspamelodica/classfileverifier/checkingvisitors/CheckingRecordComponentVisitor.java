package net.haspamelodica.classfileverifier.checkingvisitors;

import static net.haspamelodica.classfileverifier.checkingvisitors.CheckingClassVisitor.throwUnimplemented;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.TypePath;

public class CheckingRecordComponentVisitor extends RecordComponentVisitor
{
	public CheckingRecordComponentVisitor(RecordComponentVisitor nextVisitor)
	{
		super(Opcodes.ASM9, nextVisitor);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor + " for a record component");
		//return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor + " for typeRef " + typeRef + (typePath != null ? "; path " + typePath : null)
				+ " for a record component");
		//return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitAttribute(Attribute attribute)
	{
		throwUnimplemented("Non-standard attribute: " + attribute + " for a record component");
		//super.visitAttribute(attribute);
	}

	@Override
	public void visitEnd()
	{
		//Secure: no information contained
		super.visitEnd();
	}

	@Override
	public RecordComponentVisitor getDelegate()
	{
		return null;
	}
}
