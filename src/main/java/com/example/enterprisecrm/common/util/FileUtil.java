package com.example.enterprisecrm.common.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: az
 * @Data: 2021/10/26
 * 文件工具类
 */
public class FileUtil {

    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        //父目录不存在则创建
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //使用UUID重命名以免文件名重复
        String dest = filePath + fileName;
        FileOutputStream out = new FileOutputStream(dest);
        out.write(file);
        out.flush();
        out.close();
        return dest;
    }

}