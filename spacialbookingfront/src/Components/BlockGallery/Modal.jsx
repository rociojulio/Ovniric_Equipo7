import React from "react";
import styles from "./Modal.module.css";
import { useRef, useState } from "react";

import {Swiper, SwiperSlide} from "swiper/react";

import "swiper/css";
import "swiper/css/effect-flip";
import "swiper/css/pagination";
import "swiper/css/navigation";
import {EffectFlip, Pagination, Navigation} from "swiper";


function Modal(props){
    const {isModal,imgSlides, handleModal} = props
    console.log(imgSlides)
    return(
        <div className={isModal?styles.modal:styles.closeModal}>
            <button className={styles.buttonClosed} onClick={handleModal}>X</button>
            <div className={styles.gallery}>
                <Swiper effect={"flip"}
                grabCursor={true}
                pagination={true}
                navigation={true}
                modules={[EffectFlip, Pagination, Navigation]}
                className={`mySwiper ${styles.swiper}`}
                >
                    {imgSlides.map((image)=>(
                        <SwiperSlide key={image.idImage}><img src={image.imageUrl} alt={image.imageTitle}></img></SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </div>
        
    )

}


export default Modal;