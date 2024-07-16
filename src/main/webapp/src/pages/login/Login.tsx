import React, {useState} from 'react';
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react';
import CIcon from '@coreui/icons-react';
import {cilLockLocked, cilUser} from '@coreui/icons';
import {useNavigate} from 'react-router-dom';
import {saveJwt} from '../../utils/utility';
import {openToast} from '../../components/Toast';
import {loginApi} from '../../apis/handlers/login';

const Login = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const onKeyPress = (event: { key: string }) => {
    if (event.key === 'Enter') {
      handleSubmit().then();
    }
  };

  const handleSubmit = async () => {
    try {
      if (email.length === 0) {
        openToast('이메일을 올바르게 입력해주세요.');
        return;
      }

      if (password.length === 0) {
        openToast('비밀번호를 영문, 숫자, 특수기호 조합으로 8자리 이상 입력해주세요.');
        return;
      }

      loginApi.postUsersLogin({email: email, password: password, id: email})
        .then(response => {
          console.log(response)
          saveJwt(response.accessToken);
          openToast(`로그인에 성공했습니다.`)
          navigate(`/admin-page/dashboard`);
        }).catch(error => {
        openToast(`로그인에 실패했습니다.${error}`)
      })
    } catch (error) {
      openToast(error);
    }
  };

  return (
    <div className='bg-light min-vh-100 d-flex flex-row align-items-center'>
      <CContainer>
        <CRow className='justify-content-center'>
          <CCol md={5}>
            <CCard className='p-4'>
              <CCardBody>
                <CForm>
                  <h1>관리자 로그인</h1>
                  <p className='text-medium-emphasis'>테스트 계정은 문의주시길 바랍니다.</p>
                  <CInputGroup className='mb-3'>
                    <CInputGroupText>
                      <CIcon icon={cilUser}/>
                    </CInputGroupText>
                    <CFormInput
                      type='email'
                      id='email'
                      placeholder='이메일주소를 입력해주세요'
                      autoComplete='on'
                      maxLength={50}
                      onChange={e => setEmail(e.target.value)}
                    />
                  </CInputGroup>
                  <CInputGroup className='mb-4'>
                    <CInputGroupText>
                      <CIcon icon={cilLockLocked}/>
                    </CInputGroupText>
                    <CFormInput
                      type='password'
                      id='password'
                      placeholder='영문, 숫자, 특수기호 조합으로 8자리 이상 입력해주세요'
                      autoComplete='off'
                      onKeyPress={onKeyPress}
                      maxLength={20}
                      onChange={e => setPassword(e.target.value)}
                    />
                  </CInputGroup>
                  <div className='d-grid'>
                    <CButton color='primary' onClick={handleSubmit}>
                      로그인
                    </CButton>
                  </div>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  );
};

export default Login;
