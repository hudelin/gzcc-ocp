package com.hdl.gzccocpcore.service.serviceImpl;



import com.hdl.gzccocpcore.dto.NoteDTO;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.NoteRepository;
import com.hdl.gzccocpcore.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note,Long> implements NoteService {

    @Autowired
    private NoteRepository noteRepository;


    @Override
    public Note update(Note note) throws Exception {

        Note old =noteRepository.getOne(note.getId());
        if(!StringUtils.isEmpty(note.getContent()) ){
            old.setContent(note.getContent());
        }
        if(!StringUtils.isEmpty(note.getTitle())){
            old.setTitle(note.getTitle());
        }
        if(!StringUtils.isEmpty(note.getNoteType())){
            old.setNoteType(note.getNoteType());
        }
        if(note.getTop()!=null){
            old.setTop(note.getTop());
        }
        old.setResource(note.getResource());

        return noteRepository.save(old);
    }

    @Override
    public Page<Note> findNotePage(NoteDTO noteDTO, Integer page, Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Note> notePage=findAll(noteDTO,pageable);
        return notePage;
    }

    @Override
    public void deleteLogic(Long id) throws Exception {
        Note note=get(id);
        note.setDeleted(true);
        noteRepository.save(note);
    }

    private Page<Note> findAll(NoteDTO noteDTO, Pageable pageable)throws Exception {
        Page<Note> notePage= noteRepository.findAll(new Specification<Note>() {
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate  = cb.equal(root.get("deleted"),false);
//                if(!StringUtils.isEmpty(roleName)){
//                    try {
//                        ListJoin<Note, User> join= root.join(root.getModel().getList("user",User.class), JoinType.LEFT);
//                        predicate = cb.and(cb.equal(join.get("name"), roleName),predicate);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                Join<Note,User> join= root.join(root.getModel().getSingularAttribute("user",User.class), JoinType.LEFT);
                if(null != noteDTO ) {
                    if(noteDTO.getId()!=null){
                        predicate = cb.and(cb.equal(root.get("id"), noteDTO.getId()),predicate);
                    }
                    if(noteDTO.getUserId()!=null){
                        try {
                            predicate = cb.and(cb.equal(join.get("id"), noteDTO.getUserId()),predicate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!StringUtils.isEmpty(noteDTO.getUsername())){
                        try {
                            predicate = cb.and(cb.like(join.get("username"), "%" +noteDTO.getUsername()+ "%"),predicate);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!StringUtils.isEmpty(noteDTO.getTitle())){
                        predicate = cb.and(cb.like(root.get("title"), "%" + noteDTO.getTitle() + "%"),predicate);
                    }
                    if(!StringUtils.isEmpty(noteDTO.getNoteType())){
                        predicate = cb.and(cb.equal(root.get("noteType"),  noteDTO.getNoteType() ),predicate);
                    }
                }
                if(null != predicate) query.where(predicate);
                return null;
            }
        },pageable);
        return notePage;
    }
}
