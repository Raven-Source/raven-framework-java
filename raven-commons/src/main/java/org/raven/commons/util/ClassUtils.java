package org.raven.commons.util;

import org.raven.commons.data.MemberFormatType;
import org.raven.commons.data.MemberFormatUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * date 2022/9/27 15:38
 */
@SuppressWarnings("unchecked")
public class ClassUtils {

    /**
     * @param value
     * @param set
     * @return
     */
    public static boolean match(String value, Set<String> set) {
        for (String item : set) {
            if (Pattern.matches(item, value)) return true;
        }

        return false;
    }

    public static String methodMemberName(Method method) {

        Class<?> declaringClass = method.getDeclaringClass();
        if (!declaringClass.equals(Object.class)) {

            if (method.getName().startsWith("get")) {
                return method.getName().substring(3);
            } else if (method.getName().startsWith("is")) {
                return method.getName().substring(2);
            }
        }

        return null;
    }

    public static <A extends Annotation> A getMemberAnnotation(Class<?> clazz, Method method, Class<A> annotationClass) {
        A annotation = method.getAnnotation(annotationClass);

        if (annotation == null) {
            String methodName = methodMemberName(method);
            try {
                Field field = clazz.getDeclaredField(MemberFormatUtils.namingFormat(methodName, MemberFormatType.CamelCase));
                annotation = field.getAnnotation(annotationClass);

            } catch (NoSuchFieldException | SecurityException ex) {

            }
        }

        return annotation;
    }

    /**
     * 取得某个注解标记的所有类
     */
    public static <A extends Annotation> A getAnnotation(final Class<?> clazz, final Class<A> annotationCls
            , final boolean searchSupers) {

        A annotation = clazz.getAnnotation(annotationCls);

        if (annotation == null && searchSupers) {

            final Class<?>[] classes = clazz.getInterfaces();

            for (final Class<?> acls : classes) {
                annotation = getAnnotation(acls, annotationCls, searchSupers);
            }
        }

        return annotation;
    }

    public static Type getGenericSuperclass(Class<?> clazz, Class<?> superclass) {

        for (Type interfaceType : clazz.getGenericInterfaces()) {
            if (interfaceType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = ((ParameterizedType) interfaceType);
                if (superclass.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                    return interfaceType;
                }
            } else {
                if (superclass.isAssignableFrom((Class<?>) interfaceType)) {
                    return interfaceType;
                }
            }
        }

        if (clazz.getGenericSuperclass() != null) {
            if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = ((ParameterizedType) clazz.getGenericSuperclass());
                if (superclass.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                    return parameterizedType;
                }
            }
        }

        if (clazz.getSuperclass() != null) {
            return getGenericSuperclass(clazz.getSuperclass(), superclass);
//            if (superclass.isAssignableFrom(clazz.getSuperclass())) {
//                return clazz.getSuperclass();
//            }
        }

        return null;

    }

    public static Type getGenericReturnType(Class<?> clazz, Method method) {

        // Step 3: Get the return type of the getValue method
        Type returnType = method.getGenericReturnType();

        if (returnType instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable<?>) returnType;
            Type resolvedType = resolveTypeVariable(clazz, typeVariable);

            return resolvedType;

        } else {
            return returnType;
        }
    }


    private static Type resolveTypeVariable(Class<?> clazz, TypeVariable<?> typeVariable) {
        // Iterate through the class hierarchy to resolve the type variable
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            // Check the generic interfaces
            Type[] genericInterfaces = currentClass.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    TypeVariable<?>[] typeParameters = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();

                    for (int i = 0; i < typeParameters.length; i++) {
                        if (typeParameters[i].getName().equals(typeVariable.getName())) {
                            return actualTypeArguments[i];
                        }
                    }
                }
            }

            // Check the generic superclass
            Type genericSuperclass = currentClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();

                for (int i = 0; i < typeParameters.length; i++) {
                    if (typeParameters[i].getName().equals(typeVariable.getName())) {
                        return actualTypeArguments[i];
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return typeVariable;
    }

    /**
     * 取得某个注解标记的所有类
     */
    public static List<Class<?>> getAllClassByAnnotation(final Set<Class<? extends Annotation>> annotationClazzSet, final String packageName) {
        List<Class<?>> returnClassList = null;

        // 获取当前包下以及子包下所以的类
        List<Class<?>> allClass = getClasses(packageName);
        returnClassList = new ArrayList<>();
        for (Class<?> classes : allClass) {

            for (Annotation annotation : classes.getAnnotations()) {
                if (annotationClazzSet.contains(annotation.annotationType())) {
                    returnClassList.add(classes);
                    break;
                }
            }
        }

        return returnClassList;
    }

    /**
     * 取得某个接口下所有实现这个接口的类
     */
    public static List<Class<?>> getAllClassByInterface(final Class<?> interfaceClazz, final String packageName) {
        List<Class<?>> returnClassList = null;

        if (interfaceClazz.isInterface()) {

            // 获取当前包下以及子包下所以的类
            List<Class<?>> allClass = getClasses(packageName);
            returnClassList = new ArrayList<>();
            for (Class<?> classes : allClass) {
                // 判断是否是同一个接口
                if (interfaceClazz.isAssignableFrom(classes)) {
                    // 本身不加入进去
                    if (!interfaceClazz.equals(classes)) {
                        returnClassList.add(classes);
                    }
                }
            }
        }

        return returnClassList;
    }

    /**
     * 从包package中获取所有的Class
     */
    public static List<Class<?>> getClasses(final String packageName) {

        String _packageName = packageName;
        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = _packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()) {
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                //如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(_packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    //如果是jar包文件
                    //定义一个JarFile
                    JarFile jar;
                    try {
                        //获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        //从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        //同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            //如果是以/开头的
                            if (name.charAt(0) == '/') {
                                //获取后面的字符串
                                name = name.substring(1);
                            }
                            //如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                //如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    //获取包名 把"/"替换成"."
                                    _packageName = name.substring(0, idx).replace('/', '.');
                                }
                                //如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    //如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        //去掉后面的".class" 获取真正的类名
                                        String className = name.substring(_packageName.length() + 1, name.length() - 6);
                                        try {
                                            //添加到classes
                                            classes.add(Class.forName(_packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    public static void findAndAddClassesInPackageByFile(final String packageName,
                                                        final String packagePath,
                                                        final boolean recursive,
                                                        final List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirFiles = dir.listFiles((file) -> {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
        });
        if (dirFiles != null) {
            //循环所有文件
            for (File file : dirFiles) {
                //如果是目录 则继续扫描
                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                            file.getAbsolutePath(),
                            recursive,
                            classes);
                } else {
                    //如果是java类文件 去掉后面的.class 只留下类名
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        //添加到集合中去
                        classes.add(Class.forName(packageName + '.' + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
