import React, { useEffect } from "react";
import styles from './AddProducts.module.css';
// import { icons } from "react-icons/lib";
import { MinusCircleOutlined, PlusOutlined  } from '@ant-design/icons';
import { Button,Form,Input,InputNumber,Select } from "antd";
import { useState } from "react";
import { ContextGlobal } from "../globalState/GlobalState";
import { useNavigate } from "react-router-dom";


const {TextArea} = Input;
const {Option} = Select;

function AddProducts(){
  const [listCategory, setListCategory] = useState([]);
  const [listLocations, setListLocations] = useState([]);
  const [body, setBody] = useState();
  const navigate = useNavigate();

  //Estado para guardar la información del producto
  const [informationProduct, setInformationProduct] = useState({
    title: "",
    categoryId: 0,
    locationId: 0,
    altitude: 0,
    description: "",
    maxReservations: 0,
  });

  const [informationPolicies,setInformationPolicies] = useState({
    normas: "",
    salud: "",
    cancelaciones: "",
  });

  const [informationFeatures, setInformationFeatures] = useState({

  })

  const [informationImages, setInformationImages] = useState({

  })

  const changeName = (e) => {
    setInformationProduct({...informationProduct, title: e.target.value});
  }

  const changeCategory = (e) => {
    const value = parseInt(e);
    setInformationProduct({...informationProduct, categoryId: value});
  }

  const changeLocation = (e) => {
    setInformationProduct({...informationProduct, locationId: e});
  }

  const changeAltitude = (e) => {
    const value = parseInt(e.target.value);
    setInformationProduct({...informationProduct, altitude: value});
    console.log(informationProduct)
  }

  const changequotas = (e) => {
    const value = parseInt(e.target.value); 
    setInformationProduct({...informationProduct, maxReservations: value});
    console.log(informationProduct)
  }

  const changeDescription = (e) => {
    setInformationProduct({...informationProduct, description: e.target.value});
  }

  const changeNormas = (e) => {
    setInformationPolicies({...informationPolicies, normas: e.target.value});
  }

  const changeSalud = (e) => {
    setInformationPolicies({...informationPolicies, salud: e.target.value});
  }

  const changeCancelaciones = (e) => {
    setInformationPolicies({...informationPolicies, cancelaciones: e.target.value});
  }

  const changeFeatures = (e) => {
    setInformationFeatures({...informationFeatures, [e.target.id]: e.target.value})
  }

  const removeFeatures = (clave) => {
    console.log(clave)
    const featuresIndex = 'features_0';
    const newFeaturesInformation = informationFeatures;
    if(clave === 0) {
      delete newFeaturesInformation[featuresIndex]
    } else {
      delete newFeaturesInformation[clave]
    }
    setInformationFeatures(newFeaturesInformation)
  }

  const changeImages = (e) => {
    setInformationImages({...informationImages, [e.target.id]: e.target.value})
  }

  const removeImages = (clave) => {
    const imagesIndex = 'images_0';
    const newInformationImages = informationImages;
    if(clave === 0){
      delete newInformationImages[imagesIndex]
    } else {
      delete newInformationImages[clave]
    }
    setInformationImages(newInformationImages)
  }

  const createProduct = async (url, data) => {
    const datos = await fetch(url, data);
    const responseCreate = await datos.json();

    // console.log(responseCreate.id)
    if (responseCreate.id) {
      navigate("/administrationProducts/successful")
    } else {
      alert("Por favor intenta mas tarde");
    }
  } 

    const handleSubmit = () => {
      const urlCreateProduct = 'http://18.220.89.28:8080/api/productos';
      const imageUrl = [];
      for (let clave in informationImages) {
        imageUrl.push(informationImages[clave]);
      }
      const featureTitle = [];
      for (let clave in informationFeatures) {
        featureTitle.push(informationFeatures[clave]);
      }
      let policy = "";
      for (let clave in informationPolicies) {
        policy += informationPolicies[clave] + " ";
      }
      const body = {...informationProduct, imageUrl, featureTitle, policy};
      console.log(body)
      setBody(body)
      
  }

  useEffect(()=>{
    if(body){
      console.log(body)
      const createProducts = {
      method: 'POST',
      body: JSON.stringify(body),
      headers: {
        'Content-Type': 'application/json'
      }
    }
    createProduct('http://18.220.89.28:8080/api/productos', createProducts)
    }
    
  },[body])


  const urlCategories = "http://18.220.89.28:8080/api/categorias";
  const urlLocations = "http://18.220.89.28:8080/api/localizaciones";

  async function getCategories(url) {
    const data = await fetch(url);
    const categories = await data.json();
    setListCategory(categories)
  }

  async function getLocations(url) {
    const dataLocations = await fetch(url,{
      headers: {
        'Authorization': 'Bearer' + localStorage.getItem('token'),
        'Content-Type': 'application/json'
    }
    });
    const locations = await dataLocations.json();
    setListLocations(locations)
  }

  // console.log(informationProduct)
  console.log(informationImages)

  useEffect(() => {
    getCategories(urlCategories);
    getLocations(urlLocations);
  }, [])

      return(
      <Form className={styles.formProduct}
        // labelCol={{
        //   span: 4,
        // }}
        // wrapperCol={{
        //   span: 14,
        // }}
        // layout="horizontal"
      >


        <h1>Administración de Productos</h1>
        <div>
          <h2>Información del producto</h2>
          <div className={styles.informationProduct}>
        <Form.Item label="Nombre">
          <Input placeholder={'Ingrese el nombre del producto'} onChange={changeName}/>
        </Form.Item>
        <Form.Item label="Categoría">
          <Select placeholder={'Seleccione la categoría'} onChange={changeCategory}>
            {listCategory?.map(category => (
              <Option key={category.categoryId} id={category.categoryId} value={category.id}>{category.title}</Option>
            ))}
          </Select>
        </Form.Item>
        <Form.Item label="Ubicación">
          <Select placeholder={'Seleccione la ubicación'} onChange={changeLocation}>
            {listLocations?.map(location => (
              <Option key={location.idLocation} id={location.idLocation} value={location.idLocation}>{location.place}</Option>
            ))}
          </Select>
        </Form.Item>

        
        <Form.Item label="Altitud">
          <Input placeholder={'Ingrese la altitud en números sin comas ni puntos'} onChange={changeAltitude}/>
        </Form.Item>
        <Form.Item label="Cupos">
          <Input type="number" placeholder="Ingrese el número de cupos del producto" onChange={changequotas} />
        </Form.Item>
        </div>
        <Form.Item label="Descripción" className={styles.description}>
          <TextArea rows={4} onChange={changeDescription}/>
        </Form.Item>
        </div>
        <div>
          <h2>Características</h2>
        <Form.List
        name="features"
        rules={[
          {
            validator: async (_, features) => {
              if (!features || features.length > 8) {
                return Promise.reject(new Error('Debes agregar máximo 8 características'));
              }
            },
          },
        ]}
      >
        {(fields, { add, remove }, { errors }) => (
          <>
            {fields.map((field, index) => (
              <div className={styles.features}>
              <Form.Item
                // {...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
                // label={'Característica'}
                required={false}
                key={field.key}
              >
                <Form.Item
                  {...field}
                  validateTrigger={['onChange', 'onBlur']}
                  rules={[
                    {
                      required: true,
                      whitespace: true,
                      message: "Por favor ingresa la característica del producto.",
                    },
                  ]}
                  label={"Nombre"}
                  // noStyle
                >
                  <Input id={field.key} placeholder="Agregar característica" style={{ width: '60%' }} onChange={changeFeatures} />
                </Form.Item>
                {/* <Form.Item label={"Icono"}>
                  <Input placeholder="hola" style={{ width: '60%' }}></Input>
                </Form.Item> */}
                {fields.length > 1 ? (
                  <MinusCircleOutlined
                    className="dynamic-delete-button"
                    onClick={() => {remove(field.name) ; removeFeatures(field.key); console.log(field)}}
                  />
                ) : null}
              </Form.Item>
              </div>
            ))}
            <Form.Item>
            <div className={styles.buttonAdd}>
              <Button
                type="dashed"
                onClick={() => add()}
                icon={<PlusOutlined />}
                className={styles.button}
              >
                Agregar característica
              </Button>
              </div>
              <Form.ErrorList errors={errors} />
            </Form.Item>
          </>
        )}
        
      </Form.List>
      </div>

        <div>
          <h2>Políticas del producto</h2>
          <div className={styles.policyProduct}>
        <Form.Item label="Normas">
          <TextArea rows={4} onChange={changeNormas}/>
        </Form.Item>
        <Form.Item label="Salud">
          <TextArea rows={4} onChange={changeSalud}/>
        </Form.Item>
        <Form.Item label="Cancelaciones">
          <TextArea rows={4} onChange={changeCancelaciones}/>
        </Form.Item>
        </div>
        </div>

        <div>
          <h2>Cargar imágenes</h2>
        <Form.List
        name="images"
        rules={[
          {
            validator: async (_, images) => {
              if (!images || images.length < 5) {
                return Promise.reject(new Error('Debes agregar almenos cinco imagenes'));
              }
            },
          },
        ]}
      >
        {(fields, { add, remove }, { errors }) => (
          <>
            {fields.map((field, index) => (
              <div className={styles.images}>
              <Form.Item
                // {...(index === 0 ? formItemLayout : formItemLayoutWithOutLabel)}
                label={'Url Imagen'}
                required={false}
                key={field.key}
              >
                <Form.Item
                  {...field}
                  validateTrigger={['onChange', 'onBlur']}
                  rules={[
                    {
                      required: true,
                      whitespace: true,
                      message: "Por favor ingresa la url de la imagen.",
                    },
                  ]}
                  noStyle
                >
                  <Input id={field.key} placeholder="Insertar url imagen" style={{ width: '60%' }} onChange={changeImages}/>
                </Form.Item>
                {fields.length > 1 ? (
                  <MinusCircleOutlined
                    className="dynamic-delete-button"
                    onClick={() => {remove(field.name); removeImages(field.key)}}
                  />
                ) : null}
              </Form.Item>
              </div>
            ))}
            <Form.Item>
            <div className={styles.buttonAdd}>
              <Button
                type="dashed"
                onClick={() => add()}
                icon={<PlusOutlined />}
                className={styles.button}
              >
                Agregar imagen
              </Button>
              </div>
              <Form.ErrorList errors={errors} />
            </Form.Item>
          </>
        )}
      </Form.List>
      </div>







        <Form.Item>
          <div className={styles.buttonAdd}>
          <Button className={styles.button} onClick={handleSubmit}>Crear Producto</Button>
          </div>
        </Form.Item>
      </Form>
      )
}

export default AddProducts;