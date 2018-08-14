package com.cyzest.commons.spring.dao.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * EnumTypeAnnotationProcessor
 */
public class EnumTypeAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.isEmpty()) {
            return true;
        }

        for (TypeElement typeElement : annotations) {
            if (typeElement.toString().equals(JpaEnumTypeConverter.class.getName())) {
                generateEnumTypeConverter(roundEnv, typeElement);
            } else if (typeElement.toString().equals(MybatisEnumTypeHandler.class.getName())) {
                generateEnumTypeHandler(roundEnv, typeElement);
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(JpaEnumTypeConverter.class.getName());
        supportedAnnotationTypes.add(MybatisEnumTypeHandler.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private void generateEnumTypeConverter(RoundEnvironment roundEnv, TypeElement typeElement) {

        for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

            Name className = element.getSimpleName();

            String source = "package " + element.getEnclosingElement() + ";\r\n"
                    + "import com.cyzest.commons.spring.dao.EnumTypeConverter;\r\n"
                    + "import javax.persistence.Converter;\r\n"
                    + "@Converter(autoApply = true)\r\n"
                    + "public class " + className + "EnumTypeConverter extends EnumTypeConverter<" + className + "> {\r\n"
                    + "public " + className + "EnumTypeConverter() {\r\n"
                    + "super(" + className + ".class);\r\n"
                    + "}\r\n"
                    + "}\r\n";

            createSourceFile(element + "EnumTypeConverter", source);
        }
    }

    private void generateEnumTypeHandler(RoundEnvironment roundEnv, TypeElement typeElement) {

        for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

            Name className = element.getSimpleName();

            String source = "package " + element.getEnclosingElement() + ";\r\n"
                    + "import com.cyzest.commons.spring.dao.EnumTypeHandler;\r\n"
                    + "import org.apache.ibatis.type.MappedTypes;\r\n"
                    + "@MappedTypes(" + className + ".class)\r\n"
                    + "public class " + className + "EnumTypeHandler extends EnumTypeHandler<" + className + "> {\r\n"
                    + "public " + className + "EnumTypeHandler() {\r\n"
                    + "super(" + className + ".class);\r\n"
                    + "}\r\n"
                    + "}\r\n";

            createSourceFile(element + "EnumTypeHandler", source);
        }
    }

    private void createSourceFile(String sourcePath, String source) {
        try {
            Filer filer = super.processingEnv.getFiler();
            JavaFileObject javaFile = filer.createSourceFile(sourcePath);
            try (Writer writer = javaFile.openWriter()) {
                writer.write(source);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
