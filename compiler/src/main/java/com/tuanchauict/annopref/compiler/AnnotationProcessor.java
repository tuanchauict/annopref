package com.tuanchauict.annopref.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.Ignore;
import com.tuanchauict.annopref.annotation.Preference;
import com.tuanchauict.annopref.compiler.datastructure.PreferenceClass;
import com.tuanchauict.annopref.compiler.datastructure.PreferenceField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>(Arrays.asList(
                Preference.class.getCanonicalName(),
                Ignore.class.getCanonicalName(),
                Field.class.getCanonicalName()
        ));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        ArrayList<PreferenceClass> classes = new ArrayList<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Preference.class)) {
            TypeElement typeElement = (TypeElement) element;
            try {
                classes.add(buildPreferenceClass(typeElement));
            } catch (NoPackageNameException | IOException e) {
                error(typeElement, "Couldn't process class %s: %s", typeElement, e.getMessage());
            }
        }

        for (PreferenceClass preferenceClass : classes) {
            try {
                generate(preferenceClass);
            } catch (NoPackageNameException | IOException e) {
                error(preferenceClass.getTypeElement(), "Couldn't generate class");
            }
        }

        return false;
    }

    private PreferenceClass buildPreferenceClass(TypeElement typeElement) throws NoPackageNameException, IOException {
        PreferenceClass preferenceClass = new PreferenceClass(typeElement);
        for (Element element : typeElement.getEnclosedElements()) {
            if (!(element instanceof VariableElement)) {
                continue;
            }
            VariableElement variableElement = (VariableElement) element;

            if (!FieldValidator.isValid(variableElement)) {
                error(variableElement, "No support type: %s of %s from %s",
                        variableElement.asType().toString(),
                        element.toString(),
                        typeElement.getSimpleName().toString());
                break;
            }

            if (variableElement.getAnnotation(Ignore.class) != null) {
                continue;
            }
            Field field = variableElement.getAnnotation(Field.class);
            preferenceClass.addField(new PreferenceField(variableElement, field != null ? field.name() : null));
        }
        return preferenceClass;
    }

    private void generate(PreferenceClass cls) throws NoPackageNameException, IOException {
        String packageName = cls.getPackage(processingEnv.getElementUtils());
        TypeSpec generatedClass = CodeGenerator.generateClass(cls);
        JavaFile javaFile = JavaFile.builder(packageName, generatedClass).build();
        javaFile.writeTo(processingEnv.getFiler());
    }

    private void error(Element element, String mess, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(mess, args, element));
    }
}
