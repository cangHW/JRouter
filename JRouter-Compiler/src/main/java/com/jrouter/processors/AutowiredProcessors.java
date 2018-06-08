package com.jrouter.processors;

import com.google.auto.service.AutoService;
import com.jrouter.annotation.ParamsNode;
import com.jrouter.constants.Constants;
import com.jrouter.node.ParamsNodeMessage;
import com.jrouter.util.ClassPathUtils;
import com.jrouter.util.JLogger;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 12:19.
 * Function :
 */
@AutoService(Processor.class)
@SupportedOptions(Constants.AUTOWIRED)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Constants.ANNOTATION_PARAMS_NODE)
public class AutowiredProcessors extends AbstractProcessor {

    private Elements mElements;
    private Filer mFiler;
    private String mAutowired;
    private ArrayList<ParamsNodeMessage> mParamsList;
    private JLogger mJLogger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mJLogger = new JLogger(processingEnvironment.getMessager());
        mParamsList = new ArrayList<>();
        Map<String, String> stringMap = processingEnvironment.getOptions();
        if (stringMap != null && !stringMap.isEmpty()) {
            mAutowired = stringMap.get(Constants.AUTOWIRED);
        }
        if (mAutowired == null || "".equals(mAutowired)) {
            mAutowired = "true";
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && !set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ParamsNode.class);
            if (elements != null && !elements.isEmpty()) {
                if ("true".equals(mAutowired)) {
                    try {
                        parseNode(elements);
                        generateAutowiredImpl();
                    } catch (IOException e) {
                        mJLogger.error(e);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void parseNode(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement parentElement = (TypeElement) element.getEnclosingElement();
            ParamsNode paramsNode = element.getAnnotation(ParamsNode.class);
            ParamsNodeMessage message = new ParamsNodeMessage();
            message.key = paramsNode.key().equals("") ? element.getSimpleName().toString() : paramsNode.key();
            message.name = element.getSimpleName().toString();
            message.desc = paramsNode.desc();
            message.isCanEmpty = paramsNode.canEmpty();
            message.parentElement = parentElement;
            message.element = element;
            message.type = element.asType().toString();
            message.className = parentElement.getSimpleName().toString();
            message.packageName = parentElement.getQualifiedName().toString().substring(0, parentElement.getQualifiedName().toString().lastIndexOf("."));

            mParamsList.add(message);
        }
    }

    /**
     * create autowiredImpl
     */
    private void generateAutowiredImpl() throws IOException {
        String packageName = ClassPathUtils.generateAutowiredPath();
        TypeSpec.Builder builder = TypeSpec.classBuilder(ClassPathUtils.generateAutowiredClassName(mParamsList.get(0).packageName, mParamsList.get(0).className))
                .addSuperinterface(ClassName.get(mElements.getTypeElement(Constants.AUTOWIRED_SUPPERINTERFACE)))
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.METHOD_AUTOWIRED)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, Constants.PARAMETER_OBJECT)
                .addAnnotation(Override.class)
                .addStatement("$T $L = ($T) " + Constants.PARAMETER_OBJECT,
                        ClassName.get((TypeElement) mParamsList.get(0).parentElement),
                        mParamsList.get(0).className,
                        ClassName.get((TypeElement) mParamsList.get(0).parentElement));

        for (ParamsNodeMessage message : mParamsList) {
            addCode(methodBuilder, message);
        }

        builder.addMethod(methodBuilder.build());
        TypeSpec typeSpec = builder.build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
    }

    private void addCode(MethodSpec.Builder builder, ParamsNodeMessage message) {
        switch (message.type) {
            case Constants.TYPE_BOOLEAN:
                builder.addStatement("$S.$S=getIntent().getBooleanExtra($S)", message.className, message.name, message.key);
                break;
            case Constants.TYPE_BYTE:
                builder.addStatement("$L.$L=$L.getIntent().getByteExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_DOUBEL:
                builder.addStatement("$L.$L=$L.getIntent().getDoubleExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_FLOAT:
                builder.addStatement("$L.$L=$L.getIntent().getFloatExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_INTEGER:
                builder.addStatement("$L.$L=$L.getIntent().getIntExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_LONG:
                builder.addStatement("$L.$L=$L.getIntent().getLongExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_SHORT:
                builder.addStatement("$L.$L=$L.getIntent().getShortExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_STRING:
                builder.addStatement("$L.$L=$L.getIntent().getStringExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_PARCELABLE:
                builder.addStatement("$L.$L=$L.getIntent().getParcelableExtra($S)", message.className, message.name, message.className, message.key);
                break;
            case Constants.TYPE_SERIALIZABLE:
                builder.addStatement("$L.$L=$L.getIntent().getSerializableExtra($S)", message.className, message.name, message.className, message.key);
                break;
        }
    }
}
