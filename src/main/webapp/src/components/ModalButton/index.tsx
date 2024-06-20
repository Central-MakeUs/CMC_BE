import {CButton} from '@coreui/react';
import {CButtonProps} from '@coreui/react/dist/components/button/CButton';
import useModal from 'hooks/useModal';

import React from 'react';

export interface ModalButtonProps extends CButtonProps {
  title: string;
  description: string;
  onConfirm: (args?: any) => void;
}

const ModalButton = ({title, description, onConfirm, children, ...props}: ModalButtonProps) => {
  const [openModal, renderModal] = useModal();

  return (
    <>
      {typeof children === 'string' ? (
        <CButton onClick={openModal} {...props}>
          {children}
        </CButton>
      ) : (
        <div onClick={openModal}>{children}</div>
      )}
      {renderModal({
        title: title,
        description: description,
        onConfirm: onConfirm,
      })}
    </>
  );
};

export default ModalButton;
