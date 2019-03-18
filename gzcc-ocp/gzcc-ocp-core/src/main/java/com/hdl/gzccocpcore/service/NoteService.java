package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.dto.NoteDTO;
import com.hdl.gzccocpcore.entity.Note;
import org.springframework.data.domain.Page;

public interface NoteService extends BaseService<Note,Long>  {

    Page<Note> findNotePage(NoteDTO noteDTO, Integer page, Integer size) throws Exception;

    void deleteLogic(Long id)throws Exception;
}
