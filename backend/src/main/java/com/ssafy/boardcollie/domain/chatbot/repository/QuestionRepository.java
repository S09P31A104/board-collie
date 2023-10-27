package com.ssafy.boardcollie.domain.chatbot.repository;

import com.ssafy.boardcollie.domain.chatbot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
