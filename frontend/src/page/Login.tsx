import React from 'react';
import {Link} from 'react-router-dom'
import {
  Container,
  Row,
  Col
} from 'react-bootstrap'
import { HiOutlineLockClosed, HiOutlineUser } from "react-icons/hi";

const Login: React.FC = () => {
  return <main className='auth'>
    <div className='auth-background'/>
  <section>
    <Container>
      <Row>
        <Col lg={5}>
          <h2>Login</h2>
          <form>
            <div className='cad-from border mb-4'>
              <div className='label'>
                <span>Email</span>
              </div>
              <div className='input-group'>
              <input 
              type="email" 
              className='form-control'
              placeholder='examplo@mail.com'
              required
              />
              <span>
                <HiOutlineUser/>
              </span>
              </div>
            </div>
            <div className='cad-from border mb-3'>
              <div className='label'>
                <span>Senha</span>
              </div>
              <div className='input-group'>
              <input 
              type="password" 
              className='form-control'
              placeholder='6+ strong caracteres'
              required
              />
              <span>
                <HiOutlineLockClosed/>
              </span>
              </div>
            </div>
            <Link to="/recover">Esqueci minha senha?</Link>
            <button 
            type="submit" 
            className="btn btn-dark mt-3">
              Entrar
            </button>
          </form>
        </Col>
      </Row>
    </Container>
  </section>
</main>
}

export default Login;