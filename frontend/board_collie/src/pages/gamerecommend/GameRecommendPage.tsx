/* eslint-disable */

import React, { useState, useEffect } from 'react';
import { TypeAnimation } from 'react-type-animation';
import 'animate.css';

// images
import chatIcon from '../../assets/chat_icon.png';

// styles
import styled from 'styled-components';

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
  top: 23vh;
  left: 28vw;
`;

const SelectButton = styled.div`

`;

const GameRecommendPage: React.FC = () => {
  const [zoomInDone, setZoomInDone] = useState(false);
  const [fadeInDone, setFadeInDone] = useState(false);
  const [startType, setStartType] = useState(false);

  useEffect(() => {
    // ZoomIn 애니메이션이 끝난 후
    const zoomInTimer = setTimeout(() => {
      setZoomInDone(true);
    }, 1000);  // ZoomIn 애니메이션의 길이

    // FadeIn 애니메이션이 끝난 후
    if (zoomInDone) {
      const fadeInTimer = setTimeout(() => {
        setFadeInDone(true);
      }, 1000);  // FadeIn 애니메이션의 길이

      return () => clearTimeout(fadeInTimer);
    }

    return () => clearTimeout(zoomInTimer);
  }, [zoomInDone]);

  useEffect(() => {
    if (fadeInDone) {
      setStartType(true);
    }
  }, [fadeInDone]);

  return (
    <>
      <Padding />
      <ChatIcon className={`animate__animated ${zoomInDone ? '' : 'animate__zoomIn'}`}/>
      <PositionedChatBubble className={`animate__animated ${zoomInDone ? 'animate__fadeIn' : ''}`}>
        {startType && (
          <ChatBubble position='left'>
            <TypeAnimation 
              sequence={[
                '안녕하세요! AI 집사, 보드콜리 집사입니다. 사용자님께 맞는 최적의 게임을 추천해드리기 위해 간단한 질문 몇 개만 하겠습니다.',
                1000,
              ]}
              speed={50}
            />
          </ChatBubble>
        )}
      </PositionedChatBubble>
    </>
  );
};

export default GameRecommendPage;