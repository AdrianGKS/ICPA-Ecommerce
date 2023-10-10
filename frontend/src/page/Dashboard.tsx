import React, { useState, useEffect, useContext } from 'react';
import { Menu } from '../components/Menu';
import { CardDashboard } from '../components/Cards';
import user from './../assets/user.jpg';
import * as HiIcon from "react-icons/hi";
import { 
  Container,
  Row,
  Col,
  Card,
  Tooltip,
  Dropdown,
  OverlayTrigger,
  Button
} from 'react-bootstrap';
import PropagateLoader from "react-spinners/PropagateLoader";
import { api } from './../services/api';
const Dashboard: React.FC = () => {
  const [appointments, setAppointments] = useState ([]);
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    setLoading(true);
    getAppointments();
  }, []);
  async function getAppointments() { // get - listar as informacoes da base de dados/Api
    const response = await api.get('/products');
      setAppointments(response.data); 
      setLoading(false);
  }
  return <main className='d-flex'>
    <section>
      <Menu/>
    </section>
    <Container className='p-4 pt-2'>
      <div className="d-flex justify-content-between">
        <h5 className='mb-4 mt-4'><b>Painel de controle</b></h5>
        <div>
        <div className='user-menu d-flex'>
          <div className="avatar">
            <img src={user} alt="" />
          </div>
          <div className='name ms-2'>
            <h6><b>Marcia Francisco</b></h6>
            <span>Administrador</span>
          </div>
        </div>
        </div>
      </div>      
      <CardDashboard/>
      <div className='mb-4 d-flex justify-content-between'>
        <div className='d-flex'>
        <div className="input-group">
          <input 
          type="text" 
          className="form-control" 
          placeholder="Pesquisar product"/>
          <span className="input-group-text">
          <HiIcon.HiOutlineSearch/>
          </span>
        </div>
        <div className="input-group ms-3">
        <select className="form-select" aria-label="Default select example">
          <option selected>Todos</option>
          <option value="1">One</option>
        </select>
          <span className="input-group-text">
          <HiIcon.HiOutlineFilter/>
          </span>
        </div>
        </div>
        <div>
          <div>
            <button 
            type="button" 
            className="btn btn-dark font-14">
              Adicionar product
            </button>
          </div>
        </div>
      </div>
      <Card className='card-table'>
      <Card.Body className='d-flex justify-content-between'>
        <div>
         <div><h6><b>Produtos</b></h6></div>
       </div>
        <div className='mb-2'>
         {['left'].map((placement) => (
          <OverlayTrigger
          key={placement}
          overlay={
          <Tooltip id={`tooltip-${placement}`} className='font-13'>
          Baixar arquivos
          </Tooltip>
          }>
            <Button variant="dark" className='btn-dwoArq'><HiIcon.HiOutlineNewspaper/></Button>
           </OverlayTrigger>
          ))}
              </div>
            </Card.Body>
            <div className='h-tablet'>
              <table className="table">
                <thead>
                  <tr>
                    <th className='ps-4'>Codigo</th>
                    <th>Foto</th>
                    <th>Nome</th>
                    <th>Categoria</th>
                    <th>Quantidade</th>
                    <th>Preço</th>
                    <th>Tamanho</th>
                    <th>Estado</th>
                    <th className='pe-4 text-right'>Acção</th>
                  </tr>
                </thead>
                <tbody>
                { !loading ?
                    appointments?.map(appointment => (                    
                    <tr>
                      <td className='ps-4 pt-3'>{appointment.codigo}</td>
                      <td>
                      <div className="img-prod d-flex align-items-center">
                        <img src={appointment.photo} alt="" />
                      </div>
                      </td>
                      <td className='pt-3'>{appointment.name}</td>
                      <td className='pt-3'>{appointment.categoria}</td>
                      <td className='pt-3'>{appointment.quantidade}</td>
                      <td className='pt-3'>{appointment.preco}</td>
                      <td className='pt-3'>{appointment.tamanho}</td>
                      <td className='pt-3'>
                      {
                        (appointment.quantidade === 0) ?
                        <span className="badge rounded-pill ps-3 pe-3 bg-danger">Estoque vazio</span>
                        : (appointment.quantidade === 1) ? 
                        <span className="badge rounded-pill ps-3 pe-3 bg-warning">Estoque em risco</span>                        
                        : (appointment.quantidade <= 2) ? 
                        <span className="badge rounded-pill ps-3 pe-3 bg-success">No estoque</span>
                        : <span className="badge rounded-pill ps-3 pe-3 bg-success">No estoque</span>
                      }
                      </td>
                      <td className='pe-4 text-right pt-3'>
                      <Dropdown>
                        <Dropdown.Toggle className='btn-dropdown' id="dropdown-basic">
                          <HiIcon.HiDotsVertical/>
                        </Dropdown.Toggle>
                        <Dropdown.Menu className='border-0 shadow-sm'>
                          <Dropdown.Item className='font-14' href="#/action-1">Ver</Dropdown.Item>
                          <Dropdown.Item className='font-14' href="#/action-2">Editar</Dropdown.Item>
                          <Dropdown.Item className='font-14'>Apagar</Dropdown.Item>
                        </Dropdown.Menu>
                      </Dropdown>
                      </td>
                    </tr>
                    )) : <div 
                          className="loading d-flex justify-content-center align-items-center">
                          <PropagateLoader 
                          loading={loading}
                          color="#262626"
                          size={10}
                          aria-label="Loading Spinner"
                          data-testid="loader"
                        />
                    </div>
                  }
                </tbody>
              </table>
             </div>
             <Card.Body>
              <div className='pagination pb-2 d-flex justify-content-between'>
                <div><h6><b>0 ate 0</b></h6></div>
                <div className="btn-group" role="group" aria-label="Basic example">
                  <button type="button" className="btn"><HiIcon.HiChevronLeft className='icon-btn-pagination'/></button>
                  <button type="button" className="btn"><HiIcon.HiChevronRight className='icon-btn-pagination'/></button>
                </div>
              </div>
            </Card.Body>
      </Card>
    </Container>
  </main>
}

export default Dashboard;