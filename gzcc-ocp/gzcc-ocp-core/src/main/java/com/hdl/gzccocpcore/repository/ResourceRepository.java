package com.hdl.gzccocpcore.repository;

import com.hdl.gzccocpcore.entity.Resource;

public interface ResourceRepository extends BaseRepository<Resource, Long>  {

    public Resource findByFormatName(String formatName);
}
