package com.jrouter.processors;

import com.google.auto.service.AutoService;
import com.jrouter.annotation.Autowired;
import com.jrouter.annotation.RouterNode;
import com.jrouter.node.Node;
import com.jrouter.constants.Constants;
import com.jrouter.util.ClassPathUtils;
import com.jrouter.util.JLogger;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
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
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 14:28.
 * Function :
 */
@AutoService(Processor.class)
@SupportedOptions(Constants.MODULE_NAME)
@SupportedAnnotationTypes(Constants.ANNOTATION_ROUTER_NODE)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterNodeProcessors extends AbstractProcessor {

    private String mModuleName;
    private Filer mFiler;
    private Types mTypes;
    private Elements mElements;
    private JLogger mJLogger;
    private HashMap<String, ArrayList<Node>> mHashMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mTypes = processingEnv.getTypeUtils();
        mElements = processingEnv.getElementUtils();
        Messager messager = processingEnvironment.getMessager();
        mJLogger = new JLogger(messager);
        mHashMap = new HashMap<>();
        Map<String, String> stringMap = processingEnvironment.getOptions();
        if (stringMap != null && !stringMap.isEmpty()) {
            mModuleName = stringMap.get(Constants.MODULE_NAME);
        } else {
            mModuleName = "DEFAULT";
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null && !set.isEmpty()) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(RouterNode.class);
            if (elements != null && !elements.isEmpty()) {
                try {
                    parseNodes(elements);
                    generateRouterAdressImpl();
                    generateRouterUiImpl();
                } catch (IOException e) {
                    mJLogger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    private void parseNodes(Set<? extends Element> elements) {
        TypeMirror type_Activity = mElements.getTypeElement(Constants.ACTIVITY).asType();
        for (Element element : elements) {
            if (mTypes.isSubtype(element.asType(), type_Activity)) {
                TypeElement typeElement = (TypeElement) element;
                RouterNode routerNode = typeElement.getAnnotation(RouterNode.class);

                Node node = new Node();
                node.path = routerNode.path();
                node.group = routerNode.group();
                node.desc = routerNode.desc();
                node.element = element;
                node.className = typeElement.getSimpleName().toString();
                node.packageName = typeElement.getQualifiedName().toString();

                checkClassName(node);
                saveNode(node);
            }
        }
    }

    private void checkClassName(Node node) {
        String className = node.className;
        int i = 1;
        while (isHasOfClassName(className)) {
            className = node.className + i;
            ++i;
        }
        node.className = className;
    }

    private boolean isHasOfClassName(String className) {
        for (ArrayList<Node> nodes : mHashMap.values()) {
            for (Node node : nodes) {
                if (node.className.equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void saveNode(Node node) {
        checkPath(node);
        if (mHashMap.containsKey(node.group)) {
            savePathToList(node, mHashMap.get(node.group), 0);
        } else {
            ArrayList<Node> nodes = new ArrayList<>();
            nodes.add(node);
            mHashMap.put(node.group, nodes);
        }
    }

    private void checkPath(Node node) {
        boolean isShouldCheck = false;
        if (node.path == null || node.path.isEmpty()) {
            throw new IllegalArgumentException("path cannot be null or empty,this is:" + node.path);
        }
        if (!node.path.startsWith("/")) {
            isShouldCheck = true;
            mJLogger.info("path should start with /,this is:" + node.path);
            node.path = "/" + node.path;
        }
        if (node.path.contains("//")) {
            isShouldCheck = true;
            mJLogger.info("path should not contain //,this is:" + node.path);
            node.path = node.path.replaceAll("//", "/");
        }
        if (node.path.contains("&")) {
            isShouldCheck = true;
            mJLogger.info("path should not contain &,this is:" + node.path);
            node.path = node.path.replaceAll("&", "");
        }
        if (node.path.contains("?")) {
            isShouldCheck = true;
            mJLogger.info("path should not contain ?,this is:" + node.path);
            node.path = node.path.replaceAll("\\?", "");
        }
        if (node.path.endsWith("/")) {
            isShouldCheck = true;
            mJLogger.info("path should not endWith /,this is:" + node.path);
            node.path = node.path.substring(0, node.path.length() - 1);
        }
        if (isShouldCheck) {
            checkPath(node);
        }
    }

    private void savePathToList(Node node, ArrayList<Node> nodes, int index) {
        String path = "";
        if (index == 0) {
            path = node.path;
        } else {
            path = node.path + index;
        }
        for (Node n : nodes) {
            if (path.equals(n.path)) {
                savePathToList(node, nodes, ++index);
                return;
            }
        }
        node.path = path;
        nodes.add(node);
    }

    /**
     * create ui manager
     */
    private void generateRouterUiImpl() throws IOException {
        String ui = ClassPathUtils.generateUiPath(mModuleName);
        String packageName = ui.substring(0, ui.lastIndexOf("."));
        String className = ui.substring(ui.lastIndexOf(".") + 1);

        ClassName supperClass = ClassName.get(mElements.getTypeElement(Constants.UI_SUPPERCLASS));

        MethodSpec uiMethodSpec = generateUiMethodSpec();

        TypeSpec.Builder builder = TypeSpec.classBuilder(className);
        builder.addModifiers(Modifier.PUBLIC).superclass(supperClass);

        builder.addMethod(uiMethodSpec);

        JavaFile.builder(packageName, builder.build()).build().writeTo(mFiler);
    }

    private MethodSpec generateUiMethodSpec() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Constants.METHOD_CM);
        builder.addAnnotation(Override.class);
        builder.addModifiers(Modifier.PROTECTED);
        builder.addParameter(String.class, Constants.PARAMETER_GROUP);

        Collection<ArrayList<Node>> nodes = mHashMap.values();
        for (ArrayList<Node> nodeArrayList : nodes) {
            builder.beginControlFlow("if(" + Constants.PARAMETER_GROUP + ".equals(" + "\"" + nodeArrayList.get(0).group + "\"" + "))");
            for (Node node : nodeArrayList) {
                builder.addStatement(Constants.PARAMETER_UI_MAPPER + ".put($S,$T.class)", generateRouterIp(node), ClassName.get((TypeElement) node.element));
            }
            builder.endControlFlow();
        }

        return builder.build();
    }

    /**
     * create ip list
     */
    private void generateRouterAdressImpl() throws IOException {
        String adress = ClassPathUtils.generateAdressPath(mModuleName);
        String packageName = adress.substring(0, adress.lastIndexOf("."));
        String className = adress.substring(adress.lastIndexOf(".") + 1);

        ArrayList<FieldSpec> packageFieldSpecs = generatePackageFields();
        ArrayList<FieldSpec> adressFieldSpecs = generateAdressFields();
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(className).addModifiers(Modifier.PUBLIC);
        for (int i = 0; i < adressFieldSpecs.size(); i++) {
            FieldSpec packageField = packageFieldSpecs.get(i);
            FieldSpec adressField = adressFieldSpecs.get(i);
            builder.addField(packageField);
            builder.addField(adressField);
        }
        TypeSpec typeSpec = builder.build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
    }

    private ArrayList<FieldSpec> generatePackageFields() {
        ArrayList<FieldSpec> fieldSpecs = new ArrayList<>();
        Collection<ArrayList<Node>> nodes = mHashMap.values();
        for (ArrayList<Node> nodeArrayList : nodes) {
            for (Node node : nodeArrayList) {
                FieldSpec.Builder builder = FieldSpec
                        .builder(String.class, node.className + ClassPathUtils.getEndPackage())
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", node.packageName);
                fieldSpecs.add(builder.build());
            }
        }
        return fieldSpecs;
    }

    private ArrayList<FieldSpec> generateAdressFields() {
        ArrayList<FieldSpec> fieldSpecs = new ArrayList<>();
        Collection<ArrayList<Node>> nodes = mHashMap.values();
        for (ArrayList<Node> nodeArrayList : nodes) {
            for (Node node : nodeArrayList) {
                FieldSpec.Builder builder = FieldSpec
                        .builder(String.class, node.className)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", generateRouterIp(node));
                if (node.desc != null && !node.desc.equals("")) {
                    builder.addJavadoc(node.desc);
                }
                fieldSpecs.add(builder.build());
            }
        }
        return fieldSpecs;
    }

    private String generateRouterIp(Node node) {
        return "JRouter://" + mModuleName + "/" + node.group + node.path;
    }
}
