import React from "react";
import styles from "./Button.module.css";

const Button = (props) => {

    return <button type={props.type} className={styles.button}> Hello World!</button>
}

export default Button;