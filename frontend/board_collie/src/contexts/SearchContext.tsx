import React, { createContext, useState, useContext, ReactNode } from 'react';

const SearchContext = createContext<{
  searchTag: string;
  setSearchTag: (tag: string) => void;
}>({
  searchTag: '',
  setSearchTag: () => {}
});

export const useSearch = () => useContext(SearchContext);

export const SearchProvider: React.FC<{children: ReactNode}> = ({ children }) => {
  const [searchTag, setSearchTag] = useState('');

  return (
    <SearchContext.Provider value={{ searchTag, setSearchTag }}>
      {children}
    </SearchContext.Provider>
  );
};
