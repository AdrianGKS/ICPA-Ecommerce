import React from 'react';
import { BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Dashboard from '../page/Dashboard';
import Orders from '../page/Orders';
import Products from '../page/Products';
import Login from '../page/Login';
import Recover from '../page/Recover';
import Customers from '../page/Customers';
import Reports from '../page/Reports';
import Transactions from '../page/Transactions';
import Shipment from '../page/Shipment';
import Settings from '../page/Settings';
import Helpe from '../page/Helpe';

const Navegation: React.FC = () => {
  return <Router>
  <Routes> 
    <Route path="/login" element={<Login/>} />
    <Route path="/recover" element={<Recover/>} />
    <Route path="/" element={<Dashboard/>} />
    <Route path="/orders" element={<Orders/>} />
    <Route path="/products" element={<Products/>} />
    <Route path="/customers" element={<Customers/>} />
    <Route path="/reports" element={<Reports/>} />
    <Route path="/transactions" element={<Transactions/>} />
    <Route path="/shipment" element={<Shipment/>} />
    <Route path="/settings" element={<Settings/>} />
    <Route path="/helpe" element={<Helpe/>} />
  </Routes>
</Router>
}

export default Navegation;