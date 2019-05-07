package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.Comment;


import java.util.List;

public interface CommentService extends BaseService<Comment,Long>  {

    public List<Comment> findByResourceId(Long resourceId) throws Exception;
}
