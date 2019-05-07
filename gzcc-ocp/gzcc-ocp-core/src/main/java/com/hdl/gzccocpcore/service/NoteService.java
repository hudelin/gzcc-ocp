package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.dto.NoteDTO;
import com.hdl.gzccocpcore.entity.Note;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoteService extends BaseService<Note,Long>  {

    Page<Note> findNotePage(NoteDTO noteDTO, Integer page, Integer size) throws Exception;

    void deleteLogic(Long id)throws Exception;

    List<Note> findNoteTop(Long majorId)throws Exception;

    void updateReplyCount(Long noteId) throws Exception;

    List<Note> findReplyTop(Long  majorId) throws Exception;

    List<Note> findMyNote(Long  userId) throws Exception;

    List<Note> findMyCollect(Long userId)throws Exception;
}
