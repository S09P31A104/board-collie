/* eslint-disable */

import React from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

// images
import iconLogo from '../../assets/logo.png'
import chatIcon from '../../assets/chat_icon.png';

// icon
import LogoutIcon from '@mui/icons-material/Logout';

// 채팅방 컨테이너
const ChatRoomContainer = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
`;

const Padding = styled.div`
  height: 9vh;
`;

// NavBar
const NavBarContainer = styled.div`
  width: 100%;
  height: 9vh;

  position: fixed;
  top: 0;
  left: 0;

  background-color: #EDFFD0;
  color: black;

  // NabBar 내부의 레이아웃 조정
  display: flex;
  justify-content: space-between; // NavBar 내의 콘텐츠를 균등하게 분배하여 간격 생성
  align-items: center; // NavBar 내 항목들 수직 가운데 정렬

  padding: 0 0px;
  box-shadow: 0px 1px 5px rgba(0, 0, 0, 0.2);
`;

const Logo = styled.div`
  background-image: url(${iconLogo});
  background-size: contain;
  background-repeat: no-repeat;
  width: 15vw;  
  height: 8vh;
  padding: 0 1vw;
`;

const GameName = styled.div`
  padding: 0 1vw;
  font-size: 1.3rem;
  font-weight: bold;
  fontFamily: 'Jolly Lodger, cursive',
`;

// 메시지 목록 영역
const MessageList = styled.div`
  padding: 10px;
  overflow-y: auto;
  flex: 1;
`;

// 각 메시지 항목
const QuestionMessageItem = styled.div`
  align-self: flex-end;
  background-color: #dcf8c6;
  padding: 5px 10px;
  border-radius: 7px;
  max-width: 70%;
  margin-bottom: 10px;
  white-space: pre-wrap;
`;

const AnswerMessageItem = styled.div`
  align-self: flex-start;
  background-color: #fff;
  padding: 5px 10px;
  border-radius: 7px;
  max-width: 70%;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  white-space: pre-wrap;
`;

const ChatIcon = styled.div`
  background-image: url(${chatIcon});
  background-size: contain;
  background-repeat: no-repeat;
  width: 8vw;
  height: 6vh;
  display: block;
`;

// 입력 영역
const InputArea = styled.div`
  display: flex;
  border-top: 1px solid #ccc;
`;

// 입력 필드
const InputField = styled.input`
  flex: 1;
  border: none;
  padding: 10px;
  &:focus {
    outline: none;
  }
`;

// 전송 버튼
const SendButton = styled.button`
  border: none;
  background-color: #007bff;
  color: white;
  padding: 10px 15px;
  cursor: pointer;
`;

interface Question {
  id: string;
  text: string;
}

interface Answer {
  id: string;
  text: string;
}

// 채팅방 컴포넌트
const ChatBotPage: React.FC = () => {
  
  const title = '스플랜더';

  // 종료 : 현재 창 닫기
  const handleClose = () => {
    window.close();
  };

  const [questions, setQuestions] = React.useState<Question[]>([
    { id: uuidv4(), text: '가져올 수 있는 노란 보석이 없으면 찜을 못 해?'},
  ]);

  const [answers, setAnswers] = React.useState<Answer[]>([
    { id: uuidv4(), text: `게임 : ${title}\n상세한 게임룰이나 헷갈리는 규칙이 있을 경우 질문 주세요!`},
    { id: uuidv4(), text: '골드 토큰이 없는 경우에도 예약 행동은 가능합니다. 단, 골드 토큰을 받지는 못하게 됩니다.'},
  ]);

  const [inputText, setInputText] = React.useState('');

  const handleSend = async () => {
    if (inputText.trim()) {
      const newQuestion: Question = {
        id: uuidv4(),
        text: inputText,
      };
      setQuestions([...questions, newQuestion]);

      try {
        // POST 요청으로 사용자의 메시지를 전송하고, 답변을 받습니다.
        const response = await axios.post('/api/message/send', { message: inputText });
        const receivedAnswer: Answer = {
          id: uuidv4(),
          text: response.data.reply, // 가정: 응답 데이터에 답변이 'reply' 필드에 있음
        };
        setAnswers(currentAnswer => [...currentAnswer, receivedAnswer]);
      } catch (error) {
        console.error('API 요청 중 오류가 발생했습니다', error);
      }

      setInputText('');
    }
  };

  // 질문과 답변을 번갈아 출력하는 함수
  const renderMessages = () => {
    const messageItems = [];
    
    // 항상 answers의 첫 번째 항목을 먼저 표시
    if (answers.length > 0) {
      messageItems.push(
        <AnswerMessageItem key={answers[0].id}>
          <ChatIcon style={{ backgroundImage: `url(${chatIcon})` }} />
          {answers[0].text}
        </AnswerMessageItem>
      );
    }

    // 그 다음부터는 questions와 answers를 번갈아가면서 표시
    for (let i = 0; i < questions.length; i++) {
      messageItems.push(
        <QuestionMessageItem key={questions[i].id}>
          {questions[i].text}
        </QuestionMessageItem>
      );

      if (answers[i + 1]) { // +1은 첫 번째 답변은 이미 표시했기 때문에
        messageItems.push(
          <AnswerMessageItem key={answers[i + 1].id}>
            <ChatIcon style={{ backgroundImage: `url(${chatIcon})` }} />
            {answers[i + 1].text}
          </AnswerMessageItem>
        );
      }
    }

    return messageItems;
  };

  return (
    <ChatRoomContainer>
      <Padding />

      <NavBarContainer>
        <Logo />
        <GameName>{`${title}`}</GameName>
        <LogoutIcon style={{ fontSize: 27, padding: '0 2vw'}} onClick={handleClose} />
      </NavBarContainer>

      <MessageList>
        {renderMessages()}
      </MessageList>

      <InputArea>
        <InputField
          value={inputText}
          onChange={(e) => setInputText(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && handleSend()}
        />
        <SendButton onClick={handleSend}>전송</SendButton>
      </InputArea>

    </ChatRoomContainer>
  );
};

export default ChatBotPage;
