import axios from 'axios';

const hotel = {
  "nombre": "Grand Hotel",
  "direccion": "Main Street 123",
  "id": 2000
}

const habitacion = {
  "numero": 153,
  "tipo": "Single",
  "precio": 100.0,
  "id": 2000
}

const cliente = {
  "nombre": "John Doe",
  "correo": "johndoe@example.com",
  "password": "password123",
  "telefono": "12345689",
  "premium": false,
  "gasto": habitacion.precio,
  "pagado": true,
  "dni": "123456789",
  "id": 2000
}

const nuevoNombre = 'Juan Jose';

let admin = "";
let clienteID = 0;
let habitacionID = 0;
let hotelID = 0;

const instance = axios.create({
  baseURL: 'http://localhost:8443'
});


describe('Login screen', () => {
  
  afterAll(async () => {
    await instance.delete(`/clientes/${clienteID}`);
    await instance.delete(`/habitaciones/${habitacionID}`);
    await instance.delete(`/hoteles/${hotelID}`);

  });

  beforeAll(async () => {
    try {
      let response = await instance.post("/login", {dni: "admin", nhab: 12345});
      admin = response.data;

      instance.defaults.headers.common['Authorization'] = `Bearer ${admin}`;
      
      response = await instance.post("/hoteles", hotel, {headers: {'Content-Type': 'application/json'}});
      let hotelTemp = response.data;

      
      response = await instance.post("/habitaciones", habitacion, {headers: {'Content-Type': 'application/json'}});
      let habitacionTemp = response.data;
      
      await instance.put(`/habitaciones/${habitacionTemp.id}/hotel/${hotelTemp.id}`);

      response = await instance.post("/clientes", cliente, {headers: {'Content-Type': 'application/json'}});
      let clienteTemp = response.data;

      await instance.put(`/clientes/${clienteTemp.id}/habitacion/${habitacionTemp.id}`);

      await instance.put(`/clientes/${clienteTemp.id}/hotel/${hotelTemp.id}`);


      clienteID = clienteTemp.id;
      habitacionID = habitacionTemp.id;
      hotelID = hotelTemp.id;
      
    } catch (error) { 
      console.error(error)
      throw error;
    }

    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it('Login form should be visible', async () => {
    await expect(element(by.id('dni-login'))).toBeVisible();
    await expect(element(by.id('nhab-login'))).toBeVisible();
    await expect(element(by.id('btn-login'))).toBeVisible();
  });

  
  it('Should login with provided credentials', async () => {
    await element(by.id('dni-login')).typeText(cliente.dni);
    await element(by.id('nhab-login')).typeText(`${habitacion.numero}\n`);
    
    await element(by.id('btn-login')).tap();
  });

  
  it('Should be logged in after restarting app', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
  });

  it('Should be able to edit user info', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    
    await element(by.text('Edita tus datos personales')).tap();
    
    await waitFor(element(by.text(cliente.nombre))).toBeVisible().withTimeout(2000);
    await element(by.text(cliente.nombre)).replaceText(nuevoNombre);

    await element(by.text('Submit')).tap();

    await waitFor(element(by.text('OK'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();

    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
  });

  it('Should be able to persist user info changes', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    
    await element(by.text('Edita tus datos personales')).tap();
    await waitFor(element(by.text(nuevoNombre))).toBeVisible().withTimeout(2000);
  });
});
