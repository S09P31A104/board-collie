import React, { useState, CSSProperties } from 'react';
import { TextField, Grid, Button } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { useNavigate } from 'react-router-dom';

interface Props {
  onSearch: (query: string) => void;
  style?: CSSProperties; 
}

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
    
    <Grid container justifyContent="center" alignItems="center" spacing={2} style={{ marginTop: '15px', ...style }}>
      <div
        onClick={handleLogoClick}
        style={{
          position: 'absolute', 
          top: 0, 
          left: 0, 
          width: '110px', 
          height: '100px', 
          cursor: 'pointer',
          opacity: 0,
        }}
      />
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
  );
};

export default SearchBar;
