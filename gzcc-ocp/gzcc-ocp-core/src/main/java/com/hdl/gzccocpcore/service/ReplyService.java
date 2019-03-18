package com.hdl.gzccocpcore.service;

import com.hdl.gzccocpcore.dto.ReplyDTO;
import com.hdl.gzccocpcore.entity.Reply;
import org.springframework.data.domain.Page;

public interface ReplyService extends BaseService<Reply,Long>  {
    public Page<Reply>findPageByNoteId(Integer page, Integer size, Long noteId) throws Exception ;

    Page<Reply> findReplyPage(ReplyDTO replyDTO, Integer page, Integer size) throws Exception;

//    public Reply acceptReply(Long replyId,Long noteId)throws Exception ;

    public Reply praiseReply(Long replyId,Long userId)throws Exception ;

    void deleteLogic(Long id)throws Exception;

    Reply changeAccept(Long id)throws Exception ;
}
