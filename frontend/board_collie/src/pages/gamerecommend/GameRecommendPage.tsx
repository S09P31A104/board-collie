/* eslint-disable */

import React, { useState, useEffect } from 'react';
import { TypeAnimation } from 'react-type-animation';
import 'animate.css';

// images
import chatIcon from '../../assets/chat_icon.png';

// styles
import styled from 'styled-components';

// icon
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';

import ChatBubble from '../../components/chatbubble/ChatBubble';

/**
 * Game Recommend Page
 *
 * @author 허주혁
 * @todo 
 * 1. ChatBubble 안의 내용을 지웠다가 다시 쓰는 듯한 효과 구현 (불완전 : 첫페이지만 안 됨 & 같은 내용도 지웠다가 다시 쓰는 문제점)
 * 2. 버튼을 backOutDown animation 효과 부여, 3개가 한 개처럼 이동하다가 각자 정해진 위치에 멈추는 형식으로
 * 3. 질문마다 버튼 안의 내용도 바뀌도록
 * 4. 주사위 숫자 아이콘 삽입
 * 5. 종료 시점에서 API POST 처리
 */

const Padding = styled.div`
  height: 12vh;
`;

const ChatIcon = styled.div`
  background-image: url(${chatIcon});
  background-size: contain;
  background-repeat: no-repeat;
  width: 18vw;
  height: 20vh;
  display: block;

  position: absolute;
  top: 20vh;
  left: 10vw;
`;

const PositionedChatBubble = styled.div`
  position: absolute;
  top: 26vh;
  left: 28vw;
`;

const SelectButtonContainer = styled.div`
  display: grid;
  grid-template-rows: repeat(3, 1fr);
  gap: 20px;
  place-items: center;
  height: 300px;
  position: absolute;
  left: 50%;
  top: 70%;
  transform: translate(-50%, -50%);
`;

const SingleButton = styled.button`
  width: 80vw; 
  height: 9vh; 
  background-color: #3CB371;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 20px;
  cursor: pointer;

  &:hover {
    background-color: #90B299;
  }
`;

const PageNumber = styled.div`
  position: absolute;
  top: 93%;
  left: 93%;
`;

const BackButton = styled.div`
position: absolute;
top: 93%;
left: 7%;
`;

