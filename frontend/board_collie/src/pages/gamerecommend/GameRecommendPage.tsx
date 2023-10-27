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
            <TypeAnimation 
              sequence={[
                '안녕하세요! AI 집사, 보드콜리 집사입니다. 사용자님께 맞는 최적의 게임을 추천해드리기 위해 간단한 질문 몇 개만 하겠습니다.',
              ]}
              speed={70}
            />
          </ChatBubble>
        )}
      </PositionedChatBubble>

      <SelectButtonContainer>
        <SingleButton>버튼 1</SingleButton>
        <SingleButton>버튼 2</SingleButton>
        <SingleButton>버튼 3</SingleButton>
      </SelectButtonContainer>

      <PageNumber>
        {'1/5'}
      </PageNumber>

      <BackButton>
        <ArrowBackIosNewIcon />
      </BackButton>
    </>
  );
};

export default GameRecommendPage;