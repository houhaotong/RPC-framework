package com.hht.rpc.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扫描工具类
 *
 * @author hht
 * @date 2020/11/14 17:17
 */
public class MyClassUtil {

    /**
     * 获取最后的主启动类名
     * @return 主启动类类名
     */
    public static String getMainClass(){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[stackTrace.length-1].getClassName();
    }

    /**
     * 获取一个包下所有的类
     *
     * @param packageName 包的全限定名
     * @return set集合包含所有子类
     */
    public static Set<Class<?>> getClasses(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        String name = packageName.replace(".", "/");
        Enumeration<URL> resource;
        try {
            resource = Thread.currentThread().getContextClassLoader().getResources(name);
            while (resource.hasMoreElements()) {
                URL url = resource.nextElement();
                String protocol=url.getProtocol();
                //如果是文件
                if("file".equals(protocol)){
                    //获取包的物理地址
                    String path= URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
                    getFileClasses(path,packageName,classes);
                }else if("jar".equals(protocol)){
                    //获取jar
                    JarFile jarFile=((JarURLConnection)url.openConnection()).getJarFile();
                    //获取所有实体entry
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()){
                        //获取一个实体，可以是目录和一些jar包里的其他文件
                        JarEntry entry = entries.nextElement();
                        String entryName = entry.getName();
                        //如果是以/开头，去掉/
                        if(entryName.charAt(0)=='/'){
                            entryName=entryName.substring(1);
                        }
                        //如果以packageName开头，则说明是所需实体
                        if(entryName.startsWith(name)){
                            int i = entryName.lastIndexOf('/');
                            //有"/"代表是个包
                            if(i!=-1){
                                //获取包名
                                String jarName=entryName.substring(0,i).replace('/','.');
                                //以.class结尾，并且不是文件夹
                                if(entryName.endsWith(".class")&&!entry.isDirectory()){
                                    //获取类名，去除.class后缀
                                    String className = entryName.substring(jarName.length(), entryName.length() - 6);
                                    classes.add(Class.forName(jarName+"."+className));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void getFileClasses(String filePath,String packageName,Set<Class<?>> classes){
        File dir=new File(filePath);
        //如果不存在或者不是目录，直接结束
        if(!dir.exists()||!dir.isDirectory()){
            return ;
        }
        //获取目录下子文件或目录
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                //自定义过滤，留下目录和以.class后缀的文件
                return pathname.isDirectory()||pathname.getName().endsWith(".class");
            }
        });
        for (File file : files) {
            //如果是目录，则递归调用
            if(file.isDirectory()){
                getFileClasses(file.getAbsolutePath(),packageName+"."+file.getName(),classes);
            }else{
                //去除后面的.class，保留前面的文件名
                String className = file.getName().substring(0,file.getName().length()-6);
                try {
                    //添加到集合中
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName+"."+className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        MyClassUtil.getClasses("com.hht.rpc");
        System.out.println(MyClassUtil.getMainClass());
    }
}
