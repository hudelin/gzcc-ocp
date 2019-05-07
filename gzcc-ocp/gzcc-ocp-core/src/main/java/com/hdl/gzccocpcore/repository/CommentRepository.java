package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.Comment;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long>  {

    public List<Comment> findByResourceId(Long resourceId);
}
