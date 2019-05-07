package com.hdl.gzccocpcore.service.serviceImpl;


import com.hdl.gzccocpcore.dto.NoteDTO;
import com.hdl.gzccocpcore.dto.ReplyDTO;
import com.hdl.gzccocpcore.entity.Major;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.Reply;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.NoteRepository;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.ReplyService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note, Long> implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    @Override
    public Note update(Note note) throws Exception {

        Note old = noteRepository.getOne(note.getId());
        if (!StringUtils.isEmpty(note.getContent())) {
            old.setContent(note.getContent());
        }
        if (!StringUtils.isEmpty(note.getTitle())) {
            old.setTitle(note.getTitle());
        }
        if (!StringUtils.isEmpty(note.getNoteType())) {
            old.setNoteType(note.getNoteType());
        }
        if (note.getTop() != null) {
            old.setTop(note.getTop());
        }
        old.setResource(note.getResource());

        return noteRepository.save(old);
    }

    @Override
    public Page<Note> findNotePage(NoteDTO noteDTO, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page - 1, size,new Sort(Sort.Direction.DESC,"createTime"));
        Page<Note> notePage = findAll(noteDTO, pageable);
        return notePage;
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        Note note = get(id);
        note.setDeleted(true);
        noteRepository.save(note);
    }

    @Override
    public List<Note> findNoteTop(Long majorId) throws Exception {
        Sort sort=new Sort(Sort.Direction.DESC,"top").and(new Sort(Sort.Direction.DESC,"createTime"));
        Pageable pageable = PageRequest.of(0, 4,sort);
        NoteDTO noteDTO=new NoteDTO();
        noteDTO.setMajorId(majorId);
        noteDTO.setTop(true);
        Page<Note> notePage = findAll(noteDTO, pageable);
        return notePage.getContent();
    }

    @Override
    public void updateReplyCount(Long noteId) throws Exception {
        ReplyDTO replyDTO=new ReplyDTO();
        replyDTO.setNoteId(noteId);
        Page<Reply> replyPage = replyService.findReplyPage(replyDTO, 1, 10);
        Note note=get(noteId);
        note.setReplyCount(replyPage.getTotalElements());
        noteRepository.save(note);
    }

    @Override
    public List<Note> findReplyTop(Long  majorId) throws Exception {
        Pageable pageable = PageRequest.of(0, 10,new Sort(Sort.Direction.DESC,"replyCount"));
        NoteDTO noteDTO=new NoteDTO();
        noteDTO.setMajorId(majorId);
        Page<Note> notePage = findAll(noteDTO, pageable);
        List<Note> noteList=notePage.getContent();
        return noteList;
    }

    @Override
    public List<Note> findMyNote(Long userId) throws Exception {
        List<Note> noteList= noteRepository.findAll(new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate  = cb.equal(root.get("deleted"),false);
                Join<Note, User> userJoin = root.join(root.getModel().getSingularAttribute("user", User.class), JoinType.LEFT);
                if (userId!=null) {
                    predicate = cb.and(cb.equal(userJoin.get("id"), userId), predicate);
                }
                if(null != predicate) query.where(predicate);
                return null;
            }
        },new Sort(Sort.Direction.DESC,"createTime"));
        return noteList;
    }

    @Override
    public List<Note> findMyCollect(Long userId) throws Exception {
        User user=userService.get(userId);
        List<Note> noteList=new ArrayList<>();
        String[] noteIdList = user.getCollectNote().split(",");
        for (String s : noteIdList) {
            Note note=get(Long.valueOf(s));
            noteList.add(note);
        }
        return noteList;
    }

    private Page<Note> findAll(NoteDTO noteDTO, Pageable pageable) throws Exception {
        Page<Note> notePage = noteRepository.findAll(new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("deleted"), false);
                Join<Note, User> userJoin = root.join(root.getModel().getSingularAttribute("user", User.class), JoinType.LEFT);
                Join<Note, Major> majorJoin = root.join(root.getModel().getSingularAttribute("major", Major.class), JoinType.LEFT);
                if (null != noteDTO) {
                    if (noteDTO.getId() != null) {
                        predicate = cb.and(cb.equal(root.get("id"), noteDTO.getId()), predicate);
                    }
                    if (noteDTO.getMajorId() != null) {
                        predicate = cb.and(cb.equal(majorJoin.get("id"), noteDTO.getMajorId()), predicate);
                    }
                    if (noteDTO.getUserId() != null) {
                        predicate = cb.and(cb.equal(userJoin.get("id"), noteDTO.getUserId()), predicate);
                    }
                    if (!StringUtils.isEmpty(noteDTO.getUsername())) {
                        predicate = cb.and(cb.like(userJoin.get("username"), "%" + noteDTO.getUsername() + "%"), predicate);
                    }
                    if (noteDTO.getTop() != null) {
                        predicate = cb.and(cb.equal(root.get("top"), noteDTO.getTop()), predicate);
                    }
                    if (!StringUtils.isEmpty(noteDTO.getTitle())) {
                        predicate = cb.and(cb.like(root.get("title"), "%" + noteDTO.getTitle() + "%"), predicate);
                    }
                    if (!StringUtils.isEmpty(noteDTO.getNoteType())) {
                        predicate = cb.and(cb.equal(root.get("noteType"), noteDTO.getNoteType()), predicate);
                    }
                }
                if (null != predicate) query.where(predicate);
                return null;
            }
        }, pageable);
        return notePage;
    }
}
