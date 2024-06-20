import * as XLSX from 'xlsx';

/**
 * 엑셀 업로드/다운로드를 위한 유틸함수입니다.
 * object[] 형태의 데이터를 사용합니다.
 */
const excel = {
  /**
   *
   * @param name 다운로드 엑셀 파일명
   * @param excelData 다운로드할 정보
   */
  export: (name: string, excelData: any[]) => {
    if (excelData.length !== 0) {
      excelDownLoadFunction(`${name}.xlsx`, name, excelData);
    } else {
      alert(`${name} 데이터가 없습니다.`);
    }
  },
  /**
   *
   * @param file 엑셀 업로드
   */
  read: (file: File) => {
    return new Promise((resolve, reject) => {
      const fileReader = new FileReader();

      fileReader.onload = e => {
        try {
          const arrayBuffer = e.target?.result as ArrayBuffer;
          const workbook = XLSX.read(arrayBuffer, {type: 'array'});

          // 첫 번째 시트 선택
          const worksheet = workbook.Sheets[workbook.SheetNames[0]];

          // 시트를 JSON 형식으로 변환
          const jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});

          resolve(jsonData);
        } catch (error) {
          reject(error);
        }
      };

      fileReader.onerror = error => {
        reject(error);
      };

      fileReader.readAsArrayBuffer(file);
    });
  },
};

const excelDownLoadFunction = (fileName: string, sheetName: string, excelData: any[]) => {
  const excelHandler = {
    getExcelFileName: () => {
      return fileName;
    },
    getSheetName: () => {
      return sheetName;
    },
    getExcelData: () => {
      return excelData;
    },
    getWorksheet: () => {
      return XLSX.utils.json_to_sheet(excelHandler.getExcelData());
    },
  };

  const datas = excelHandler.getWorksheet();
  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, datas, excelHandler.getSheetName());
  XLSX.writeFile(workbook, excelHandler.getExcelFileName());
};

export default excel;
