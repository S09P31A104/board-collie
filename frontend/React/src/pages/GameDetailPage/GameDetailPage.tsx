import React from 'react';
import { useParams } from 'react-router-dom';

const GameDetailPage: React.FC = () => {
  const { name } = useParams<{ name: string }>();

  // 여기에 게임 데이터를 검색하는 로직을 추가하세요
  // 예: const game = dummyData.find(game => game.name === name);

  return (
    <div>
      <h1>{name}</h1>
      {/* 여기에 게임의 상세 정보를 표시하는 코드를 추가하세요 */}
      {/* 예:
      <ul>
        {game?.tags.map(tag => (
          <li key={tag}>{tag}</li>
        ))}
      </ul>
      */}
    </div>
  );
}

export default GameDetailPage;
