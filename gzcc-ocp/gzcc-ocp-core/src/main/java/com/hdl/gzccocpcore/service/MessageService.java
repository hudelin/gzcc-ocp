package com.hdl.gzccocpcore.service;


import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.dto.SendDTO;
import com.hdl.gzccocpcore.entity.Friend;
import com.hdl.gzccocpcore.entity.Message;

import java.util.List;

public interface MessageService extends BaseService<Message,Long>  {

    public List<MessageDTO> createMessage(SendDTO sendDTO) throws  Exception;

    public void readMessage(Long id) throws  Exception;

    public void add(MessageDTO messageDTO) throws  Exception;

    public List<Message> getReaderMessage(Long userId) throws  Exception;

    public Message agreeFriend(Long id) throws  Exception;

    public void refuseFriend(Long id) throws  Exception;

    public void reply(Long userId,Long noteId)throws  Exception;


}
