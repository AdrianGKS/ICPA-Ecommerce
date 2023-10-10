import React from 'react';
import * as HiIcon from "react-icons/hi";
import logo from './../assets/logoW.svg'
import { BrowserRouter as Router, Routes, Route, NavLink} from "react-router-dom";
export const Menu: React.FC = () => {
  const menu = [
    {
      title: 'Dashboard',
      path: '/',
      icon: <HiIcon.HiOutlineViewGrid className="icon-menu me-2"/>
    },{
      title: 'Orders',
      path: '/orders',
      icon: <HiIcon.HiOutlineShoppingCart className="icon-menu me-2"/>
    },{
      title: 'Products',
      path: '/products',
      icon: <HiIcon.HiOutlineFolderOpen className="icon-menu me-2"/>
    },{
      title: 'Customers',
      path: '/customers',
      icon: <HiIcon.HiOutlineUsers className="icon-menu me-2"/>
    },{
      title: 'Reports',
      path: '/reports',
      icon: <HiIcon.HiOutlineChartSquareBar className="icon-menu me-2"/>
    },{ 
      title: 'Transactions',
      path: '/transactions',
      icon: <HiIcon.HiOutlineCreditCard className="icon-menu me-2"/>
    },{
      title: 'Shipment',
      path: '/shipment',
      icon: <HiIcon.HiOutlineTruck className="icon-menu me-2"/>
    }
  ];
  return <div className="menu">
    <div className="logo mb-4">
      <img src={logo} alt="" />
    </div>
    <div className='nav flex-column'>
      {
        menu.map (function (item){
          return (
            <div className="nav-item">            
              <NavLink to={item.path} className='menu-link nav-link '>
                <span className='icon me-2'>{item.icon}</span>
                {item.title}
              </NavLink>
            </div>
          )
        })
      }
      <div className='menu-option'>
      <div className="nav-item">            
              <NavLink to='/settings' className='menu-link nav-link'>
                <span className='icon me-2'><HiIcon.HiOutlineCog/></span>
                Settings
              </NavLink>
            </div>
            <div className="nav-item">            
              <NavLink to='/helpe' className='menu-link nav-link'>
                <span className='icon me-2'><HiIcon.HiOutlineInformationCircle/></span>
                Helpe Center
              </NavLink>
            </div>
            <div className="nav-item">            
              <NavLink to='/login' className='menu-link nav-link'>
                <span className='icon me-2'><HiIcon.HiOutlineLogout/></span>
                Logout
              </NavLink>
            </div>
      </div>
    </div>
  </div>
}