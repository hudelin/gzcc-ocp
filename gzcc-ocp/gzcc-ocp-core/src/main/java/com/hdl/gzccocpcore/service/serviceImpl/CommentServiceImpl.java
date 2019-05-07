package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.constant.OcpErrorConstant;
import com.hdl.gzccocpcore.entity.Comment;
import com.hdl.gzccocpcore.entity.Resource;
import com.hdl.gzccocpcore.exception.BaseException;
import com.hdl.gzccocpcore.repository.CommentRepository;
import com.hdl.gzccocpcore.repository.ResourceRepository;
import com.hdl.gzccocpcore.service.CommentService;
import com.hdl.gzccocpcore.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {

    @Value("${gzcc.ocp.web.root-path}")
    private String rootPath;
    @Value("${gzcc.ocp.web.path}")
    private String path;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public List<Comment> findByResourceId(Long resourceId) throws Exception {
        return commentRepository.findByResourceId(resourceId);
    }

    @Override
    public Comment update(Comment comment) throws Exception {
        return null;
    }
}
