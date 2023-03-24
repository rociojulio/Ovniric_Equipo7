
import React from "react";
import styles from "./BlockGallery.module.css"
import GalleryTabletMobile from "./GalleryTabletMobile";
import Modal from "./Modal";
import { GlobalContext } from "../globalState/GlobalState";
import  {useState, useEffect} from 'react';

function BlockGallery({images}){

    const [isModal, setIsModal]= useState(false);

    
  
    let imgList = images;
    console.log(imgList)


    function handleModal(){
        setIsModal(!isModal)
    }


    console.log(imgList)





    return(
        <>
        <div className={styles.desktop}>
        <div className={styles.containerImg}>
            <div className={styles.mainImg}><img src={`${imgList[0].imageUrl}`} alt="" /></div>
            <div className={styles.containerSecondImg}>
                {imgList.slice(1,5).map((image) => (
                    <img key={image.idImage} src={image.imageUrl} alt={image.imageTitle}></img>
                    
                ))}
            </div>
        </div>
        <div className={styles.viewMore}><button onClick={handleModal}>Ver más...</button></div>
        <Modal imgSlides={imgList} isModal={isModal} handleModal={handleModal}></Modal>
        </div>
        <div className={styles.tabletMobile}>
            <GalleryTabletMobile imgSlides={imgList}></GalleryTabletMobile>
        </div>
        </>
    )
}

export default BlockGallery