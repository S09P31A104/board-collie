import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Box, Typography, Button, IconButton, Card, CardMedia } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import gameimg from '../../assets/splendor.png';

const SelectPage: React.FC = () => {
  const { name } = useParams<{ name: string }>();
  const navigate = useNavigate();

  const goBack = () => {
    navigate(-1);
  };

  const handleYoutubeSearch = () => {
    const gameName = encodeURIComponent(name || "게임 이름 없음"); // 게임 이름을 URL 인코딩합니다.
    window.open(`https://www.youtube.com/results?search_query=${gameName} 튜토리얼`, '_blank');
  };
  

  return (
    <Box sx={{ padding: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', mt: '90px' }}>
      <IconButton onClick={goBack} aria-label="뒤로 가기" sx={{ alignSelf: 'flex-start', mb: 2 }}>
        <ArrowBackIcon />
      </IconButton>
      <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 2, mb: 2 }}>
        <Card sx={{ width: 300 }}>
          <CardMedia component="img" height="140" image={gameimg} alt={name} />
        </Card>
        <Button variant="contained" color="primary" onClick={handleYoutubeSearch}>
          유튜브 검색
        </Button>
      </Box>
      <Typography variant="body1" textAlign="center">
        게임의 튜토리얼을 체험해보거나 유튜브 영상을 감상하실 수 있습니다.
      </Typography>
    </Box>
  );
};

export default SelectPage;
