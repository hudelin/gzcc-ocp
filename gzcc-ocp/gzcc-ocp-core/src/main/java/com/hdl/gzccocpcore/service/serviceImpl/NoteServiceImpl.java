package com.hdl.gzccocpcore.service.serviceImpl;



import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.repository.NoteRepository;
import com.hdl.gzccocpcore.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note,Long> implements NoteService {

    @Autowired
    private NoteRepository noteRepository;


    @Override
    public Note update(Note note) throws Exception {

        Note noteOld =noteRepository.getOne(note.getId());
        if(!StringUtils.isEmpty(note.getContent()) ){
            noteOld.setContent(note.getContent());
        }
        if(!StringUtils.isEmpty(note.getTitle())){
            noteOld.setTitle(note.getTitle());
        }
        if(!StringUtils.isEmpty(note.getNoteType())){
            noteOld.setNoteType(note.getNoteType());
        }
        noteOld.setResource(note.getResource());

        return noteRepository.save(noteOld);
    }
}
