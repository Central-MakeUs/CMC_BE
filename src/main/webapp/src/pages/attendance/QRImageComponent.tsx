import React, {useEffect, useState} from "react";
import {attendanceApi} from "../../apis/handlers/attendance";

export function QRImageComponent({code, type}: { code: string, type: string }) {
  const [imageSrc, setImageSrc] = useState('');

  useEffect(() => {
    console.log("-------------")
    attendanceApi.getQRImageByCode({
      code: code,
      type: type
    }).then((r) => {
      console.log(r)
      setImageSrc(`data:image/jpeg;base64,${r.body}`);
    }).catch((e) => {
      console.log(e)
    })
  }, []);

  return (
    <div>
      {imageSrc ? (
        <img src={imageSrc} alt="Fetched from server"/>
      ) : (
        <p>Loading image...</p>
      )}
    </div>
  );
};
