import React, { useState } from 'react';
import { View, Text, StyleSheet, Button } from 'react-native';
import { CheckBox } from 'react-native-elements';
import { TitleText } from '../components/CustomText';
import axios from 'axios';
import CustomButton from '../components/CustomButton';

const ComfortServiceScreen = () => {
  const [isChecked1, setIsChecked1] = useState(false);
  const [isChecked2, setIsChecked2] = useState(false);
  const [isChecked3, setIsChecked3] = useState(false);
  const [isChecked4, setIsChecked4] = useState(false);

  const postService = async (tipo, descripcion) => {
    try {
      const serviceData = {
        tipo,
        descripcion: "Servicio comfort",
        fecha: new Date().toISOString()
      };
  
      const response = await axios.post('http://localhost:8443/services/1', serviceData);
      console.log('Service created successfully:', tipo);
      return true
    } catch (error) {
      console.log(error.response.data)
      console.error('Error creating service:', error);
      return false
    }
  };

  const handleSubmit = () => {
    const selectedServices = [
      { name: 'Ropa de cama', checked: isChecked1 },
      { name: 'Toallas de baño', checked: isChecked2 },
      { name: 'Almohadas', checked: isChecked3 },
      { name: 'Productos de baño', checked: isChecked4 },
    ];
    const selectedItems = selectedServices.filter(service => service.checked);
    const selectedServiceNames = selectedItems.map(service => service.name).filter(name => postService(name));
    alert(`Servicios Seleccionados: ${selectedServiceNames.join(', ')}`);
  };
  return (
    <View style={styles.container}>
      <TitleText text={"SERVICIOS"} />
      <View style={styles.serviceList}>
        <View style={styles.serviceItem}>
          <Text style={styles.serviceName}>Ropa de cama</Text>
          <CheckBox center checked={isChecked1} onPress={() => setIsChecked1(!isChecked1)} checkedColor='blue'/>
        </View>
        <View style={styles.serviceItem}>
          <Text style={styles.serviceName}>Toallas de baño</Text>
          <CheckBox center checked={isChecked2} onPress={() => setIsChecked2(!isChecked2)} checkedColor='blue'/>
        </View>
        <View style={styles.serviceItem}>
          <Text style={styles.serviceName}>Almohadas</Text>
          <CheckBox center checked={isChecked3} onPress={() => setIsChecked3(!isChecked3)} checkedColor='blue'/>
        </View>
        <View style={styles.serviceItem}>
          <Text style={styles.serviceName}>Productos de baño</Text>
          <CheckBox center checked={isChecked4} onPress={() => setIsChecked4(!isChecked4)} checkedColor='blue'/>
        </View>
      </View>
      <CustomButton text="Submit" func={handleSubmit} />
    </View>

  );
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'center',
    },
    serviceList: {
      width: '80%',
      backgroundColor: '#f2f2f2',
      borderRadius: 10,
      padding: 10,
    },
    serviceItem: {
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'space-between',
      marginBottom: 10,
    },
    serviceName: {
      fontSize: 16,
    },
});

export default ComfortServiceScreen;
