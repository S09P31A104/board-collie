import React, { useState, CSSProperties } from 'react';
import { TextField, Grid, Button } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/logo.png' 
import styled from 'styled-components';

interface Props {
  onSearch: (query: string) => void;
  style?: CSSProperties; 
}


const Logo = styled.div`
  background-image: url(${logo});
  background-size: contain;
  background-repeat: no-repeat;
  width: 12vw;
  height: 12vh;
  position: absolute;
  top: 50%;
  left: 0;
  transform: translateY(-50%);
  cursor: pointer;
  z-index: 5; 
`;

const SearchBar: React.FC<Props> = ({ onSearch, style }) => {
  const [inputValue, setInputValue] = useState("");
  const [isFocused, setIsFocused] = useState(false);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
  };

  const handleSearchClick = () => { 
    onSearch(inputValue);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearchClick();
    }
  };

  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate('/main');
  };

  return (
    <div style={{ position: 'relative' }}>
    <Logo onClick={handleLogoClick} />
    <Grid container justifyContent="center" alignItems="center" spacing={2} style={{ marginTop: '15px', ...style }}>
      
      <Grid item xs={4}>
      

        <TextField
          fullWidth
          variant="outlined"
          placeholder="제목이나 태그를 입력해주세요"
          InputProps={{
            endAdornment: <SearchIcon  style={{position: 'relative', zIndex: 1000, marginRight: '10px'   }} />,
            sx: {
              height: '6vh',
              borderRadius: '30px',
              backgroundColor: isFocused ? 'white' : '#e7e7e7',
              fontFamily: 'Jua, sans-serif',
            },
          }}
          style={{ position: 'relative', zIndex: 1000 }}
          sx={{
            "& .MuiOutlinedInput-notchedOutline": {
              border: isFocused ? "" : 'none',
              height: '6vh',
            },
          }}
          value={inputValue}
          onChange={handleInputChange}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          onKeyDown={handleKeyDown}
        />
      </Grid>
      <Grid item xs={2}>
      <Button 
          variant="contained" 
          onClick={handleSearchClick}
          sx={{
            borderRadius: '15px', 
            backgroundColor: '#FDCD51',
            fontFamily: 'Jua, sans-serif',
            paddingX: '30px',
            color: 'black',
          }}
          style={{ position: 'relative', zIndex: 1000 }}
        >
          검색
        </Button>
      </Grid>
    </Grid>
    </div>
  );
};

export default SearchBar;
