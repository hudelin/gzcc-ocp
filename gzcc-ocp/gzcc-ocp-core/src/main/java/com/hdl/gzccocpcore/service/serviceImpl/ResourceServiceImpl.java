package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.Resource;

import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.ResourceRepository;
import com.hdl.gzccocpcore.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements ResourceService {

    @Value("${gzcc.ocp.web.root-path}")
    private String rootPath;

    @Autowired
    private ResourceRepository resourceRepository;

    private String prefix = "00";

    private int index = 0;

    @Override
    public Resource update(Resource resource) throws Exception {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource uploadResource(MultipartFile multipartFile,Long userId,String type) throws Exception {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BaseException(OcpErrorConstant.RESOURCE_IS_NULL, "资源为空！");
        }
        //获取文件的后缀，对文件进行重命名
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = gen() + OcpConstant.RESOURCE_TYPE_NOTE + "." + suffix;
        File f = new File(rootPath + fileName);
        multipartFile.transferTo(f);
        Resource resource = new Resource();
        resource.setSrc(fileName);
        resource.setResourceType(type);
        resource.setUserId(userId);
        resource.setFormatName(fileName);
        resource.setOriginalName(multipartFile.getOriginalFilename());
        resource.setSuffix(suffix);
        resourceRepository.save(resource);
        return resource;
    }

    private synchronized String gen() {
        StringBuilder sBuilder = new StringBuilder(prefix);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        sBuilder.append(sdf.format(new Date()));
//        String randomNum = RandomStringUtils.randomAlphanumeric(4).toUpperCase();//获取随机数
//        sBuilder.append(randomNum);
        DecimalFormat df = new DecimalFormat("0000");//补全4位数值
        sBuilder.append(df.format(index));
        index++;
        if (index >= 10000) {
            index = 0;
        }
        return sBuilder.toString();
    }
}
