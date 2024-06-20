import React, {useState} from 'react';

export const useInput = (initialValue: string, processingFn?: (arg?: string) => any) => {
  const [value, setValue] = useState(initialValue);

  const reset = () => {
    setValue(initialValue);
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    processingFn ? setValue(processingFn(e.target.value)) : setValue(e.target.value);
  };

  return [value, onChange, setValue, reset] as const;
};
