package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import org.springframework.data.domain.Page;

public interface ReplyService extends BaseService<Reply,Long>  {
    public Page<Reply>findPageByNoteId(Integer page, Integer size, Long noteId) throws Exception ;

    public Reply acceptReply(Long replyId,Long noteId)throws Exception ;

    public void deleteReply(Long replyId,Long userId)throws Exception ;
}
