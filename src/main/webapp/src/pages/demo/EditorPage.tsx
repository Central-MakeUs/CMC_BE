import ModalButton from 'components/ModalButton';
import Section from 'components/Section';
import TextEditor from 'components/TextEditor';
import React from 'react';
import {useState} from 'react';

const EditorPage = () => {
  const [content, setContent] = useState('');

  return (
    <Section
      header={'텍스트 에디터 데모'}
      body={<TextEditor setContent={setContent} content={content} />}
      footer={
        <ModalButton
          title='입력내용'
          description={content}
          onConfirm={() => {
            console.log('//제출버튼');
          }}
        >
          입력 내용 확인
        </ModalButton>
      }
    />
  );
};

export default EditorPage;
