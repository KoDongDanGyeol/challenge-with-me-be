package com.example.challengewithmebe.ide.util;

import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.UUID;

@Component
public class CompileBuilder {

    // todo AWS 서버에 저장
    private final static String path = "TODO_WRITE_AWS_PATH";

    public Object compileCode(String body) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String uuidPath = path + uuid + "/";

        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + "Solution.java");
        Class<?> clazz;
        ByteArrayOutputStream err= new ByteArrayOutputStream();
        PrintStream origErr = System.err;

        try {

            newFolder.mkdir();
            new FileWriter(sourceFile).append(body).close();

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            System.setErr(new PrintStream(err));

            int compileResult = compiler.run(null, null, null, sourceFile.getPath());
            if (compileResult == 1)
                return err.toString();

            // compile 된 클래스 가져오기
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(uuidPath).toURI().toURL()});
            clazz = Class.forName("Solution", true, classLoader);

            return clazz.getConstructor().newInstance();

        } catch (Exception e) {
            return null;
        } finally {
            System.setErr(origErr);

            if (sourceFile.exists())
                sourceFile.delete();
            if (newFolder.exists())
                newFolder.delete();
        }

    }

}
