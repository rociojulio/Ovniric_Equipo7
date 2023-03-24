import React from 'react';
import './App.css';
import { Routes, Route,Navigate } from 'react-router-dom';
import Header from './Components/Header/Header';
import Footer from './Components/Footer/Footer';
import Login from "./Components/Login/Login.jsx"
import Register from "./Components/Register/Register.jsx";
import Main from './Components/Main/Main';
import { GlobalContext } from './Components/globalState/GlobalState';
import SearchBlock from "./Components/SearchBlock/SearchBlock";
import { useEffect, useState} from "react";
import BookingCalendar from './Components/BookingCalendar/BookingCalendar.jsx';
import ProductDetailView from './Components/ProductDetailView/ProductDetailView';
import FilterCategory from './Components/FilterCategory/FilterCategory';
import BookingForm from './Components/BookingForm/BookingForm';
import Reservations from './Components/Reservations/Reservations';
import SuccessfulReservation from './Components/SuccessfulReservation/SuccessfulReservation';
import MyBookings from './Components/MyBookings/MyBookings';
import SuccessfulAdd from './Components/AddProducts/SuccessfulAdd';
import AddProducts from './Components/AddProducts/AddProducts';
import './stylesVariables/variables.css'



function App() {

const {isLoged,dataproduct,idProduct,setIdProduct,setDataProduct} = GlobalContext();
const [theme, setTheme] = useState('light');

function changeTheme() {
  console.log("working")
  console.log(theme)
  if (theme === 'light') {
    setTheme('dark');
    document.documentElement.style.setProperty('--background', 'linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%), linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%)');
    document.documentElement.style.setProperty('--bar', 'linear-gradient(to bottom, #0c3483 0%, #a2b6df 100%, #6b8cce 100%, #a2b6df 100%)');
    document.documentElement.style.setProperty('--backImage', 'radial-gradient(at top center, rgba(255,255,255,0.03) 0%, rgba(0,0,0,0.03) 100%), linear-gradient(to top, rgba(255,255,255,0.1) 0%, rgba(143,152,157,0.60) 100%)');
    document.documentElement.style.setProperty('--color', 'azure');
  } else {
    setTheme('light');
    document.documentElement.style.setProperty('--background', 'linear-gradient(to top, #e6e9f0 0%, #eef1f5 100%)');
    document.documentElement.style.setProperty('--bar', 'linear-gradient(to bottom, #0c3483 0%, #a2b6df 100%, #6b8cce 100%, #a2b6df 100%)');
    document.documentElement.style.setProperty('--backImage', 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)');
    document.documentElement.style.setProperty('--color', '#3F3F3F');
  }
}



  return <>
  <Header changeTheme={changeTheme} />
  <Routes>
        <Route path="/" 
        element={<>
                    <SearchBlock/>
                    <Main></Main>
                  </>}/>
        <Route path="/signup" element={<Register></Register>} />
        <Route path="/login" element={ !isLoged? <Login></Login> : <Navigate to="/"/>} />
        <Route path="/productdetails/:id" element={<ProductDetailView></ProductDetailView>} />
        <Route path='/categoryProducts/:id' element={<FilterCategory></FilterCategory>}></Route>
        {/* <Route path='/bookingForm' element={<BookingForm></BookingForm>}></Route> */}
        <Route path='/administrationProducts' element={<AddProducts></AddProducts>}></Route>
        <Route path='/administrationProducts/successful' element={<SuccessfulAdd></SuccessfulAdd>}></Route>
        <Route path='/reservations/:id' element={<Reservations></Reservations>}></Route>
        <Route path='/successfulReservation' element={<SuccessfulReservation></SuccessfulReservation>}></Route>
        <Route path='/MisReservas'element={<MyBookings></MyBookings>}></Route>
  </Routes>      
  <Footer/>
</>;
}
export default App;

