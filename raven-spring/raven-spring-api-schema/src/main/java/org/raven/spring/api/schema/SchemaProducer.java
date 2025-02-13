package org.raven.spring.api.schema;

import lombok.NonNull;
import org.raven.commons.data.MutableDescribable;
import org.raven.commons.util.ClassUtils;
import org.raven.commons.util.CollectionUtils;
import org.raven.commons.util.Maps;
import org.raven.commons.data.MemberFormatType;
import org.raven.commons.data.MemberFormatUtils;
import org.raven.commons.util.StringUtils;
import org.raven.spring.api.schema.spi.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * date 2018/8/31
 *
 * @author liangyi
 */
@Slf4j
public class SchemaProducer {

    private final @Nullable WebHandlerProvide webHandlerInfoProvide;
    private final @Nullable JavadocProvide javadocProvide;
    private final @Nullable ConstraintProvide constraintProvide;

    private final Set<String> baseClassSet;

    private final String loadPackageRoot;
    private final Set<Class<? extends Annotation>> serviceFindByAnnotationSet;
    private final Set<Class<? extends Annotation>> operationFilterByAnnotationSet;
    private final Set<Class<? extends Annotation>> operationIgnoreByAnnotationSet;
    private final Set<Class<? extends Annotation>> memberIgnoreByAnnotationSet;
    private final Set<Class<? extends Annotation>> paramFilterByAnnotationSet;
    private final Class<?> enumInterface;
    private final Set<String> excludeClassSet;

    private final String version;

    private final List<ServiceType> serviceTypes;

    private final Map<String, ClassType> modelTypesMap;

    public SchemaProducer(
            @NonNull String loadPackageRoot,
            WebHandlerProvide webHandlerInfoProvide,
            JavadocProvide javadocProvide,
            ConstraintProvide constraintProvide,
            Set<Class<? extends Annotation>> serviceFindByAnnotationSet,
            Set<Class<? extends Annotation>> operationFilterByAnnotationSet,
            Set<Class<? extends Annotation>> operationIgnoreByAnnotationSet,
            Set<Class<? extends Annotation>> memberIgnoreByAnnotationSet,
            Set<Class<? extends Annotation>> paramFilterByAnnotationSet,
            Class<?> enumInterface,
            Set<String> excludeClassSet,
            String version) {

        modelTypesMap = new HashMap<>();
        serviceTypes = new ArrayList<>();

        baseClassSet = new LinkedHashSet<String>() {{
            add("java.*");
            add("org.springframework.*");
            add(void.class.getName());
            add(char.class.getName());
            add(byte.class.getName());
            add(short.class.getName());
            add(int.class.getName());
            add(long.class.getName());
            add(float.class.getName());
            add(double.class.getName());
            add(boolean.class.getName());
        }};

        this.webHandlerInfoProvide = webHandlerInfoProvide;
        this.javadocProvide = javadocProvide;
        this.constraintProvide = constraintProvide;

        this.loadPackageRoot = loadPackageRoot;
        this.serviceFindByAnnotationSet = serviceFindByAnnotationSet;
        this.operationFilterByAnnotationSet = operationFilterByAnnotationSet;
        this.operationIgnoreByAnnotationSet = operationIgnoreByAnnotationSet;
        this.memberIgnoreByAnnotationSet = memberIgnoreByAnnotationSet;
        this.paramFilterByAnnotationSet = paramFilterByAnnotationSet;
        this.enumInterface = enumInterface;
        this.excludeClassSet = excludeClassSet;
        this.version = version;

        this.init();
    }

