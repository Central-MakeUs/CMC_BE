import {CButton, CFormInput} from '@coreui/react';
import {Column} from '@coreui/react/dist/components/table/types';
import {FlexBox} from 'components/FlexBox';
import Section from 'components/Section';
import useSelectSize from 'components/Select/useSelectSize';
import useQueryString from 'hooks/useQueryString';
import React, {Fragment, useState} from 'react';
import useQueryStringEffect from "../../hooks/useQueryStringEffect";
import {useInput} from "../../hooks/useInput";
import {useQuery} from "@tanstack/react-query";
import Table from "../../components/Table";
import {getTableResponseType} from "../../utils/getTableResponseType";
import Row from "../../components/Row";
import {generationWeekApi} from "../../apis/handlers/generationWeek";

const GENERATION_WEEK_COLUMNS: Column[] = [
  {label: 'id', key: 'id'},
  {label: '기수', key: 'generation'},
  {label: '주차', key: 'week'},
  {label: '날짜', key: 'date'},
  // {label: '오프라인 유무', key: 'isOffline'},
];

const GenerationWeekList = () => {

  const {get} = useQueryString();
  const [page, setPage] = useState(Number(get('page') || 0));
  const [size, SizeSelect] = useSelectSize(() => setPage(0));
  const [generation, onChangeGeneration, setGeneration] = useInput(get('searchValue') || '15');

  const {
    data,
    status: httpStatus,
    refetch,
  } = useQuery(
    [page, size],
    () =>
      generationWeekApi.getAllGenerationWeeksByGeneration(
        {
          generation: parseInt(generation),
          page: page,
          size: size
        }
      ),
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
            <Row.Custom label={'기수'}>
              <CFormInput
                placeholder='기수를 입력해주세요.'
                value={generation}
                type={'number'}
                onChange={onChangeGeneration}
              />
            </Row.Custom>
          </>
        }
        footer={
          <FlexBox gap={10}>
            <CButton onClick={() => refetch()}>검색</CButton>
          </FlexBox>
        }
      />
      <Section
        body={
          <Fragment>
            <Table
              column={GENERATION_WEEK_COLUMNS}
              paginationState={[page, setPage]}
              size={size}
              data={getTableResponseType({src: data?.contents, totalCnt: data?.totalCnt, page})}
            />
          </Fragment>
        }
      />
    </>
  );
};

export default GenerationWeekList;
