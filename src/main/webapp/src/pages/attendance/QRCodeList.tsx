import {Column} from '@coreui/react/dist/components/table/types';
import Section from 'components/Section';
import useSelectSize from 'components/Select/useSelectSize';
import useQueryString from 'hooks/useQueryString';
import React, {Fragment, useState} from 'react';
import useQueryStringEffect from "../../hooks/useQueryStringEffect";
import {useQuery} from "@tanstack/react-query";
import Table from "../../components/Table";
import {getTableResponseType} from "../../utils/getTableResponseType";
import {attendanceApi} from "../../apis/handlers/attendance";
import Row from "../../components/Row";
import {CButton, CFormInput} from "@coreui/react";
import useSelect, {Option} from "../../components/Select/useSelect";
import {FlexBox} from "../../components/FlexBox";

const ATTENDANCE_CODE_COLUMN: Column[] = [
  {label: 'id', key: 'id'},
  {label: '기수', key: 'generation'},
  {label: '주차', key: 'week'},
  {label: '1차 출석(FIRST), 2차 출석(SECOND)', key: 'hour'},
  {label: '출석 시작 시간', key: 'startTime'},
  {label: '출석 종료 시간', key: 'endTime'},
  {label: '지각 허용 시간(분)', key: 'lateMinute'},
];

const ATTEMDAMCE_HOUR_OPTIONS: Option[] = [
  {label: '1차', value: '1'},
  {label: '2차', value: '2'},
];

const QRCodeList = () => {

  const {get, initDate, initSelect} = useQueryString();
  const [page, setPage] = useState(Number(get('page') || 0));
  const [size, SizeSelect] = useSelectSize(() => setPage(0));
  const [generation, onChangeGenerationValue] = useState<string>('');
  const [attendanceHour, AttendanceHourStatusSelect, setAttendanceHourStatusSelect] = useSelect(ATTEMDAMCE_HOUR_OPTIONS, initSelect('1', ATTEMDAMCE_HOUR_OPTIONS));
  const [week, onChangeWeekValue] = useState<string>('');
  const [startHour, onChangeStartHourValue] = useState<string>('');
  const [startMinute, onChangeStartMinuteValue] = useState<string>('');
  const [endHour, onChangeEndHourValue] = useState<string>('');
  const [endMinute, onChangeEndMinuteValue] = useState<string>('');
  const [lateMinute, onChangeLateMinuteValue] = useState<string>('');

  const {
    data,
    status: httpStatus,
    refetch,
  } = useQuery(
    [page, size],
    () =>
      attendanceApi.getAllAttendanceCode(
        {
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

  const generateAttendanceCode = () => {
    attendanceApi.postAttendanceCode({
      generation: generation,
      week: week,
      hour: attendanceHour!!.toString(),
      startTime: {
        hour: startHour,
        minute: endMinute
      },
      endTime: {
        hour: endHour,
        minute: endMinute
      },
      lateMinute: lateMinute
    }).then((r) => {
      console.log(r)
      refetch()
    })
  }

  return (
    <>
      <Section
        body={
          <>
            <Row.Custom label={'QR 코드 조회 및 생성'}/>
            <Row.Custom label={'생성'}>
              <Row.Custom label={'기수'}>
                <CFormInput
                  placeholder='(14, 15)'
                  value={generation}
                  onChange={(event) => {
                    onChangeGenerationValue(event.target.value)
                  }}
                />
              </Row.Custom>
              <Row.Custom label={'주차'}>
                <CFormInput
                  placeholder='(1, 2)'
                  value={week}
                  onChange={(event) => {
                    onChangeWeekValue(event.target.value)
                  }}
                />
              </Row.Custom>

              <Row.Custom label={'출석 시작 시(24시간제)'}>
                <CFormInput
                  placeholder='(13, 14)'
                  value={startHour}
                  onChange={(event) => {
                    onChangeStartHourValue(event.target.value)
                  }}
                />
              </Row.Custom>

              <Row.Custom label={'출석 시작 분'}>
                <CFormInput
                  placeholder='(10, 30)'
                  value={startMinute}
                  onChange={(event) => {
                    onChangeStartMinuteValue(event.target.value)
                  }}
                />
              </Row.Custom>

              <Row.Custom label={'출석 종료 시(24시간제)'}>
                <CFormInput
                  placeholder='(13, 14)'
                  value={endHour}
                  onChange={(event) => {
                    onChangeEndHourValue(event.target.value)
                  }}
                />
              </Row.Custom>

              <Row.Custom label={'출석 종료 분'}>
                <CFormInput
                  placeholder='(10, 30)'
                  value={endMinute}
                  onChange={(event) => {
                    onChangeEndMinuteValue(event.target.value)
                  }}
                />
              </Row.Custom>

              <Row.Custom label={'지각 허용 시간(분)'}>
                <CFormInput
                  placeholder='(15)'
                  value={lateMinute}
                  onChange={(event) => {
                    onChangeLateMinuteValue(event.target.value)
                  }}
                />
              </Row.Custom>
            </Row.Custom>

            <Row.Custom label={'1차, 2차 출석'}>
              <AttendanceHourStatusSelect width={130}/>
            </Row.Custom>
          </>
        }
        footer={
          <>
            <FlexBox gap={15}>
              <CButton onClick={() => {
                generateAttendanceCode()
              }}>QR 생성</CButton>
            </FlexBox>
          </>
        }
      />
      <Section
        body={
          <Fragment>
            <Table
              column={ATTENDANCE_CODE_COLUMN}
              paginationState={[page, setPage]}
              size={size}
              data={getTableResponseType({src: data?.contents, totalCnt: data?.totalCnt, page})}
              renderColumnData={{
                modal: data => (
                  <>
                  </>
                ),
              }}
            />
          </Fragment>
        }
      />
    </>
  );
};

export default QRCodeList;
