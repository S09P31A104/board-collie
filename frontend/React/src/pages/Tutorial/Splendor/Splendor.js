import { useState } from "react";

function Splender() {

    const [page, setPage] = useState(0);
    const flow = [
        // [템플렛 이름, 사진, 타이틀, 내용, 버튼1 내용, 버튼1 화면 번호, 버튼2 내용, 버튼2 화면 번호, 버튼3 내용, 버튼3 화면 번호, 버튼4 내용, 버튼4 화면 번호,]
        [
            'TutorialStartComponent',
            '/Tutorial/Splendor/Splendor-title.png',
            '스플렌더',
            `인원수별 세팅 방법이 달라집니다.
            인원수를 다시 한 번 확인해주세요.`,
            null, null, null, null, null, null, null, null
        ],
    ]

    return (
            (flow[page] && flow[page][0] === 'TutorialStartComponent') ?
            <TutorialStartComponent/>
            :
            null
    )
}

export default Splender;