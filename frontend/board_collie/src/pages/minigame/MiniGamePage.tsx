import React from 'react';
import { Button, Paper, Grid, Typography } from '@mui/material';
import CasinoIcon from '@mui/icons-material/Casino';
import { useNavigate } from 'react-router-dom';

const MiniGamesPage: React.FC = () => {
  const navigate = useNavigate();

  // 룰렛 게임 페이지로 이동
  const handleRouletteGameClick = () => {
    navigate('/wheel'); 
  };

  return (
    <div style={{ padding: 20 }}>
      <Typography variant="h4" style={{ marginBottom: 20 }}>
        미니게임 선택
      </Typography>
      <Grid container spacing={2} justifyContent="center">
        <Grid item>
          <Paper elevation={3} style={{ padding: 20, textAlign: 'center' }}>
            <CasinoIcon style={{ fontSize: 60, marginBottom: 10 }} />
            <Typography variant="h6">룰렛 게임</Typography>
            <Button variant="contained" color="primary" onClick={handleRouletteGameClick} style={{ marginTop: 10 }}>
              플레이
            </Button>
          </Paper>
        </Grid>
        {/* 향후 추가될 다른 미니게임들의 컴포넌트 위치 */}
      </Grid>
    </div>
  );
};

export default MiniGamesPage;
