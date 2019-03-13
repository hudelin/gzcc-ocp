package com.hdl.gzccocpcore.repository;


import com.hdl.gzccocpcore.entity.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface MajorRepository extends BaseRepository<Major, Long>{


    Major findByName(String name);

    Page<Major> findAll(Specification specification, Pageable pageable);



}
