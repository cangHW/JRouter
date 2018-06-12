package com.jrouter.processors;

import com.google.auto.service.AutoService;
import com.jrouter.annotation.AutowiredNode;
import com.jrouter.constants.Constants;
import com.jrouter.node.ParamsNodeMessage;
import com.jrouter.util.ClassPathUtils;
import com.jrouter.util.JLogger;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import javax.swing.text.View;

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
    private HashMap<String,ArrayList<ParamsNodeMessage>> mParamsMap;
    private JLogger mJLogger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mJLogger = new JLogger(processingEnvironment.getMessager());
        mParamsMap=new HashMap<>();
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
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AutowiredNode.class);
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
            AutowiredNode autowiredNode = element.getAnnotation(AutowiredNode.class);
            ParamsNodeMessage message = new ParamsNodeMessage();
            message.key = autowiredNode.key().equals("") ? element.getSimpleName().toString() : autowiredNode.key();
            message.name = element.getSimpleName().toString();
//            message.isView=autowiredNode.isView();
//            message.viewId=autowiredNode.viewId();
            message.desc = autowiredNode.desc();
            message.isCanEmpty = autowiredNode.canEmpty();
            message.parentElement = parentElement;
            message.element = element;
            message.type = element.asType().toString();
            message.className = parentElement.getSimpleName().toString();
            message.packageName = parentElement.getQualifiedName().toString().substring(0, parentElement.getQualifiedName().toString().lastIndexOf("."));

            if (mParamsMap.containsKey(message.packageName+message.className)){
                mParamsMap.get(message.packageName+message.className).add(message);
            }else {
                ArrayList<ParamsNodeMessage> list=new ArrayList<>();
                list.add(message);
                mParamsMap.put(message.packageName+message.className,list);
            }
        }
    }

    /**
     * create autowiredImpl
     */
    private void generateAutowiredImpl() throws IOException {
        Collection<ArrayList<ParamsNodeMessage>> collection=mParamsMap.values();
        for (ArrayList<ParamsNodeMessage> arrayList:collection){
            createJavaFile(arrayList);
        }
    }

    private void createJavaFile(ArrayList<ParamsNodeMessage> list) throws IOException {
        String packageName = ClassPathUtils.generateAutowiredPackageName();
        TypeSpec.Builder builder = TypeSpec.classBuilder(ClassPathUtils.generateAutowiredClassName(list.get(0).packageName, list.get(0).className))
                .addSuperinterface(ClassName.get(mElements.getTypeElement(Constants.AUTOWIRED_SUPPERINTERFACE)))
                .addModifiers(Modifier.PUBLIC);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.METHOD_AUTOWIRED)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, Constants.PARAMETER_OBJECT)
                .addAnnotation(Override.class)
                .addStatement("$T $L = ($T) " + Constants.PARAMETER_OBJECT,
                        ClassName.get((TypeElement) list.get(0).parentElement),
                        list.get(0).className,
                        ClassName.get((TypeElement) list.get(0).parentElement));

//        MethodSpec.Builder viewMethodBuilder=MethodSpec.methodBuilder(Constants.METHOD_GETVIEW)
//                .addModifiers(Modifier.PUBLIC)
//                .returns(View.class)
//                .addParameter(ClassName.get((TypeElement) list.get(0).parentElement),"o")
//                .addParameter(Integer.class,"id");

        for (ParamsNodeMessage message : list) {
            if (message.isView){
                addCodeForView(methodBuilder,message);
            }else {
                addCode(methodBuilder, message);
            }
        }

        builder.addMethod(methodBuilder.build());
//        builder.addMethod(viewMethodBuilder.build());
        TypeSpec typeSpec = builder.build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
    }

    private void addCodeForView(MethodSpec.Builder builder, ParamsNodeMessage message){
        builder.addStatement("$S.$S=($T)getView($L,$L)"
                ,message.className
                ,message.name
                ,ClassName.get(mElements.getTypeElement(message.type))
                ,message.className
                ,message.viewId);
    }

    private void addCode(MethodSpec.Builder builder, ParamsNodeMessage message) {
        switch (message.type) {
            case Constants.TYPE_BOOLEAN:
                builder.addStatement("$L.$L=$L.getIntent().getBooleanExtra($S)", message.className, message.name, message.className, message.key);
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
