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

const nuevoNombre = 'Juan Jose';

let admin = "";
let clienteID = 0;
let habitacionID = 0;
let hotelID = 0;

const instance = axios.create({
  baseURL: 'http://localhost:8443'
});


describe('Login screen', () => {
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

  it('Should login with provided credentials', async () => {
    await expect(element(by.id('dni-login'))).toBeVisible();
    await expect(element(by.id('nhab-login'))).toBeVisible();
    await expect(element(by.id('btn-login'))).toBeVisible();

    await element(by.id('dni-login')).typeText(cliente.dni);
    await element(by.id('nhab-login')).typeText(`${habitacion.numero}\n`);
    
    await waitFor(element(by.id('btn-login'))).toBeVisible().withTimeout(2000);

    await element(by.id('btn-login')).tap();
  });

});

describe('Profile screen', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
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


describe('Checkout screen', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });


  it('Should not be able to see factura', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Check-out'))).toBeVisible().withTimeout(2000);
    await element(by.text('Check-out')).tap();

    await waitFor(element(by.text('Ver factura'))).toBeVisible().withTimeout(2000);
    await element(by.text('Ver factura')).tap();
    
    await waitFor(element(by.text(`Pagado: ${cliente.gasto}`))).not.toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();
  });

  it('Should be able to pay', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Check-out'))).toBeVisible().withTimeout(2000);
    await element(by.text('Check-out')).tap();

    await waitFor(element(by.text('Pagar'))).toBeVisible().withTimeout(2000);
    await element(by.text('Pagar')).tap();
    
    await waitFor(element(by.id('bank'))).toBeVisible().withTimeout(2000);
    await element(by.id('bank')).typeText('1234567899876');
    await element(by.id('cvv')).typeText('012\n');
    await element(by.id('btn-pay')).tap();

    await waitFor(element(by.text('OK'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();
  });

  it('Should not be able to pay', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Check-out'))).toBeVisible().withTimeout(2000);
    await element(by.text('Check-out')).tap();

    await waitFor(element(by.text('Pagar'))).toBeVisible().withTimeout(2000);
    await element(by.text('Pagar')).tap();
    
    await waitFor(element(by.id('bank'))).toBeVisible().withTimeout(2000);
    await element(by.id('bank')).typeText('1234567899876');
    await element(by.id('cvv')).typeText('012\n');
    await element(by.id('btn-pay')).tap();

    await waitFor(element(by.text('OK'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();

   await expect(element(by.text('PAGO DE LA CUENTA'))).toBeVisible(); 
  });

  it('Should be able to see factura', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Check-out'))).toBeVisible().withTimeout(2000);
    await element(by.text('Check-out')).tap();

    await waitFor(element(by.text('Ver factura'))).toBeVisible().withTimeout(2000);
    await element(by.text('Ver factura')).tap();
    
    await waitFor(element(by.text(`Pagado: ${cliente.gasto}`))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();
  });
});

describe('Reservas', () => {
  afterAll(async () => {
    await instance.delete(`/reservas/cliente/${clienteID}`);
    await instance.delete(`/clientes/${clienteID}`);
    await instance.delete(`/habitaciones/${habitacionID}`);
    await instance.delete(`/hoteles/${hotelID}`);
  });

  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });


  it('Should be able to create a new reserve', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Nueva reserva'))).toBeVisible().withTimeout(2000);
    await element(by.text('Nueva reserva')).tap();

    await waitFor(element(by.text('Reserva'))).toBeVisible().withTimeout(2000);
    
    await element(by.text('13')).tap();
    await element(by.text('19')).tap();
    
    await element(by.text('19')).swipe('up', 'fast', 1);
    
    await waitFor(element(by.id('android_picker'))).toBeVisible().withTimeout(2000);
    await element(by.id('android_picker')).tap();
    
    await element(by.text(hotel.nombre)).tap();
    await element(by.id('btn-reserve')).tap();

    await waitFor(element(by.text('OK'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();

    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
  });

  it('Should be able to persist the created reserve', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    
    await element(by.text('Consulta tus reservas')).tap();

    await waitFor(element(by.text(hotel.nombre))).toBeVisible().withTimeout(2000);
    await expect(element(by.text(/^13\/.*\/.*$/))).toBeVisible()
    await expect(element(by.text(/^19\/.*\/.*$/))).toBeVisible()
  });
});

describe('Servicios', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it('Should be able to request services', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Servicios confort'))).toBeVisible().withTimeout(2000);
    await element(by.text('Servicios confort')).tap();

    await waitFor(element(by.text('SERVICIOS'))).toBeVisible().withTimeout(2000);

    // Ropa de cama
    await element(by.id('checkbox')).atIndex(0).tap();

    // Almohadas
    await element(by.id('checkbox')).atIndex(2).tap();
    
    await element(by.text('Submit')).tap();

    await waitFor(element(by.text('Servicios Seleccionados: Ropa de cama, Almohadas'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();

  });
});

describe('Incidencias', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it('Should be able to create a incidence', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);
    await element(by.text('MI PERFIL')).swipe('right', 'fast', 0.2);

    await waitFor(element(by.text('Incidencias'))).toBeVisible().withTimeout(2000);
    await element(by.text('Incidencias')).tap();

    await element(by.id('incident')).typeText('Ejemplo incidencia');

    await waitFor(element(by.text('Enviar Incidencia'))).toBeVisible().withTimeout(2000);
    await element(by.text('Enviar Incidencia')).tap()

    
    await waitFor(element(by.text('Incidencia enviada con éxito'))).toBeVisible().withTimeout(2000);
    await element(by.text('OK')).tap();

  });
});

describe('Transporte', () => {
  beforeAll(async () => {
    await device.launchApp();
  });

  beforeEach(async () => {
    await device.reloadReactNative();
  });

  it('Should be able to request taxi', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);

    await element(by.label('Transport')).tap();

    await waitFor(element(by.text('Taxi'))).toBeVisible().withTimeout(2000);
    await element(by.text('Taxi')).tap();
  });

  it('Should be able to request airport transport', async () => {
    await waitFor(element(by.text('MI PERFIL'))).toBeVisible().withTimeout(2000);

    await element(by.label('Transport')).tap();

    await waitFor(element(by.text('Transporte al aeropuerto'))).toBeVisible().withTimeout(2000);
    await element(by.text('Transporte al aeropuerto')).tap();

  });

});