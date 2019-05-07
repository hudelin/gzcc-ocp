package com.hdl.gzccocpcore.service.serviceImpl;

import com.hdl.gzccocpcore.constant.OcpConstant;
import com.hdl.gzccocpcore.dto.MessageDTO;
import com.hdl.gzccocpcore.dto.SendDTO;
import com.hdl.gzccocpcore.dto.UserDTO;
import com.hdl.gzccocpcore.entity.Message;
import com.hdl.gzccocpcore.entity.Note;
import com.hdl.gzccocpcore.entity.User;
import com.hdl.gzccocpcore.repository.MessageRepository;
import com.hdl.gzccocpcore.service.FriendService;
import com.hdl.gzccocpcore.service.MessageService;
import com.hdl.gzccocpcore.service.NoteService;
import com.hdl.gzccocpcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message,Long> implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private NoteService noteService;

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
            //群聊
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

    @Override
    public void add(MessageDTO messageDTO) throws Exception {
        Message message=new Message();
        message.setFormUserId(messageDTO.getFromid());
        message.setToUserId(messageDTO.getToid());
        message.setRemark(messageDTO.getRemark());
        message.setContent(OcpConstant.ADD_CONTENT);
        message.setType(OcpConstant.CHAT_MESSAGE_TYPE_ADD);
        message.setStatus(OcpConstant.ADD_FRIEND);
        message.setGroupId(messageDTO.getGroupId());
        messageRepository.save(message);
    }

    @Override
    public List<Message> getReaderMessage(Long userId) throws Exception {
        Message message=new Message();
        message.setType(OcpConstant.CHAT_MESSAGE_TYPE_ADD);
        List<Message> messageList = findList(message, userId);
        return messageList;
    }

    @Override
    public Message agreeFriend(Long id) throws Exception {
        Message message=get(id);
        message.setStatus(OcpConstant.AGREE);
        messageRepository.save(message);
        Message messageBack=new Message();
        messageBack.setSystem(true);
        messageBack.setType(OcpConstant.CHAT_MESSAGE_TYPE_ADD);
        messageBack.setStatus(OcpConstant.AGREE);
        messageBack.setFormUserId(message.getToUserId());
        messageBack.setToUserId(message.getFormUserId());
        messageBack.setContent(userService.get(message.getToUserId()).getUsername()+" "+OcpConstant.AGREE_CONTENT);
        messageRepository.save(messageBack);
        friendService.addFriend(message.getToUserId(), message.getGroupId());
        return message;
    }

    @Override
    public void refuseFriend(Long id) throws Exception {
        Message message=get(id);
        message.setStatus(OcpConstant.REFUSE);
        messageRepository.save(message);
        Message messageBack=new Message();
        messageBack.setSystem(true);
        messageBack.setType(OcpConstant.CHAT_MESSAGE_TYPE_ADD);
        messageBack.setStatus(OcpConstant.REFUSE);
        messageBack.setFormUserId(message.getToUserId());
        messageBack.setToUserId(message.getFormUserId());
        messageBack.setContent(userService.get(message.getToUserId()).getUsername()+" "+OcpConstant.REFUSE_CONTENT);
        messageRepository.save(messageBack);
    }

    @Override
    public void reply(Long userId, Long noteId) throws Exception {
        Note note=noteService.get(noteId);
        if(userId!=note.getUser().getId()){
            Message message=new Message();
            message.setFormUserId(userId);
            message.setType(OcpConstant.CHAT_MESSAGE_TYPE_REPLY);
            message.setToUserId(note.getUser().getId());
            message.setContent(OcpConstant.REPLY_CONTENT);
        }
    }


    private List<Message> findList(Message message,Long userId)throws Exception {
        List<Message> messageList= messageRepository.findAll(new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate  = cb.equal(root.get("deleted"),false);
                if(null != message ) {
                    if(userId!=null){
                        predicate = cb.and(cb.equal(root.get("toUserId"), userId),predicate);
                    }
                }
                if (!StringUtils.isEmpty(message.getType())) {
                    predicate = cb.and(cb.equal(root.get("type"), message.getType()), predicate);
                }
                if(null != predicate) query.where(predicate);
                return null;
            }
        },new Sort(Sort.Direction.DESC,"createTime"));
        return messageList;
    }
}
