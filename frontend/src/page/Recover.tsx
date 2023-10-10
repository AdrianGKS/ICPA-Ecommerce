import React from 'react';
import {Link} from 'react-router-dom'
import {
  Container,
  Row,
  Col
} from 'react-bootstrap'
import { HiOutlineLockClosed, HiOutlineUser } from "react-icons/hi";

const Recover: React.FC = () => {
  return <main className='auth'>
  <div className='auth-background'/>
<section>
  <Container>
    <Row>
      <Col lg={6}>
        <h2>Recuperar senha</h2>
        <form>
          <div className='cad-from border mb-4'>
            <div className='label'>
              <span>Email</span>
            </div>
            <div className='input-group'>
            <input 
            type="email" 
            className='form-control'
            placeholder='Insere seu email'
            required
            />
            <span>
              <HiOutlineUser/>
            </span>
            </div>
          </div>
          <button 
          type="submit" 
          className="btn btn-dark">
            Enviar
          </button>
          <Link to="/login" className='mt-2 btn text-center border-0 w-100'>Cancelar</Link>
        </form>
      </Col>
    </Row>
  </Container>
</section>
</main>
}

export default Recover;