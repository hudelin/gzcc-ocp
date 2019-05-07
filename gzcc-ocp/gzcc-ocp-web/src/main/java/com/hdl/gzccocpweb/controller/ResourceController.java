package com.hdl.gzccocpweb.controller;

import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.response.ObjectRestResponse;


import com.hdl.gzccocpcore.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/resource")
public class ResourceController {


    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/upload")
    @ResponseBody
    private ObjectRestResponse upload(MultipartFile file,Long userId,String type) throws Exception {
        Resource resource=resourceService.uploadResource(file,userId,type);
        return new ObjectRestResponse(resource);
    }

    @RequestMapping("/findById")
    @ResponseBody
    private ObjectRestResponse findById(Long id) throws Exception {
        Resource resource=resourceService.get(id);
        return new ObjectRestResponse(resource);
    }



    @Value("${gzcc.ocp.web.root-path}")
    private String rootPath;
    @RequestMapping("/download/{fileName}")
    @ResponseBody
    public ObjectRestResponse test(@PathVariable String fileName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        String fileName = "default.jpg";//被下载文件的名称
        String downloadFilePath = rootPath+fileName;//被下载的文件在服务器中的路径,
        File file = new File(downloadFilePath);
        if (file.exists()) {
            httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开            
            httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = httpServletResponse.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/downloadZip")
    public void imgDownload(String fileName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,String name) throws Exception {
        //存放--服务器上zip文件的目录
        String[] names=name.split(",");
//        String downloadFilePath = rootPath+fileName;
//        File directoryFile=new File(downloadFilePath);
//        if(!directoryFile.isDirectory() && !directoryFile.exists()){
//            directoryFile.mkdirs();
//        }
        //设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = formatter.format(new Date())+".zip";
        String strZipPath = rootPath+zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try{
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i<names.length ;i++){
                //解码获取真实路径与文件名
//                String realFileName = java.net.URLDecoder.decode(names[i],"UTF-8");
//                String realFilePath = java.net.URLDecoder.decode(paths[i],"UTF-8");
                String realFilePath=rootPath+names[i];
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if(file.exists())
                {
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */
                    ZipEntry zipEntry = new ZipEntry(names[i]);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
                    {
                        zipStream.write(buf, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream){
                    zipStream.flush();
                    zipStream.close();
                }
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            test(zipFileName,httpServletRequest,httpServletResponse);
            zipFile.delete();
        }
    }



}