    private void init() {

        List<Class<?>> ctrlList = ClassUtils.getAllClassByAnnotation(serviceFindByAnnotationSet, loadPackageRoot);

        //类
        for (Class<?> ctrl : ctrlList) {

            if (Modifier.isAbstract(ctrl.getModifiers()) || isExclude(ctrl)) {
                continue;
            }

            ServiceType service = null;

            //方法列表
            Method[] methods = ctrl.getDeclaredMethods();
            for (Method method : methods) {

                boolean needed = false;

                if (!CollectionUtils.isEmpty(operationFilterByAnnotationSet)) {

                    for (Class<? extends Annotation> aClass : operationFilterByAnnotationSet) {
                        Annotation annotation = MethodUtils.getAnnotation(method, aClass, true, false);
                        needed = annotation != null;
                        if (needed) break;
                    }
                } else {
                    needed = true;
                }

                if (!needed) {
                    continue;
                }

                boolean ignore = false;
                if (!CollectionUtils.isEmpty(operationIgnoreByAnnotationSet)) {

                    for (Class<? extends Annotation> aClass : operationIgnoreByAnnotationSet) {
                        Annotation annotation = MethodUtils.getAnnotation(method, aClass, true, false);
                        if (annotation != null) {
                            ignore = true;
                            break;
                        }
                    }
                }

                if (ignore) {
                    continue;
                }


                if (service == null) {
                    service = new ServiceType();

                    buildDeclaration(service, ctrl);

                    serviceTypes.add(service);
                }

                service.setType(ctrl.getName());

                final OperationType operation = new OperationType();
                service.getOperations().add(operation);
                operation.setName(method.getName());

                if (webHandlerInfoProvide != null) {
                    WebHandlerDescribable webHandlerInfo = webHandlerInfoProvide.getWebHandlerDeclaration(ctrl, method);
                    if (webHandlerInfo != null) {
                        operation.setPaths(webHandlerInfo.getPaths());
                        operation.setMethods(webHandlerInfo.getMethods());
                    }
                }

                buildDeclaration(operation, ctrl, method);

                //region

//                    MemberType result = new MemberType();
//                    result.setType(resultModel.getType());
//                    buildGenericType(result, method.getGenericReturnType());
//
//                    if (method.getReturnType().isArray()) {
//                        result.setIsArray(true);
//                        result.setType(method.getReturnType().getComponentType());
//
//                    } else {
//                        result.setType(method.getReturnType());
//                    }
//
//                    // 泛型类型字段
//                    Type genericType = method.getGenericReturnType();
//                    if (!method.getReturnType().getName().equals(genericType.getTypeName())) {
//                        result.setGenericReturnTypeName(genericType.getTypeName());
//                    }

                //endregion

                MemberType result = buildMember(
                        Maps.newLinkedHashMap(), method.getReturnType(),
                        method.getGenericReturnType(), method.getName()
                );

                ModelType resultModel = new ModelType();

                if (result != null) {
                    buildModel(resultModel, result.getType());
                    buildGenericType(resultModel, result.getType().getGenericSuperclass());
                }

                operation.setResult(result);

                //参数列表
                Parameter[] parameters = method.getParameters();
                Optional<Parameter> paraOptional = Arrays.stream(parameters)
                        .filter(f -> {
                            if (CollectionUtils.isNotEmpty(paramFilterByAnnotationSet)) {
                                return paramFilterByAnnotationSet.contains(f.getType());
                            }
                            return true;
                        })
                        .findFirst();

                if (!paraOptional.isPresent() && method.getParameters().length > 0) {
                    paraOptional = Optional.of(parameters[0]);
                }

                paraOptional.ifPresent(param -> {

                    MemberType paramType = buildMember(
                            Maps.newLinkedHashMap(), param.getType(),
                            param.getParameterizedType(), param.getName()
                    );

                    if (paramType != null) {

                        ModelType paramModel = new ModelType();
                        buildModel(paramModel, paramType.getType());
                        buildGenericType(paramModel, paramType.getType().getGenericSuperclass());
                    }


                    operation.setParam(paramType);

                });


            }
        }
    }

    public ApiSchema toSchema() {
        return new ApiSchema(version, serviceTypes, modelTypesMap);
    }

    private boolean isExclude(Class<?> clazz) {

        return modelTypesMap.containsKey(clazz.getName())
                || ClassUtils.match(clazz.getName(), baseClassSet)
                || ClassUtils.match(clazz.getName(), excludeClassSet);


    }

    private boolean isEnums(Class<?> clazz) {

        return Enum.class.isAssignableFrom(clazz)
                || (enumInterface != null && enumInterface.isAssignableFrom(clazz));

    }

    private void buildGenericType(BasicType basicType, Type type) {

        if (type == null) {
            return;
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = ((ParameterizedType) type);

            int length = parameterizedType.getActualTypeArguments().length;
            List<BasicType> genericTypes = new ArrayList<>(length);

            for (Type generic : parameterizedType.getActualTypeArguments()) {

                if (generic instanceof ParameterizedType) {

                    BasicType genericType = new BasicType();
                    genericType.setType((Class<?>) ((ParameterizedType) generic).getRawType());

                    genericTypes.add(genericType);

                    buildModel(new ModelType(), genericType.getType());
                    buildGenericType(genericType, generic);

                } else if (generic instanceof TypeVariable<?>) {
                    TypeVariable<?> typeVariable = ((TypeVariable<?>) generic);

                    BasicType genericType = new BasicType();
                    genericType.setGenericTypeName(typeVariable.getTypeName());

                    genericTypes.add(genericType);

                } else if (generic instanceof Class) {
                    buildModel(new ModelType(), (Class<?>) generic);

                    BasicType genericType = new BasicType();

                    genericType.setType(
                            (Class<?>) generic
                    );

                    genericTypes.add(genericType);
                }
            }

            if (!genericTypes.isEmpty()) {
                basicType.setGenericTypes(genericTypes);
            }

        }
    }

