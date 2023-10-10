import React from 'react';
import * as HiIcon from "react-icons/hi";
import { 
  Container,
  Row,
  Col,
  Card
} from 'react-bootstrap';

export const CardDashboard: React.FC = () => {
  return <Row className='mb-4'>
  <Col md={3}>
    <Card>
    <Card.Body className='d-flex justify-content-between align-items-center'>
          <div>
            <h6><b>Produtos</b></h6>
           <b>200</b>
          </div>
          <HiIcon.HiOutlineFolderOpen className='icon-card text-primary'/>
        </Card.Body>
    </Card>
  </Col>
  <Col md={3}>
    <Card>
    <Card.Body className='d-flex justify-content-between align-items-center'>
          <div>
            <h6><b>Pedidos</b></h6>
           <b>200</b>
          </div>
          <HiIcon.HiOutlineShoppingCart className='icon-card text-info'/>
        </Card.Body>
    </Card>
  </Col>
  <Col md={3}>
    <Card>
    <Card.Body className='d-flex justify-content-between align-items-center'>
          <div>
            <h6><b>Visitantes</b></h6>
           <b>200</b>
          </div>
          <HiIcon.HiOutlineUsers className='icon-card text-success'/>
        </Card.Body>
    </Card>
  </Col>
  <Col md={3}>
    <Card>
    <Card.Body className='d-flex justify-content-between align-items-center'>
          <div>
            <h6><b>Saldo</b></h6>
           <b>$200k</b>
          </div>
          <HiIcon.HiOutlineCreditCard className='icon-card text-warning'/>
        </Card.Body>
    </Card>
  </Col>
</Row>
}

 