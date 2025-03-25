package com.app.api.service.interfaces;

import com.app.api.model.BotChat;

import java.util.List;

public interface IBotChatService {
    public List<BotChat> listChat();
    public boolean addChat(String Question,String Answer);
    public boolean updateChat(int idChat,String KeyQuestionOrAnswer,String QuestionOrAnswer);
    public boolean deleteChat(int idChat,String KeyQuestionOrAnswer);
}
