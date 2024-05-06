import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useIsFocused } from '@react-navigation/native';

import { TitleText } from '../../components/CustomText'
import { get, validJWT } from '../../utils/Requests';
import AuthContext from '../../utils/AuthProvider';

const EmpleadosScreen = () => {
  const [empleados, setEmpleados] = useState([]);

  const isFocused = useIsFocused();
  const { signOut } = React.useContext(AuthContext);

  useEffect(() => {
    fetchData();
  }, [isFocused]);

  const fetchData = async () => {
    try {
      const empleadosResponse = await get("empleados");
      setEmpleados(empleadosResponse.map(empleado => ({nombre: `${empleado.nombre} - ${empleado.dni}`, telefono: empleado.telefono, hotel: empleado.hotel.nombre, correo: empleado.correo})));
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
        <TitleText text={"LISTA DE EMPLEADOS"} />
      {empleados.map((empleado, index) => (
        <View key={index} style={styles.reservationContainer}>
          <Text style={styles.label}>Nombre:</Text>
          <Text style={styles.info}>{empleado.nombre}</Text>
          <Text style={styles.label}>Correo:</Text>
          <Text style={styles.info}>{empleado.correo}</Text>
          <Text style={styles.label}>Telefono:</Text>
          <Text style={styles.info}>{empleado.telefono}</Text>
          <Text style={styles.label}>Hotel:</Text>
          <Text style={styles.info}>{empleado.hotel}</Text>
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

export default EmpleadosScreen;
