import React, { useState, useRef } from 'react';
import './Wheel.css';

const Wheel = () => {
  const [clicks, setClicks] = useState(0);
  const [winner, setWinner] = useState('');
  const innerWheelRef = useRef(null);

  const spinWheel = () => {
    // 클릭 수 증가
    const newClicks = clicks + 1;
    setClicks(newClicks);

    // 회전 계산
    const degree = 1800;
    const newDegree = degree * newClicks;
    const extraDegree = Math.floor(Math.random() * 360) + 1;
    const totalDegree = newDegree + extraDegree;


    // 룰렛 회전
    if (innerWheelRef.current) {
      innerWheelRef.current.style.transition = 'all 6s cubic-bezier(0,.99,.44,.99)';
      innerWheelRef.current.style.transform = `rotate(${totalDegree}deg)`;
    }

    // 승자 계산
    setTimeout(() => {
      const remainderDegree = totalDegree % 360;
      let challenge = '';
      if (remainderDegree > 337.5 || remainderDegree <= 22.5) challenge = 'plyo lunges';
      else if (remainderDegree > 22.5 && remainderDegree <= 67.5) challenge = 'reverse lunges';
      else if (remainderDegree > 67.5 && remainderDegree <= 112.5) challenge = 'mason twists';
      else if (remainderDegree > 112.5 && remainderDegree <= 157.5) challenge = 'leg raises';
      else if (remainderDegree > 157.5 && remainderDegree <= 202.5) challenge = 'mountain climbers';
      else if (remainderDegree > 202.5 && remainderDegree <= 247.5) challenge = 'push-ups';
      else if (remainderDegree > 247.5 && remainderDegree <= 292.5) challenge = 'squats';
      else challenge = 'burpees';
      
      setWinner(`your challenge: ${challenge}`);
    }, 6000); // 룰렛 회전시간에 맞춰 6초 후에 결과 표시
  };

  return (
    <div className="wrapper">
      <hgroup>
        <h1>Workout Wheel</h1>
        <h3>Instruction:</h3>
        <ol>
          <li>Spin the wheel.</li>
          <li>Perform the exercise for 1 minute.</li>
        </ol>
      </hgroup>
      <div className="wheel">
        <div className="inner-wheel">
          <div className="sec"><span className="name">burpees</span></div>
          <div className="sec"><span className="name">squats</span></div>
          <div className="sec"><span className="name">push-ups</span></div>
          <div className="sec"><span className="name">mountain climbers</span></div>
          <div className="sec"><span className="name">leg raises</span></div>
          <div className="sec"><span className="name">mason twists</span></div>
          <div className="sec"><span className="name">reverse lunges</span></div>
          <div className="sec"><span className="name">plyo lunges</span></div>
        </div>
        <div className="spin" onClick={spinWheel}>
          <div className="inner-spin"></div>
        </div>
        <div className="shine"></div>
      </div>
      <h2 className="winner">{winner}</h2>
    </div>
  );
};

export default Wheel;
