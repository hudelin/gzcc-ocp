package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.BaseConstant;
import com.hdl.gzccocpcore.dto.ReplyDTO;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.ReplyRepository;
import com.hdl.gzccocpcore.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReplyServiceImpl extends BaseServiceImpl<Reply,Long> implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public Reply update(Reply reply) throws Exception {
        Reply old = get(reply.getId());
        if (!StringUtils.isEmpty(reply.getContent())) {
            old.setContent(reply.getContent());
        }
        if (!StringUtils.isEmpty(reply.getContent())) {
            old.setContent(reply.getContent());
        }
        if(reply.getAccepted()!=null){
            old.setAccepted(reply.getAccepted());
        }
        return replyRepository.save(old);
    }

    @Override
    public Page<Reply> findPageByNoteId(Integer page, Integer size, Long noteId) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Reply> replyPage=replyRepository.findByNoteIdAndDeletedOrderByAcceptedDescCreateTimeDesc(pageable,noteId,false);
        return replyPage;
    }

    @Override
    public Page<Reply> findReplyPage(ReplyDTO replyDTO, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Reply> replyPage=findAll(replyDTO,pageable);
        return replyPage;
    }

    private Page<Reply> findAll(ReplyDTO replyDTO, Pageable pageable)throws Exception {
        Page<Reply> replyPage= replyRepository.findAll(new Specification<Reply>() {
            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate  = cb.equal(root.get("deleted"),false);

                Join<Reply, User> userJoin= root.join(root.getModel().getSingularAttribute("user",User.class), JoinType.LEFT);
                Join<Reply, Note> noteJoin= root.join(root.getModel().getSingularAttribute("note",Note.class), JoinType.LEFT);
                if(null != replyDTO ) {
                    if(replyDTO.getId()!=null){
                        predicate = cb.and(cb.equal(root.get("id"), replyDTO.getId()),predicate);
                    }
                    if(replyDTO.getUserId()!=null){
                        try {
                            predicate = cb.and(cb.equal(userJoin.get("id"), replyDTO.getUserId()),predicate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(replyDTO.getNoteId()!=null){
                        try {
                            predicate = cb.and(cb.equal(noteJoin.get("id"), replyDTO.getNoteId()),predicate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!StringUtils.isEmpty(replyDTO.getUsername())){
                        try {
                            predicate = cb.and(cb.like(userJoin.get("username"), "%" +replyDTO.getUsername()+ "%"),predicate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(null != predicate) query.where(predicate);
                return null;
            }
        },pageable);
        return replyPage;
    }

//    @Override
//    public Reply acceptReply(Long replyId, Long noteId) throws Exception {
//
//        if(replyRepository.findByNoteIdAndAccepted(noteId, true)!=null){
//            Reply acceptReplyOld=replyRepository.findByNoteIdAndAccepted(noteId,true);
//            acceptReplyOld.setAccepted(false);
//            replyRepository.save(acceptReplyOld);
//        }
//        Reply acceptReplyNew=replyRepository.getOne(replyId);
//        acceptReplyNew.setAccepted(true);
//        replyRepository.save(acceptReplyNew);
//        return acceptReplyNew;
//    }

    @Override
    public Reply praiseReply(Long replyId, Long userId) throws Exception {
        Reply reply = get(replyId);
        //判断是否有已有点赞
        if (!StringUtils.isEmpty(reply.getPraiseUserIdString())) {
            String[] userIdList = reply.getPraiseUserIdString().split(",");
            List<String> list = new ArrayList<>();
            Boolean exist=false;
            for (String s : userIdList) {
                if(!userId.toString().equals(s)){
                    list.add(s);
                }else{
                    exist=true;
                }
            }
            if(!exist){
                list.add(userId.toString());
            }
            userIdList = list.toArray(new String[list.size()]);
            reply.setPraiseUserIdString(StringUtils.arrayToDelimitedString(userIdList, ","));
            reply.setPraise((long) list.size());
        } else {
            reply.setPraiseUserIdString(userId.toString());
            reply.setPraise((long) 1);
        }
        return null;
    }

    @Override
    public Reply changeAccept(Long id) throws Exception {
        Reply reply=get(id);
        if(reply.getAccepted()!=null){
            reply.setAccepted(!reply.getAccepted());
        }else{
            reply.setAccepted(true);
        }
        replyRepository.save(reply);
        return reply;
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        Reply reply=replyRepository.getOne(id);
        reply.setDeleted(true);
        replyRepository.save(reply);
    }

}
