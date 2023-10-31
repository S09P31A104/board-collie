import { Box, Modal } from '@mui/material';
import styled from 'styled-components';
import { useState } from "react";

/* 스타일 */
const InfoContainer = styled.div`
    display: flex;
    align-items: center;
`;
const StyledBox = styled(Box)`
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 50%;
    height: 50%;
    background-color: #FFFFFF;
    padding-top: 3px;
    padding-right: 4px;
    padding-bottom: 3px;
    border-radius: 8px;
    font-family: 'Jua', sans-serif;
    overflow: auto;
    
    /* 스크롤바 스타일 설정 */
    scrollbar-width: thin;
    scrollbar-color: #CCF38C transparent; /* 스크롤바 썸 색상 / 스크롤바 트랙 색상 */

    /* Chrome, Safari */
    &::-webkit-scrollbar {
        width: 8px;
    }
    &::-webkit-scrollbar-track {
        background: transparent; /* 스크롤바 트랙 색상 */
    }
    &::-webkit-scrollbar-thumb {
        background-color: #CCF38C; /* 스크롤바 썸 색상 */
        border-radius: 4px;
    }
`;
const ModalTitle = styled.div`
    margin-top: 2%;
    margin-bottom: 2%;
    font-size: 3vw;
`;
const ModalContent = styled.div`
    width: 90%;
    display: flex;
    align-items: center;
    margin-top: 2%;
    margin-bottom: 2%;
`;
const InfoImage = styled.img`
    width: 50%;
`;
const InfoText = styled.div`
    width: 50%;
    margin-left: 10%;
    font-size: 2vw;
`;

function InfoComponent({info}) {

    const [modalOpen, setModalOpen] = useState(false);
    const handlekModalOpen = () => {
        setModalOpen(true);
    };
    const handleModalClose = () => {
        setModalOpen(false);
    };

    return (
        <InfoContainer>
            <div onClick={handlekModalOpen}>
                📦
            </div>
            <Modal
                open={modalOpen}
                onClose={handleModalClose}
            >
                <StyledBox  sx={{ '&:focus': { outline: 'none' }}}>
                    <ModalTitle>내용물</ModalTitle>
                    {info.map((item, index) => (
                        <ModalContent key={index}>
                            <InfoImage src={item[0]} />
                            <InfoText>{item[1]}</InfoText>
                        </ModalContent>
                    ))}
                </StyledBox >
            </Modal>
        </InfoContainer>
    )
}

export default InfoComponent;