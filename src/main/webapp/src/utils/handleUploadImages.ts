import {storage} from 'config/firebase';
import {getDownloadURL, ref, uploadBytes} from 'firebase/storage';

/**
 * 이미지를 업로드하는 유틸함수입니다
 * @examplle 
 * 
 * const handleUploadImages = async (e: ChangeEvent<HTMLInputElement>) => {
  const uploadImgs = e.target.files;

  if (uploadImgs) {
    const uploadUrls = await uploadImages.multiple(uploadImgs);
    setInfoImages(uploadUrls);
  }
};
const handleUploadImage = async (e: ChangeEvent<HTMLInputElement>) => {
  const uploadImg = e.target.files?.[0];
  if (uploadImg) {
    const uploadUrl = await uploadImages.single(uploadImg);
    setThumbnail(uploadUrl);
  }
};
 */
const uploadImages = {
  /**
   * 여러 이미지 업로드
   * @param {FileList} uploadImgs
   */
  multiple: async (uploadImgs: FileList) => {
    const uploadPromises = Array.from(uploadImgs).map(async uploadImg => {
      const name = uploadImg.name;
      const date = new Date();
      const imageName = `${date.getTime()}_${name}`;
      const storageRef = ref(storage, imageName);

      await uploadBytes(storageRef, uploadImg);
      const uploadUrl = await getDownloadURL(storageRef);
      return uploadUrl;
    });

    const uploadUrls = await Promise.all(uploadPromises);
    return uploadUrls;
  },
  /**
   * 단일 이미지 업로드
   * @param {File} uploadImgs
   */
  single: async (uploadImg: File) => {
    const name = uploadImg.name;

    const date = new Date();
    const imageName = `${date.getTime()}_${name}`;
    const storageRef = ref(storage, imageName);

    await uploadBytes(storageRef, uploadImg);
    const uploadUrl = await getDownloadURL(storageRef);
    return uploadUrl;
  },
};

export default uploadImages;
