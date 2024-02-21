package com.example.challengewithmebe.chat.repository;

import com.example.challengewithmebe.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    void deleteAllByMemberId(Long memberId);
}
