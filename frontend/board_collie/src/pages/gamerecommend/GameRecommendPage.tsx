/* eslint-disable */

import React from 'react';

// images
import chatIcon from '../../assets/chat_icon.png';

// styles
import styled from 'styled-components';

/**
 * Game Recommend Page
 *
 * @author 허주혁
 * @todo 
 */

const ChatIcon = styled.div`
    background-image: url(${chatIcon});
    background-size: contain;
    background-repeat: no-repeat;
    width: 10vw;
    height: 15vh;
    display: block;
    position: absolute;
    top: 20vh;
    left: 10vw;
`;

const ChatBubble = styled.div`
  padding: 20px;
  justify-self: center;
  align-self: center;
  text-align: left;
  display: flex;
  flex-direction: column;
  width: 450px;
`;

const TextBubble = styled.p<{ $toMe?: boolean }>`
  font-size: 16px;
  line-height: 1.4;
  margin: 1px 0;
  padding: 8px 17px 6px 13px;
  max-width: 380px;
  position: relative;
  border-radius: 18px;
  color: ${props => (props.$toMe ? 'black' : 'initial')};
  align-self: ${props => (props.$toMe ? 'flex-start' : 'initial')};
  background-color: ${props => (props.$toMe ? '#E5E5EA' : 'initial')};

  &:after {
    position: absolute;
    content: "";
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    z-index: -1;
    background: ${props => props.$toMe ? 'url("data:image/svg+xml;charset=utf-8,<svg xmlns=\'http://www.w3.org/2000/svg\' x=\'0px\' y=\'0px\' width=\'15.515px\' height=\'17.5px\' viewBox=\'32.484 17.5 15.515 17.5\' enable-background=\'new 32.484 17.5 15.515 17.5\'><path fill=\'#E5E5EA\' d=\'M38.484,17.5c0,8.75,1,13.5-6,17.5C51.484,35,52.484,17.5,38.484,17.5z\'/></svg>") left bottom no-repeat' : 'none'};
    left: -6px;
  }
`;


const GameRecommendPage: React.FC = () => {


  return (
    <>
      <ChatIcon />
      <ChatBubble>
        <TextBubble $toMe> Fuck </TextBubble>  
      </ChatBubble>    
    </>
  );
};

export default GameRecommendPage;