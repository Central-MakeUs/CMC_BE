import {Column} from '@coreui/react/dist/components/table/types';
import Section from 'components/Section';
import useSelectSize from 'components/Select/useSelectSize';
import useQueryString from 'hooks/useQueryString';
import React, {Fragment, useState} from 'react';
import useQueryStringEffect from "../../hooks/useQueryStringEffect";
import {useInput} from "../../hooks/useInput";
import {useQuery} from "@tanstack/react-query";
import {userApi} from "../../apis/handlers/users";

const USER_COLUMNS: Column[] = [
    {label: 'id', key: 'id'},
    {label: '기수', key: 'generation'},
    {label: '이름', key: 'name'},
    {label: '이메일', key: 'email'},
    {label: '닉네임', key: 'nickname'},
    {label: '파트', key: 'part'},
    {label: '회원가입 승인', key: 'signUpApprove'},
    {label: '회원가입 승인', key: 'modal'},
];

const QRCodeList = () => {

    const {get} = useQueryString();
    const [page, setPage] = useState(Number(get('page') || 0));
    const [size, SizeSelect] = useSelectSize(() => setPage(0));
    const [generation, onChangeGeneration, setGeneration] = useInput(get('searchValue') || '15');

    /**
     *
     * useQuery의 querykey에 다양한 변수들을 넣어줄 수 있습니다.
     * const {data, status: httpStatus} = useQuery(['mock',page,size, status //..외 기타 키들], () =>
     */
    const {
        data,
        status: httpStatus,
        refetch,
    } = useQuery(
        [page, size],
        () =>
            userApi.getAllUsersByGeneration(
                {
                    generation: parseInt(generation),
                    page: page,
                    size: size
                }
            ),
        {
            onSuccess: data => {
                console.log(data)
            },
        },
    );

    useQueryStringEffect(
        {
            page,
            size,
        },
        [page, size],
    );

    return (
        <>
            <Section
                body={
                    <>
                        QR 코드 준비 중...
                    </>
                }
                footer={
                    <>
                    </>
                    // <FlexBox gap={10}>
                    //     <CButton onClick={() => refetch()}>검색</CButton>
                    // </FlexBox>
                }
            />
            {/*<Section*/}
            {/*    body={*/}
            {/*        <Fragment>*/}
            {/*            <Table*/}
            {/*                column={USER_COLUMNS}*/}
            {/*                paginationState={[page, setPage]}*/}
            {/*                size={size}*/}
            {/*                data={getTableResponseType({src: data?.contents, totalCnt: data?.totalCnt, page})}*/}
            {/*                renderColumnData={{*/}
            {/*                    modal: data => (*/}
            {/*                        <>*/}
            {/*                            <ModalButton*/}
            {/*                                title='회원가입 승인'*/}
            {/*                                description={`이름(${data.name})의 회원가입을 승인할까요?`}*/}
            {/*                                onConfirm={() => {*/}
            {/*                                    userApi.handleSignUpAprrove({*/}
            {/*                                        userId: data.id,*/}
            {/*                                        approve: true*/}
            {/*                                    }).then(() => {*/}
            {/*                                        refetch();*/}
            {/*                                    });*/}
            {/*                                }}*/}
            {/*                            >*/}
            {/*                                승인*/}
            {/*                            </ModalButton>*/}
            {/*                            <ModalButton*/}
            {/*                                title='회원가입 거부'*/}
            {/*                                description={`이름(${data.name})의 회원가입 승인을 거부할까요?`}*/}
            {/*                                onConfirm={() => {*/}
            {/*                                    userApi.handleSignUpAprrove({*/}
            {/*                                        userId: data.id,*/}
            {/*                                        approve: false*/}
            {/*                                    }).then(() => {*/}
            {/*                                        refetch().then();*/}
            {/*                                    });*/}
            {/*                                }}>*/}
            {/*                                거부*/}
            {/*                            </ModalButton>*/}
            {/*                        </>*/}
            {/*                    ),*/}
            {/*                }}*/}
            {/*            />*/}
            {/*        </Fragment>*/}
            {/*    }*/}
            {/*/>*/}
        </>
    );
};

export default QRCodeList;
