package com.hdl.gzccocpcore.service.serviceImpl;



import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.repository.NoteRepository;
import com.hdl.gzccocpcore.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note,Long> implements NoteService {

    @Autowired
    private NoteRepository noteRepository;


    @Override
    public Note update(Note note) throws Exception {
        return null;
    }
}