    private void buildEnums(Class<?> clazz) {
        if (!isEnums(clazz)) {
            return;
        }

        EnumType enumType = new EnumType();
        enumType.setType(clazz);
        modelTypesMap.put(clazz.getName(), enumType);

        buildDeclaration(enumType, clazz);

        Map<String, EnumType.EnumValue> enumValues = new LinkedHashMap<>();
        enumType.setEnums(enumValues);

        BasicType enumSuperType = new BasicType();

        if (enumInterface != null) {

//            if(clazz.getGenericSuperclass() != null && )

            Type type = ClassUtils.getGenericSuperclass(clazz, enumInterface);
            if (type != null) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = ((ParameterizedType) type);
                    enumSuperType.setType((Class<?>) parameterizedType.getRawType());
                } else {
                    enumSuperType.setType((Class<?>) type);
                }

                buildGenericType(enumSuperType, type);

                if (enumSuperType.getType().isInterface()) {
                    enumType.setImplement(enumSuperType);
                } else {
                    enumType.setExtend(enumSuperType);
                }

            }

//            else {
//                parameterizedType = ClassUtils.getGenericSuperclass(clazz, enumInterface);
//
//                if (parameterizedType != null) {
//                    enumSuperType.setType((Class<?>) parameterizedType.getRawType());
//                    buildGenericType(enumSuperType, clazz.getGenericSuperclass());
//
//                    enumType.setExtend(enumSuperType);
//                }
//
//            }

            Method valueMethod = null;

