package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.Note;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NoteRepository extends BaseRepository<Note, Long> {

    List<Note> findByUserId(Long userId);
}
