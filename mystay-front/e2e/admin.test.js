import axios from 'axios';

const hotel = {
    "nombre": "Dummy Grands",
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
    "pagado": false,
    "dni": "123456789",
    "id": 2000
}

const empleado = {
    "nombre": "John Doe",
    "correo": "john.doe@example.com",
    "telefono": "123-456-7890",
    "disponible": true,
    "id": 2000,
    "dni": "123456789A"
}

let admin = "";
let clienteID = 0;
let habitacionID = 0;
let empleadoID = 0;
let hotelID = 0;

const instance = axios.create({
  baseURL: 'http://localhost:8443'
});


describe('Login screen', () => {
  beforeAll(async () => {
    try {
      let response = await instance.post("/login", { dni: "admin", nhab: "admin" });
      admin = response.data.token;

      instance.defaults.headers.common['Authorization'] = `Bearer ${admin}`;

      response = await instance.post("/hoteles", hotel, { headers: { 'Content-Type': 'application/json' } });
      let hotelTemp = response.data;


      response = await instance.post("/habitaciones", habitacion, { headers: { 'Content-Type': 'application/json' } });
      let habitacionTemp = response.data;

      await instance.put(`/habitaciones/${habitacionTemp.id}/hotel/${hotelTemp.id}`);

      response = await instance.post("/empleados", empleado, { headers: { 'Content-Type': 'application/json' } });
      let empleadoTemp = response.data;

      await instance.put(`/empleados/${empleadoTemp.id}/hotel/${hotelTemp.id}`);

      response = await instance.post("/clientes", cliente, { headers: { 'Content-Type': 'application/json' } });
      let clienteTemp = response.data;

      await instance.put(`/clientes/${clienteTemp.id}/habitacion/${habitacionTemp.id}`);

      await instance.put(`/clientes/${clienteTemp.id}/hotel/${hotelTemp.id}`);

      clienteID = clienteTemp.id;
      habitacionID = habitacionTemp.id;
      empleadoID = empleadoTemp.id;
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

  afterAll(async () => {
    await device.terminateApp();
    await new Promise(resolve => setTimeout(resolve, 1000));
  })

  it('Should login with provided credentials', async () => {
    await expect(element(by.id('dni-login'))).toBeVisible();
    await expect(element(by.id('nhab-login'))).toBeVisible();
    await expect(element(by.id('btn-login'))).toBeVisible();

    await element(by.id('dni-login')).typeText('admin');
    await element(by.id('nhab-login')).typeText('admin\n');

    await waitFor(element(by.id('btn-login'))).toBeVisible().withTimeout(2000);

    await element(by.id('btn-login')).tap();
  });

});

describe('All clientes', () => {
    beforeAll(async () => {
      await device.launchApp();
    });
  
    beforeEach(async () => {
      await device.reloadReactNative();
    });
  
    afterAll(async () => {
      await device.terminateApp();
      await new Promise(resolve => setTimeout(resolve, 1000));
    })
  
    it('Should be able to see all clients', async () => {
      await waitFor(element(by.text('ADMINISTRACION'))).toBeVisible().withTimeout(2000);
  
      await element(by.text('Ver todos los clientes')).tap();
  
      await waitFor(element(by.text(`${cliente.nombre} - ${cliente.dni}`))).toBeVisible().withTimeout(2000);
    });
});

describe('All empleados', () => {
    beforeAll(async () => {
      await device.launchApp();
    });
  
    beforeEach(async () => {
      await device.reloadReactNative();
    });
  
    afterAll(async () => {
      await device.terminateApp();
      await new Promise(resolve => setTimeout(resolve, 1000));
    })
  
    it('Should be able to see all empleados', async () => {
      await waitFor(element(by.text('ADMINISTRACION'))).toBeVisible().withTimeout(2000);
  
      await element(by.text('Ver todos los empleados')).tap();
  
      await waitFor(element(by.text(`${empleado.nombre} - ${empleado.dni}`))).toBeVisible().withTimeout(2000);
    });
});

describe('Logout', () => {
    afterAll(async () => {
      await instance.delete(`/clientes/${clienteID}`);
      await instance.delete(`/habitaciones/${habitacionID}`);
      await instance.delete(`/empleados/${empleadoID}`);
      await instance.delete(`/hoteles/${hotelID}`);
    });
  
    beforeAll(async () => {
      await device.launchApp();
    });
  
    beforeEach(async () => {
      await device.reloadReactNative();
    });
  
    it('Should be able to logout', async () => {
        await waitFor(element(by.text('ADMINISTRACION'))).toBeVisible().withTimeout(2000);
  
        await element(by.text('Logout')).tap();
  
        await waitFor(element(by.id('btn-login'))).toBeVisible().withTimeout(2000);
    });
  });