const GameRecommendPage: React.FC = () => {
  // 질문의 대답을 저장하는 리스트
  const [selectedButtons, setSelectedButtons] = useState<number[]>([]);
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [showButtons, setShowButtons] = useState(false);
  const [handleClicked, setHandleClicked] = useState(false);

  // 질문의 대답을 저장하는 리스트 로그
  useEffect(() => {
    console.log("선택된 버튼 배열: ", selectedButtons);
  }, [selectedButtons]);

  // useEffect를 활용하여 처음에 5초 동안 기다리기
  useEffect(() => {
    if (currentQuestion === 0) {
      setTimeout(() => {
        setCurrentQuestion(1);
        setShowButtons(true);
      }, 7000);
    }

    if (currentQuestion === 6) {
      setShowButtons(false);
    }

    console.log(currentQuestion);

  }, [currentQuestion]);

  // 초기 메시지와 마지막 메시지를 별도로 관리
  const initialMessage = '안녕하세요! AI 집사, 보드콜리 집사입니다.\n사용자님께 맞는 최적의 게임을 추천해드리기 위해 간단한 질문 몇 개만 하겠습니다.';
  const lastMessage = '사용자님 마음에 들 게임들이 있습니다!\n후보들을 보여드릴테니, 천천히 둘러보시길 바랍니다.';

  // ChatBubble 안의 내용 문구들 상태 변수 저장
  const [chatMessage, setChatMessage] = useState([
    '보드게임 플레이 타임은 어느 정도로 생각하시나요?',
    '게임 난이도는 어느 정도를 생각하시나요?',
    '이제부터 게임 스타일에 대한 선택지를 드리겠습니다.\n선호하시는 플레이 스타일을 선택하세요.',
  ]);

  const handleClick = (buttonNumber: number) => {
    setHandleClicked(true);

    // 선택된 버튼을 배열에 추가
    setSelectedButtons([...selectedButtons, buttonNumber]);

    // 다음 질문으로 이동
    setCurrentQuestion(currentQuestion + 1);

    // 새로운 문구
    const newChatMessage = [...chatMessage];
    switch (currentQuestion + 1) {
      case 1:
        newChatMessage[currentQuestion] = '보드게임 플레이 타임은 어느 정도로 생각하시나요?';
        break;
      case 2:
        newChatMessage[currentQuestion] = '게임 난이도는 어느 정도를 생각하시나요?';
        break;
      case 3:
      case 4:
      case 5:
        newChatMessage[currentQuestion] = '이제부터 게임 스타일에 대한 선택지를 드리겠습니다.\n선호하시는 플레이 스타일을 선택하세요.';
        break;
      case 6:
        newChatMessage[currentQuestion] = '사용자님 마음에 들 게임들이 있습니다!\n후보들을 보여드릴테니, 천천히 둘러보시길 바랍니다.';
        break;
      default:
        newChatMessage[currentQuestion] = '';
        break;
    }
    setChatMessage(newChatMessage);

    // setHandleClicked(false);
  };

  const handleBack = () => {
    // 마지막 선택을 배열에서 제거
    setSelectedButtons(selectedButtons.slice(0, -1));

    // 이전 질문으로 이동
    setCurrentQuestion(currentQuestion - 1);
  };

  // 애니메이션 효과 순서 지정
  const [zoomInDone, setZoomInDone] = useState(false);
  const [fadeInDone, setFadeInDone] = useState(false);
  const [startType, setStartType] = useState(false);

  useEffect(() => {
    // ZoomIn 애니메이션이 끝난 후
    const zoomInTimer = setTimeout(() => {
      setZoomInDone(true);
    }, 1000); // ZoomIn 애니메이션의 길이
    
    return () => clearTimeout(zoomInTimer);
  }, []);

  useEffect(() => {
    // FadeIn 애니메이션이 끝난 후
    if (zoomInDone) {
      const fadeInTimer = setTimeout(() => {
        setFadeInDone(true);
      }, 500); // FadeIn 애니메이션의 길이

      return () => clearTimeout(fadeInTimer);
    }
  }, [zoomInDone]);

  useEffect(() => {
    // TypeAnimation 시작
    if (fadeInDone) {
      setStartType(true);
    }
  }, [fadeInDone]);



  return (
    <>
      <Padding />
      <ChatIcon className={`animate__animated ${zoomInDone ? '' : 'animate__zoomIn'}`} />

      <PositionedChatBubble className={`animate__animated ${fadeInDone ? '' : (zoomInDone ? 'animate__fadeIn' : '')}`}>
        {startType && (
          <ChatBubble position='left'>
            {handleClicked ? (
              <TypeAnimation
                key={currentQuestion}
                preRenderFirstString={true}
                style={{whiteSpace: 'pre-line',}}
                sequence={[
                  chatMessage[currentQuestion - 2],
                  1000,
                  ' ',
                  currentQuestion === 0 ? initialMessage : 
                  (currentQuestion >= 1 && currentQuestion < 3 ? chatMessage[currentQuestion - 1] : 
                  (currentQuestion >= 3 && currentQuestion <= 5 ? chatMessage[2] : 
                  (currentQuestion === 6 ? lastMessage : ""))),
                  1000,
                  ]}
                  speed={70}
              />
            ) :
              <TypeAnimation
                key={currentQuestion}
                style={{whiteSpace: 'pre-line',}}
                sequence={[
                  currentQuestion === 0 ? initialMessage : 
                  (currentQuestion >= 1 && currentQuestion < 3 ? chatMessage[currentQuestion - 1] : 
                  (currentQuestion >= 3 && currentQuestion <= 5 ? chatMessage[2] : 
                  (currentQuestion === 6 ? lastMessage : ""))),
                  1000,
                  ]}
                  speed={70}
              />
            }
          </ChatBubble>
        )}
      </PositionedChatBubble>

      {showButtons && (
        <SelectButtonContainer>
          <SingleButton 
            style={selectedButtons[currentQuestion] === 1 ? {backgroundColor: '#90B299'} : {}}
            onClick={() => handleClick(1)}
          >
            버튼 1
          </SingleButton>
          <SingleButton 
            style={selectedButtons[currentQuestion] === 2 ? {backgroundColor: '#90B299'} : {}}
            onClick={() => handleClick(2)}
          >
            버튼 2
          </SingleButton>
          <SingleButton 
            style={selectedButtons[currentQuestion] === 3 ? {backgroundColor: '#90B299'} : {}}
            onClick={() => handleClick(3)}
          >
            버튼 3
          </SingleButton>
        </SelectButtonContainer>
      )}
      
      {currentQuestion !== 0 && currentQuestion !== 6 && (
        <>
          <PageNumber>
            {`${currentQuestion}/5`}
          </PageNumber>
          <BackButton>
            <ArrowBackIosNewIcon onClick={handleBack}/>
          </BackButton>
        </>
      )}
    </>
  );
};

export default GameRecommendPage;