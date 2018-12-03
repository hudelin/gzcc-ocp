package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.Note;

public interface NoteRepository extends BaseRepository<Note, Long> {

    Note findByUserId(Long id);
}
