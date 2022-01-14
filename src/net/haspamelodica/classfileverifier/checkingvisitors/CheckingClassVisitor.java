package net.haspamelodica.classfileverifier.checkingvisitors;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.TypePath;

import net.haspamelodica.classfileverifier.CheckFailedException;

public class CheckingClassVisitor extends ClassVisitor
{
	public CheckingClassVisitor(ClassVisitor nextVisitor)
	{
		super(Opcodes.ASM9, nextVisitor);
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces)
	{
		System.out.println("Checking " + name);
		//overriding classes / implementing interfaces and having type arguments doesn't affect security
		//TODO maybe it does by making "evil" methods accessible
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitSource(String source, String debug)
	{
		//neither source file nor debug information affect security
		super.visitSource(source, debug);
	}

	@Override
	public ModuleVisitor visitModule(String name, int access, String version)
	{
		return throwUnimplemented("Module definition class for " + name + " version " + version);
	}

	@Override
	public void visitNestHost(String nestHost)
	{
		//Secure: the nest host has to list all members explicitly
		super.visitNestHost(nestHost);
	}

	@Override
	public void visitOuterClass(String owner, String name, String descriptor)
	{
		throwUnimplemented("Outer class " + owner + (name != null ? "; Method " + name + " " + descriptor : null));
		//super.visitOuterClass(owner, name, descriptor);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor);
		//return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible)
	{
		return throwUnimplemented("Annotation " + descriptor + " for typeRef " + typeRef + (typePath != null ? "; path " + typePath : null));
		//return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
	}

	@Override
	public void visitAttribute(Attribute attribute)
	{
		throwUnimplemented("Non-standard attribute: " + attribute);
		//super.visitAttribute(attribute);
	}

	@Override
	public void visitNestMember(String nestMember)
	{
		//Secure: members have to mention their host explicitly
		super.visitNestMember(nestMember);
	}

	@Override
	public void visitPermittedSubclass(String permittedSubclass)
	{
		//Secure: subclasses still have to mention their superclass explicitly
		super.visitPermittedSubclass(permittedSubclass);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access)
	{
		throwUnimplemented("Inner class " + name + ", outer " + outerName + ", inner " + innerName);
		//super.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature)
	{
		//Secure: The name, descriptor and signature of a record component don't have an effect on security
		return new CheckingRecordComponentVisitor(super.visitRecordComponent(name, descriptor, signature));
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value)
	{
		//The documentation says value is an Integer, Float, Long, Double or String.
		//Let's be on the safe side if this changes in future versions.
		if(value != null && !(value instanceof Integer || value instanceof Float ||
				value instanceof Double || value instanceof Long || value instanceof String))
			//calling toString on value isn't any more insecure than creating it in the first place.
			throw new CheckFailedException("Unknown initial value type encountered; treating as insecure: "
					+ value.getClass() + ": " + value);
		return new CheckingFieldVisitor(super.visitField(access, name, descriptor, signature, value));
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions)
	{
		//Secure: The name, descriptor, signature, and thrown exceptions don't have an effect on security.
		//This is true even for overridden methods.
		return new CheckingMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions));
	}

	@Override
	public void visitEnd()
	{
		//Secure: no information contained
		super.visitEnd();
	}

	//TODO evaluate the safety of all classfile parts
	public static <R> R throwUnimplemented(String messsage)
	{
		throw new CheckFailedException("Unknown class file part encountered; treating as insecure: " + messsage);
	}
}
