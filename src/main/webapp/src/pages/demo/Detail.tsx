import {CButton, CFormInput, CFormSwitch} from '@coreui/react';
import ModalButton from 'components/ModalButton';
import Row from 'components/Row';
import Section from 'components/Section';
import {openToast} from 'components/Toast';
import {useInput} from 'hooks/useInput';
import React, {ChangeEvent, useState} from 'react';
import {date} from 'utils/date';
import uploadImages from 'utils/handleUploadImages';
import {StringNumberToLocaleString, localeStringToNumber} from 'utils/utility';

const Detail = () => {
  const [inputValue, onChangeInputValue] = useInput('');
  const [textAreaValue, onChangeTextAreaValue] = useInput('');
  // 두번째 인자로 e.target.value를 가공하는 함수를 넘겨줄 수 있습니다.
  // 숫자 입력을 '세자리 콤마'표기로 변환하는 함수를 넘겨주었습니다.
  const [localStringValue, onChangeLocaleStringValue] = useInput('', localeStringToNumber);
  const [switchValue, setSwitchValue] = useState(false);
  const [singleImage, setSingleImage] = useState('');

  const handleUploadImage = async (e: ChangeEvent<HTMLInputElement>) => {
    openToast(
      '이미지 업로드를 위해 파이어베이스 세팅이 필요합니다.\n 이미지 저장소에 업로드한 Callback URL 을 불러와야합니다.',
    );
    const uploadImg = e.target.files?.[0];
    if (uploadImg) {
      const uploadUrl = await uploadImages.single(uploadImg);
      setSingleImage(uploadUrl);
    }
  };

  return (
    <Section
      header={'상세 데모페이지'}
      body={
        <>
          <Section
            header={'기본 컴포넌트'}
            body={
              <>
                <Row.Input
                  label='인덱스'
                  value={inputValue}
                  onChange={onChangeInputValue}
                  placeholder='내용을 입력해주세요'
                />
                <Row.Input
                  label='plainText'
                  value={'변경되지 않는 값은 plainText 속성을 이용하세요'}
                  plainText
                  readOnly
                />
                <Row.Input
                  label='숫자(세자리마다 콤마 입력)'
                  onChange={onChangeLocaleStringValue}
                  value={StringNumberToLocaleString(localStringValue)}
                  placeholder='숫자를 입력해주세요'
                />
                <Row.Input
                  label='날짜 포맷'
                  value={`${date.formatDate('2023-07-17 08:59:37')} ${date.formatTime('2023-07-17 08:59:37')}`}
                  plainText
                  readOnly
                />
                <Row.Textarea
                  label='textarea 컴포넌트'
                  onChange={onChangeTextAreaValue}
                  value={textAreaValue}
                  placeholder='rows 속성을 통해 높이를 변경할 수 있어요'
                  rows={12}
                />
              </>
            }
          />
          <Section
            header={'커스텀 컴포넌트'}
            body={
              <>
                <Row.Custom label='스위치'>
                  <CFormSwitch size='lg' />
                </Row.Custom>
                <Row.Custom label='스위치 (변경확인 모달창)'>
                  <ModalButton
                    title='변경 확인'
                    description='모달창 확인 후에 스위치를 토글할 수 있어요'
                    onConfirm={() => setSwitchValue(!switchValue)}
                  >
                    <CFormSwitch size='lg' checked={switchValue} readOnly />
                  </ModalButton>
                </Row.Custom>
                <Row.Custom label='토스트 알림'>
                  <CButton
                    color='secondary'
                    onClick={() => {
                      openToast('토스트 알림을 띄울 수 있어요');
                    }}
                  >
                    눌러서 알림
                  </CButton>
                </Row.Custom>
                <Row.Custom label='단일 이미지 업로드'>
                  <img src={singleImage} alt='동작을 위해 파이어베이스 설정이 필요합니다' style={{width: '300px'}} />
                  <CFormInput type='file' onChange={handleUploadImage} style={{width: '300px'}} />
                </Row.Custom>
              </>
            }
          />
        </>
      }
      footer={
        <ModalButton
          title='수정하기'
          description='ModalButton 컴포넌트를 통해 확인 모달을 쉽게 띄울수있어요'
          onConfirm={() => console.log('//POST request api handler')}
        >
          수정하기
        </ModalButton>
      }
    />
  );
};

export default Detail;
