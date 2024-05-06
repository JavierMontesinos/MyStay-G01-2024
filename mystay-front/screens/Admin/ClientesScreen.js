import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useIsFocused } from '@react-navigation/native';

import { TitleText } from '../../components/CustomText'
import { get, validJWT } from '../../utils/Requests';
import AuthContext from '../../utils/AuthProvider';

const ClientesScreen = () => {
  const [clientes, setClientes] = useState([]);

  const isFocused = useIsFocused();
  const { signOut } = React.useContext(AuthContext);

  useEffect(() => {
    fetchData();
  }, [isFocused]);

  const fetchData = async () => {
    try {
      const clientesResponse = await get("clientes");
      setClientes(clientesResponse.map(cliente => ({nombre: `${cliente.nombre} - ${cliente.dni}`, telefono: cliente.telefono, hotel: cliente.hotel.nombre, habitacion: cliente.habitacion.numero})));
    } catch (error) {
      if (validJWT(error.response?.data, signOut)) {
        console.log(error)
        if (error.response?.data){
          alert(error.response?.data)
        } else {
          alert(error)
        }
      }
    }
  };


  return (
    <ScrollView contentContainerStyle={styles.container}>
        <TitleText text={"LISTA DE CLIENTES"} />
      {clientes.map((cliente, index) => (
        <View key={index} style={styles.reservationContainer} testID='scrollitem'>
          <Text style={styles.label}>Informacion:</Text>
          <Text style={styles.info}>{cliente.nombre}</Text>
          <Text style={styles.label}>Telefono:</Text>
          <Text style={styles.info}>{cliente.telefono}</Text>
          <Text style={styles.label}>Hotel:</Text>
          <Text style={styles.info}>{cliente.hotel}</Text>
          <Text style={styles.label}>Habitación:</Text>
          <Text style={styles.info}>{cliente.habitacion}</Text>
        </View>
      ))}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    backgroundColor: '#f0f0f0',
    paddingVertical: 20,
    paddingHorizontal: 15,
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
    color: '#333',
  },
  reservationContainer: {
    backgroundColor: '#fff',
    borderRadius: 8,
    padding: 15,
    marginBottom: 15,
    elevation: 3,
  },
  label: {
    fontWeight: 'bold',
    marginBottom: 5,
    color: '#555',
  },
  info: {
    marginBottom: 10,
    color: '#333',
  },
});

export default ClientesScreen;