            for (Method interfaceMethod : enumInterface.getDeclaredMethods()) {

                if (interfaceMethod.getParameterCount() == 0) {

                    try {
                        valueMethod = clazz.getMethod(interfaceMethod.getName());

                        Class<?> returnType = (Class<?>) ClassUtils.getGenericReturnType(clazz, valueMethod);

                        enumType.setEnumType(returnType);

                        if (returnType.equals(String.class)) {
                            enumType.setString(true);
                        } else {
                            enumType.setString(false);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                }

            }

            for (Field declaredField : clazz.getDeclaredFields()) {
                if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(clazz)) {
                    try {
                        Object valueType = declaredField.get(declaredField);
                        if (valueMethod != null) {
                            EnumType.EnumValue enumValue = new EnumType.EnumValue();

                            enumValue.setName(declaredField.getName());
                            enumValue.setValue(valueMethod.invoke(valueType));

                            buildDeclaration(enumValue, clazz, declaredField);

                            enumValues.put(enumValue.getName(), enumValue);
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }

        }

        if (enumSuperType.getType() == null && Enum.class.isAssignableFrom(clazz)) {
            enumType.setEnumType(String.class);
            enumType.setString(true);

            for (Object enumConstant : clazz.getEnumConstants()) {
                String name = ((Enum) enumConstant).name();

                EnumType.EnumValue enumValue = new EnumType.EnumValue();

                enumValue.setName(name);
                enumValue.setValue(name);

//                if (javadocProvide != null) {
//                    MemberDeclaration memberDeclaration = javadocProvide.getFieldDeclaration(clazz, declaredField);
//                    if (memberDeclaration != null) {
//                        enumValue.setDescription(memberDeclaration.getDescription());
//                    }
//                }

                enumValues.put(enumValue.getName(), enumValue);
            }
        }
    }

    private void buildModel(BasicType basicType, Class<?> clazz) {

        basicType.setType(clazz);
        if (isExclude(clazz)) {
            return;
        }

        if (isEnums(clazz)) {
            buildEnums(clazz);
            return;
        }

//        ModelType modelType = new ModelType();

//        Element modelEle = root.addElement(Label.model.name());
//        modelEle.addAttribute(Label.type.name(), classNameProcess(clazz.getName()));
//        modelType.setName(clazz.getName());

        modelTypesMap.put(clazz.getName(), basicType);

        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            if (!superClazz.equals(Object.class)) {

                BasicType superType = new BasicType();
                superType.setType(superClazz);

                if (clazz.getGenericSuperclass() != null) {
                    buildGenericType(superType, clazz.getGenericSuperclass());
                }

                basicType.setExtend(superType);
            }

            ModelType modelType = new ModelType();
            buildModel(modelType, superClazz);
        }

//        if (Collection.class.isAssignableFrom(clazz)) {
//            buildGenericType(basicType, clazz.getGenericSuperclass());
//        }

        if (basicType instanceof ModelType) {
            ModelType modelType = ((ModelType) basicType);

            List<BasicType> genericTypes = new ArrayList<>();
            for (TypeVariable<? extends Class<?>> typeVariable : clazz.getTypeParameters()) {

                BasicType genericType = new BasicType();
                genericType.setGenericTypeName(typeVariable.getTypeName());

                genericTypes.add(genericType);
//                typeNames.add(typeVariable.getTypeName());
            }

            if (!genericTypes.isEmpty()) {
                basicType.setGenericTypes(genericTypes);
            }

            buildDeclaration(modelType, clazz);

            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {

                boolean ignore = false;
                if (!CollectionUtils.isEmpty(memberIgnoreByAnnotationSet)) {

                    for (Class<? extends Annotation> aClass : memberIgnoreByAnnotationSet) {
                        Annotation annotation = MethodUtils.getAnnotation(method, aClass, true, false);
                        if (annotation != null) {
                            ignore = true;
                            break;
                        }
                    }
                }

                if (ignore) {
                    continue;
                }

                String methodName = ClassUtils.methodMemberName(method);
                if (StringUtils.isNotBlank(methodName)) {
                    MemberType memberType = buildMember(modelType.getMembers(), method.getReturnType(), method.getGenericReturnType(), methodName);

                    if (memberType != null) {
                        buildDeclaration(memberType, clazz, method);
                        buildConstraint(memberType, clazz, method);
                    }
                }
            }
        }

    }

    private @Nullable MemberType buildMember(Map<String, MemberType> members, Class<?> type, Type genericType, String
            memberName) {
        MemberType memberType = new MemberType();

        memberType.setName(memberName);
        memberType.setType(type);

        if (type.isArray()) {
            memberType.setIsArray(true);
            memberType.setType(type.getComponentType());

        } else {
            memberType.setType(type);
        }

        // 解决泛型继承字段
        MemberType exists = members.get(memberType.getName());
        if (exists == null || exists.getType().isAssignableFrom(memberType.getType())) {
            members.put(memberType.getName(), memberType);
        } else {
            return null;
        }

        // 泛型类型字段
        buildGenericType(memberType, genericType);
        if (CollectionUtils.isEmpty(memberType.getGenericTypes()) && !type.getName().equals(genericType.getTypeName())) {
            memberType.setGenericTypeName(genericType.getTypeName());
        }

        buildModel(new ModelType(), memberType.getType());
        return memberType;
    }

    public void buildDeclaration(MutableDescribable declaration, Class<?> clazz) {

        if (javadocProvide != null) {
            MemberMutableDescribable memberDescribable = javadocProvide.getTypeDeclaration(clazz);
            if (memberDescribable != null) {
                declaration.setDescription(memberDescribable.getDescription());
            }
        }
    }

    public void buildDeclaration(MutableDescribable declaration, Class<?> clazz, Method method) {

        if (javadocProvide != null) {
            MemberMutableDescribable memberDescribable = javadocProvide.getMethodDeclaration(clazz, method);
            if (memberDescribable != null) {
                declaration.setDescription(memberDescribable.getDescription());
            } else {
                String methodName = ClassUtils.methodMemberName(method);
                try {
                    if (methodName != null) {

                        Field field = clazz.getDeclaredField(MemberFormatUtils.namingFormat(methodName, MemberFormatType.CamelCase));
                        buildDeclaration(declaration, clazz, field);
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void buildDeclaration(MutableDescribable declaration, Class<?> clazz, Field field) {

        if (javadocProvide != null) {
            MemberMutableDescribable memberDescribable = javadocProvide.getFieldDeclaration(clazz, field);
            if (memberDescribable != null) {
                declaration.setDescription(memberDescribable.getDescription());
            }
        }
    }

    public void buildConstraint(MemberType memberType, Class<?> clazz, Method method) {

        if (constraintProvide != null) {

            ConstraintType constraintType = new ConstraintType();
            constraintType.setNullable(
                    constraintProvide.isNullable(clazz, method)
            );
            memberType.setConstraint(constraintType);

        }
    }


    /*@Test
    public void domTest() {


        System.out.println(boolean.class);
        System.out.println(Boolean.class);

        Document document = DocumentHelper.createDocument();
        Element structure = document.addElement("structure");

        Element apis = structure.addElement("apis");
        Element service = apis.addElement("service");
        service.addAttribute("type", "com.orderdish.openplatform.api.controller.DiancanOrderQueryController");

        print(document);

    }*/


}