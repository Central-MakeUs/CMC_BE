import {CModal, CModalHeader, CModalTitle, CModalBody, CModalFooter, CButton} from '@coreui/react';
import React, {useState} from 'react';

export interface ModalProps {
  title: string;
  description: string;
  onConfirm: (args?: any) => void;
  data?: any;
}

/**
 * 제목, 설명, [확인, 취소] 형식의 모달을 띄울때 사용하는 훅입니다.
 * <ModalButton/> 컴포넌트를 통해 더욱 간편하게 사용할 수 있습니다.
 *
 * @returns [openModal, renderModal, closeModal]
 * @example
 * <>
 * {renderModal({
 *   title : '모달 제목',
 *   description : '모달 디스크립션',
 *   onConfirm : '확인 버튼 핸들러'
 * })}
 * </>
 */
const useModal = () => {
  const [state, setState] = useState(false);
  const [data, setData] = useState();

  const openModal = (payload?: any) => {
    setState(true);
    setData(payload);
  };

  const closeModal = () => {
    setState(false);
  };

  const renderModal = ({title, description, onConfirm}: ModalProps) => {
    return (
      <CModal visible={state} onClose={closeModal}>
        <CModalHeader>
          <CModalTitle>{title}</CModalTitle>
        </CModalHeader>
        <CModalBody>{description}</CModalBody>
        <CModalFooter>
          <CButton color='secondary' onClick={closeModal}>
            취소
          </CButton>
          <CButton
            color='primary'
            onClick={() => {
              data ? onConfirm(data) : onConfirm();
              closeModal();
            }}
          >
            확인
          </CButton>
        </CModalFooter>
      </CModal>
    );
  };

  return [openModal, renderModal, closeModal] as const;
};

export default useModal;
