import {Editor} from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import chart from '@toast-ui/editor-plugin-chart';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import 'tui-color-picker/dist/tui-color-picker.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import tableMergedCell from '@toast-ui/editor-plugin-table-merged-cell';
import uml from '@toast-ui/editor-plugin-uml';
import {HookCallback} from '@toast-ui/editor/types/editor';
import '@toast-ui/editor/dist/i18n/ko-kr';
import React, {useEffect, useRef, useState} from 'react';

export interface TextEditorProps {
  content: string;
  setContent: (value: string) => void;
}
/**
 * 텍스트 에디터 컴포넌트입니다.
 * 이미지 업로드 로직은 추가 구현이 필요합니다.
 */
const TextEditor = ({content, setContent}: TextEditorProps) => {
  const toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr', 'quote'],
    ['ul', 'ol', 'task', 'indent', 'outdent'],
    ['table', 'image', 'link'],
    ['code', 'codeblock'],
    ['scrollSync'],
  ];
  const editorRef = useRef<Editor>(null);
  const [isInitialized, setIsInitialized] = useState<boolean>(false);

  useEffect(() => {
    if (editorRef.current && !isInitialized && content !== '') {
      setIsInitialized(true);
      if (content) {
        editorRef.current.getInstance().setHTML(content);
      }
    }
  }, [content, isInitialized]);

  // editor image upload handler
  const uploadImageHandler = async (_image: Blob | File, _callback: HookCallback) => {
    // 이미지 업로드 로직 이후에 응답받은 이미지 url과 imageName을 콜백으로 넘겨줍니다.
    // callback(url, imageName)
  };

  // 입력값 onChangeHandler
  const onChange = () => {
    if (editorRef.current) setContent(editorRef.current!.getInstance().getHTML());
  };

  return (
    <Editor
      ref={editorRef}
      onChange={onChange}
      previewStyle='vertical' // 미리보기 스타일 지정
      hideModeSwitch={true} // 모드 바꾸기 스위치 숨기기 여부
      previewHighlight={true}
      height='500px' // 에디터 창 높이
      initialEditType='wysiwyg' // 초기 입력모드 설정
      toolbarItems={toolbarItems}
      useCommandShortcut={false}
      language='ko-KR'
      autofocus
      hooks={{
        addImageBlobHook: uploadImageHandler,
      }}
      plugins={[chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml]}
    />
  );
};
export default TextEditor;
