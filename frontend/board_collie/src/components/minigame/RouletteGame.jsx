/* eslint-disable */
import React, { useState, useEffect, useRef } from 'react';
import { Select, MenuItem, FormControl, InputLabel, Button } from '@mui/material';
import styles from './RouletteGame.module.css';

function Roulette() {
  const [players, setPlayers] = useState(2);
  const canvasRef = useRef(null);

  const products = ["토끼", '거북이', "고양이", "강아지", "앵무새", "돌고래", '백조', "코끼리", "너구리"];
  const colors = ["#dc0936", "#e6471d", "#f7a416", "#efe61f ", "#60b236", "#209b6c", "#169ed8", "#3f297e", "#87207b", "#be107f", "#e7167b"];

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext('2d');
    const [cw, ch] = [canvas.width / 2, canvas.height / 2];
    const arc = Math.PI / (players / 2);
  
    ctx.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 초기화

    for (let i = 0; i < players; i++) {
      ctx.beginPath();
      ctx.fillStyle = colors[i % colors.length];
      ctx.moveTo(cw, ch);
      ctx.arc(cw, ch, cw, arc * (i - 1), arc * i);
      ctx.fill();
      ctx.closePath();
    }

    ctx.fillStyle = "#fff";
    ctx.font = "18px Pretendard";
    ctx.textAlign = "center";

    for (let i = 0; i < players; i++) {
      const angle = (arc * i) + (arc / 2);
      ctx.save();
      ctx.translate(cw + Math.cos(angle) * (cw - 50), ch + Math.sin(angle) * (ch - 50));
      ctx.rotate(angle + Math.PI / 2);
      ctx.fillText(products[i], 0, 0);
      ctx.restore();
    }
  }, [players]);

  const rotate = () => {
    const canvas = canvasRef.current;
    canvas.style.transition = 'initial';
    canvas.style.transform = 'initial';
    setTimeout(() => {
      const ran = Math.floor(Math.random() * players);
      const arc = 360 / players;
      const extraDegrees = Math.random() * arc; // 0 ~ arc 사이의 랜덤한 각도 추가
      const rotate = (ran * arc) + 3600 + extraDegrees;
      canvas.style.transform = `rotate(-${rotate}deg)`;
      canvas.style.transition = `2s`;
    }, 1);
  };

  const handleChange = (event) => {
    setPlayers(event.target.value);
  };

  return (
    <div className={styles.container}>
      <FormControl className={styles.formControl}>
        <InputLabel id="players-select-label">인원수</InputLabel>
        <Select
          labelId="players-select-label"
          id="players-select"
          value={players}
          label="인원수"
          onChange={handleChange}
        >
          {[2, 3, 4, 5, 6, 7, 8].map((num) => (
            <MenuItem key={num} value={num}>
              {num}명
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <div className={styles.rouletteContainer}>
        <canvas ref={canvasRef} width="380" height="380" className={styles.canvas} />
        <div className={styles.triangle}></div>
      </div>
      
      <Button variant="contained" color="primary" onClick={rotate} className={styles.button}>룰렛 돌리기</Button>
    </div>
  );
}

export default Roulette;
