package com.hdl.gzccocpcore.repository;


import com.hdl.gzccocpcore.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ReplyRepository extends BaseRepository<Reply, Long> {

    Page<Reply> findByNoteIdAndDeletedOrderByAcceptedDescCreateTimeDesc(Pageable pageable,Long noteId,Boolean deleted);
    List<Reply> findByNoteIdAndAccepted( Long noteId,Boolean accepted);

}
