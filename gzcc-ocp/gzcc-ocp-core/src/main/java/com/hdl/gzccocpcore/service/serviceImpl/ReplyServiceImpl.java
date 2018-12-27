package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.BaseConstant;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.repository.ReplyRepository;
import com.hdl.gzccocpcore.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.Field;

@Service
@Transactional
public class ReplyServiceImpl extends BaseServiceImpl<Reply,Long> implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public Reply update(Reply reply) throws Exception {
        return null;
    }

    @Override
    public Page<Reply> findPageByNoteId(Integer page, Integer size, Long noteId) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Reply> replyPage=replyRepository.findByNoteIdAndIsDeleteOrderByIsAcceptDescCreateTimeDesc(pageable,noteId,BaseConstant.FALSE);
        return replyPage;
    }

    @Override
    public Reply acceptReply(Long replyId, Long noteId) throws Exception {

        if(replyRepository.findByNoteIdAndIsAccept(noteId, BaseConstant.TRUE)!=null){
            Reply acceptReplyOld=replyRepository.findByNoteIdAndIsAccept(noteId,BaseConstant.TRUE);
            acceptReplyOld.setIsAccept(BaseConstant.FALSE);
            replyRepository.save(acceptReplyOld);
        }
        Reply acceptReplyNew=replyRepository.getOne(replyId);
        acceptReplyNew.setIsAccept(BaseConstant.TRUE);
        replyRepository.save(acceptReplyNew);
        return acceptReplyNew;
    }

    @Override
    public void delete(Long replyId) throws Exception {
        Reply acceptReplyNew=replyRepository.getOne(replyId);
        acceptReplyNew.setIsDelete(BaseConstant.TRUE);
        replyRepository.save(acceptReplyNew);
    }

}
