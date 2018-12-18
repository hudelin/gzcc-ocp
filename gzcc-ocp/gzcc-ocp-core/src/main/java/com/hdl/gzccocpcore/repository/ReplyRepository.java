package com.hdl.gzccocpcore.repository;


import com.hdl.gzccocpcore.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReplyRepository extends BaseRepository<Reply, Long> {

    Page<Reply> findByNoteIdAndIsDeleteOrderByIsAcceptDescCreateTimeDesc(Pageable pageable,Long noteId,String isDelete);
    Reply findByNoteIdAndIsAccept( Long noteId,String isAccept);

}
