package com.app.api.repository;

import com.app.api.model.BotChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBotChatRepository extends JpaRepository<BotChat,Integer> {

}
