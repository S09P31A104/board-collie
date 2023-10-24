import styled from 'styled-components';
import { useState, useEffect } from "react";
import TutorialStartComponent from "../../components/Tutorial/TutorialStartComponent";

/* 스타일 */
const SplenderContainer = styled.div`

`;

function Splender() {

    const [page, setPage] = useState(0);
    const flow = [
        // [템플렛 이름, 사진, 타이틀, 내용, 버튼1 내용, 버튼1 화면 번호, 버튼2 내용, 버튼2 화면 번호, 버튼3 내용, 버튼3 화면 번호, 버튼4 내용, 버튼4 화면 번호,]
        [
            'TutorialStartComponent',
            '/Tutorial/Splendor/title.png',
            '스플렌더',
            '인원 수별 세팅 방법이 달라집니다.\n인원수를 다시 한번 확인해 주세요.',
            'START', 1,
            null, null, null, null, null, null
        ],
    ]

    useEffect(() => {
        console.log(page);
    }, [page])

    return (
        <SplenderContainer>
            {
                (flow[page] && flow[page][0] === 'TutorialStartComponent') ?
                <TutorialStartComponent
                    title_image={flow[page][1]}
                    title={flow[page][2]}
                    message={flow[page][3]}
                    button1={flow[page][4]}
                    movePage1={flow[page][5]}
                    setPage={setPage}
                />
                :
                null
            }
        </SplenderContainer>
    )
}

export default Splender;