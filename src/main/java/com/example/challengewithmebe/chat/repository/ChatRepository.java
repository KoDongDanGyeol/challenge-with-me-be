package com.example.challengewithmebe.chat.repository;

import com.example.challengewithmebe.chat.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatRoomIdOrderByCreatedAt(Long chatRoomId);
}
