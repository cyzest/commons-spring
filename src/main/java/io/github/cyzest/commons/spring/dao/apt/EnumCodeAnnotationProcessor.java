package io.github.cyzest.commons.spring.dao.apt;

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
 * EnumCode Annotation Processor
 */
public class EnumCodeAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.isEmpty()) {
            return true;
        }

        for (TypeElement typeElement : annotations) {
            if (typeElement.toString().equals(JpaEnumCodeConverter.class.getName())) {
                generateEnumCodeConverter(roundEnv, typeElement);
            } else if (typeElement.toString().equals(MybatisEnumCodeHandler.class.getName())) {
                generateEnumCodeHandler(roundEnv, typeElement);
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(JpaEnumCodeConverter.class.getName());
        supportedAnnotationTypes.add(MybatisEnumCodeHandler.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private void generateEnumCodeConverter(RoundEnvironment roundEnv, TypeElement typeElement) {

        for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

            Name className = element.getSimpleName();

            String source = "package " + element.getEnclosingElement() + ";\r\n"
                + "import io.github.cyzest.commons.spring.dao.EnumCodeConverter;\r\n"
                + "import javax.persistence.Converter;\r\n"
                + "@Converter(autoApply = true)\r\n"
                + "public class " + className + "EnumCodeConverter extends EnumCodeConverter<" + className + "> {\r\n"
                + "public " + className + "EnumCodeConverter() {\r\n"
                + "super(" + className + ".class);\r\n"
                + "}\r\n"
                + "}\r\n";

            createSourceFile(element + "EnumCodeConverter", source);
        }
    }

    private void generateEnumCodeHandler(RoundEnvironment roundEnv, TypeElement typeElement) {

        for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {

            Name className = element.getSimpleName();

            String source = "package " + element.getEnclosingElement() + ";\r\n"
                + "import io.github.cyzest.commons.spring.dao.EnumCodeHandler;\r\n"
                + "import org.apache.ibatis.type.MappedTypes;\r\n"
                + "@MappedTypes(" + className + ".class)\r\n"
                + "public class " + className + "EnumCodeHandler extends EnumCodeHandler<" + className + "> {\r\n"
                + "public " + className + "EnumCodeHandler() {\r\n"
                + "super(" + className + ".class);\r\n"
                + "}\r\n"
                + "}\r\n";

            createSourceFile(element + "EnumCodeHandler", source);
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
