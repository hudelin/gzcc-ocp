package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.dto.SendDTO;
import com.hdl.gzccocpcore.dto.UserDTO;
import com.hdl.gzccocpcore.entity.Message;
import com.hdl.gzccocpcore.repository.MessageRepository;
import com.hdl.gzccocpcore.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message,Long> implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message update(Message message) throws Exception {
        return messageRepository.save(message);
    }


    @Override
    public List<MessageDTO> createMessage(SendDTO sendDTO) throws Exception {

        UserDTO mine = sendDTO.getData().getMine();
        UserDTO to = sendDTO.getData().getTo();

        List<MessageDTO> messageDTOList=new ArrayList<>();
        if(OcpConstant.CHAT_MESSAGE_TYPE_FRIEDN_MESSAGE.equals(to.getType())){
            //新增一条聊天记录
            Message message=createMessage(mine,to,OcpConstant.CHAT_MESSAGE_TYPE_FRIEDN_MESSAGE);
            //组装前端接收聊天对象
            MessageDTO messageDTO=createMessageDTO(message,mine,to,OcpConstant.CHAT_MESSAGE_TYPE_FRIEDN_MESSAGE);
            messageDTO.setId(mine.getId());
            messageDTOList.add(messageDTO);

        }else if(OcpConstant.CHAT_MESSAGE_TYPE_GROUP_MESSAGE.equals(to.getType())){
            for(UserDTO userDTO:to.getUserList()){
                //新增一条聊天记录
                Message message=createMessage(mine,userDTO,OcpConstant.CHAT_MESSAGE_TYPE_GROUP_MESSAGE);
                //组装前端接收聊天对象
                MessageDTO messageDTO=createMessageDTO(message,mine,userDTO,OcpConstant.CHAT_MESSAGE_TYPE_GROUP_MESSAGE);
                messageDTO.setId(to.getId());
                messageDTOList.add(messageDTO);
            }
        }
        return messageDTOList;
    }

    private Message createMessage(UserDTO mine, UserDTO to,String type){
        Message message=new Message();
        message.setFormUserId(mine.getId());
        message.setContent(mine.getContent());
        message.setType(type);
        if(mine.getId().equals(to.getId())){
            message.setMine(true);
        }
        message.setToUserId(to.getId());
        messageRepository.save(message);
        return message;
    }

    private MessageDTO createMessageDTO(Message message, UserDTO mine, UserDTO to,String type){
        MessageDTO messageDTO=new MessageDTO();
        messageDTO.setUsername(mine.getUsername());
        messageDTO.setAvatar(mine.getAvatar());
        messageDTO.setType(type);
        messageDTO.setContent(mine.getContent());
        messageDTO.setCid(message.getId());
        messageDTO.setTimestamp(message.getCreateTime().getTime());
        if(mine.getId().equals(to.getId())){
            messageDTO.setMine(true);
        }
        messageDTO.setFromid(mine.getId());
        messageDTO.setToid(to.getId());
        return messageDTO;

    }

    @Override
    public void readMessage(Long id) throws Exception {
        Message message=get(id);
        message.setReadStatus(true);
        messageRepository.save(message);
    }
}
