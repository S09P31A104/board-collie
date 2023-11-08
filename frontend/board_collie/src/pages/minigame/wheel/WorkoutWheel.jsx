import React, { useState } from 'react';
import './Wheel.css'; 

const WorkoutWheel = () => {
  const [clicks, setClicks] = useState(0);
  const [winner, setWinner] = useState('');
  const [rotation, setRotation] = useState(0);
  const degree = 1800;

  const spinWheel = () => {
    const newClicks = clicks + 1;
    setClicks(newClicks);
    const newDegree = degree * newClicks;
    const extraDegree = Math.floor(Math.random() * 360) + 1;
    const totalDegree = newDegree + extraDegree;
    const remainderDegree = totalDegree % 360;

    setRotation(totalDegree);

    // 각도에 따른 운동 이름 설정
    setTimeout(() => {
      if (remainderDegree > 337.5 || remainderDegree <= 22.5) {
        setWinner('plyo lunges');
      } else if (remainderDegree > 22.5 && remainderDegree <= 67.5) {
        setWinner('reverse lunges');
      } else if (remainderDegree > 67.5 && remainderDegree <= 112.5) {
        setWinner('mason twists');
      } else if (remainderDegree > 112.5 && remainderDegree <= 157.5) {
        setWinner('leg raises');
      } else if (remainderDegree > 157.5 && remainderDegree <= 202.5) {
        setWinner('mountain climbers');
      } else if (remainderDegree > 202.5 && remainderDegree <= 247.5) {
        setWinner('push-ups');
      } else if (remainderDegree > 247.5 && remainderDegree <= 292.5) {
        setWinner('squats');
      } else {
        setWinner('burpees');
      }
    }, 6000);
  };

  return (
    <div id="wrapper">
      <div style={{marginTop: '100px' }}>
      <hgroup>
        <h1>Workout Wheel</h1>
        <h3>Instruction:</h3>
        <ol>
          <li>Spin the wheel.</li>
          <li>Perform the exercise for 1 minute.</li>
        </ol>
      </hgroup>
      <div id="wheel">
        <div id="inner-wheel" style={{ transform: `rotate(${rotation}deg)` }}>
          <div className="sec"><span className="name">burpees</span></div>
          <div className="sec"><span className="name">squats</span></div>
        <div className="sec"><span className="name">push-ups</span></div>
        <div className="sec"><span className="name">mountain climbers</span></div>
        <div className="sec"><span className="name">leg raises</span></div>
        <div className="sec"><span className="name">mason twists</span></div>
        <div className="sec"><span className="name">reverse lunges</span></div>
        <div className="sec"><span className="name">plyo lunges</span></div>
        </div>
        <div id="spin" onClick={spinWheel}>
          <div id="inner-spin"></div>
        </div>
        <div id="shine"></div>
      </div>
      <h2 id="winner">선공 플레이어: {winner}</h2>
      </div>
    </div>
  );
};

export default WorkoutWheel;
