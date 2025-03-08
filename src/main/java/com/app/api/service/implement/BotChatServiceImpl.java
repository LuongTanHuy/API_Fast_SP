package com.app.api.service.implement;

import com.app.api.model.BotChat;
import com.app.api.repository.IBotChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BotChatServiceImpl implements com.app.api.service.interfaces.IBotChatService {
    private final IBotChatRepository botChatRepository;

    @Autowired
    public BotChatServiceImpl(IBotChatRepository botChatRepository) {
        this.botChatRepository = botChatRepository;
    }

    @Override
    public List<BotChat> listChat() {
        return this.botChatRepository.findAll().stream().sorted(Comparator.comparing(BotChat::getId)).toList();
    }

    @Override
    public boolean addChat(String Question, String Answer) {
        if(!Question.isEmpty() && !Answer.isEmpty()){
            BotChat newChat = new BotChat();
            newChat.setTrainer(Question);
            newChat.setModel(Answer);
            this.botChatRepository.save(newChat);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateChat(int idChat,String KeyQuestionOrAnswer,String QuestionOrAnswer) {
        Optional<BotChat> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChat updateChat = getChat.get();
            if (KeyQuestionOrAnswer.equals("question")) {
                updateChat.setTrainer(QuestionOrAnswer);
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            } else if (KeyQuestionOrAnswer.equals("answer")) {
                updateChat.setModel(QuestionOrAnswer);
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean checkNull(int idChat){
        Optional<BotChat> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChat updateChat = getChat.get();
            if(updateChat.getModel().equals("") && updateChat.getTrainer().equals("")){
                this.botChatRepository.deleteById(idChat);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteChat(int idChat,String KeyQuestionOrAnswer) {
        Optional<BotChat> getChat = this.botChatRepository.findById(idChat);
        if (getChat.isPresent()) {
            BotChat updateChat = getChat.get();
            if (KeyQuestionOrAnswer.equals("question")) {
                updateChat.setTrainer("");
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            } else if (KeyQuestionOrAnswer.equals("answer")) {
                updateChat.setModel("");
                this.botChatRepository.save(updateChat);
                this.checkNull(idChat);
                return true;
            }

            return false;
        }
        return false;
    }

}
