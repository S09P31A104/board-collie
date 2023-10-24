import styled from 'styled-components';
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Splender from "./Splendor";


/* 스타일 */
const TutorialContainer = styled.div`
    position: relative;

    /* 컨테이너 크기 조정 */
    width: 100vw;
    height: 100vh;

    /* 문자열 내 줄바꿈(\n) 적용 */
    white-space: pre-wrap;

    /* 배경 사진 */
    background-size: cover;
    background-position: center;

    display: flex;
    justify-content: center;
    align-items: center;
`;
const BackgroundLayer = styled.div`
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(247, 238, 246, 0.2);
`;
const MainContent = styled.div`
    position: absolute;
    z-index: 1;
    width: 80%;
    height: 90%;
`;

function TutorialPage() {

    const params = useParams();
    const [backgroundImage, setBackgroundImage] = useState('');

    useEffect(() => {
        if(params.title === 'splender') {
            setBackgroundImage('/Tutorial/Splendor/background.jpg');
        }
    }, [])

    return (
        <TutorialContainer
            style={{backgroundImage: `url(${backgroundImage})`}}
        >
            <BackgroundLayer/>
            <MainContent>
            {
                (params.title === 'splender') ?
                    <Splender/>
                    :
                    null
            }
            </MainContent>
        </TutorialContainer>
    )
}

export default TutorialPage;