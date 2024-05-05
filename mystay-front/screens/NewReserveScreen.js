import React, { useEffect, useState } from 'react';

import { View, Text, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { Calendar } from 'react-native-calendars';
import { useIsFocused } from '@react-navigation/native';

import { TitleText } from '../components/CustomText'
import CustomButton from '../components/CustomButton';
import { get, post, validJWT } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';

import RNPickerSelect from 'react-native-picker-select';

const NewReserveScreen = ({ navigation }) => {
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [hoteles, setHoteles] = useState([]);

  const [selectedDate, setSelectedDate] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const isFocused = useIsFocused();
  const { signOut } = React.useContext(AuthContext);

  const sendReservation = async (navigation) => {
    try {
      let startDateTemp = new Date(startDate)
      let endDateTemp = new Date(endDate)
      
      const response = await post('cliente/reservas', {
        fechaInicio: startDateTemp.toISOString(),
        fechaFinal: endDateTemp.toISOString(),
        hotel: selectedHotel,
      });

      alert(response)

      navigation.navigate("Profile")
    } catch (error) {
      console.log(error)
      if (error instanceof RangeError) {
        alert("Escoge una fecha de entrada y salida");
      } else {
        if (validJWT(error.response?.data, signOut)) {
          console.log(error.response.data)
          alert(error.response.data)
        }
      }
    }
  };

  const handleSelectDate = (date) => {
    const newDate = date.dateString;
    if (!startDate) {
      setStartDate(newDate);
    } else if (startDate && !endDate) {
      if (newDate > startDate) {
        setEndDate(newDate);
      } else {
        setStartDate(newDate);
      }
    } else {
      setStartDate('');
      setEndDate('');
    }
    setSelectedDate(newDate);
  };

  const getHoteles = async () => {
    try {
      const hoteles = await get("hoteles");
      setHoteles(hoteles.map(hotel => ({label: hotel.nombre, value: hotel.id })))
    } catch(error) {
      if (validJWT(error.response?.data, signOut)) {
        console.log(error.response?.data)
        alert(error.response?.data)
      }
    }
  }

  useEffect(() => {
    if (isFocused){
      getHoteles();
    }
  }, [isFocused])

  return (
    <ScrollView style={styles.container}>
      <TitleText text={"Reserva"} style={{textAlign: "center"}} />
      
      <Calendar
        onDayPress={handleSelectDate}
        theme={{
          calendarBackground: '#36454F', // Light gray background
          selectedDayBackgroundColor: '#F59B00', // Orange for selected day
          selectedDayTextColor: '#fff',
          dayTextColor: '#fff', // Dark gray day text color
          textDayHeaderFontWeight: 'bold', // Bold day header text
        }}
      />
      <View style={styles.dateLabelsContainer}>
        <Text style={styles.dateLabel}>Fecha Entrada:</Text>
        <Text style={styles.dateValue}>{startDate || 'Sin fecha seleccionada'}</Text>
      </View>
      <View style={styles.dateLabelsContainer}>
        <Text style={styles.dateLabel}>Fecha Salida:</Text>
        <Text style={styles.dateValue}>{endDate || 'Sin fecha seleccionada'}</Text>
      </View>

      <View style={styles.container}>
        <Text style={styles.headerText}>Elige el hotel para hacer la reserva</Text>
        <RNPickerSelect
          onValueChange={(value) => setSelectedHotel(value)}
          items={ hoteles }
          placeholder={{ label: 'Elige el hotel para hacer la reserva...', value: null }}
          value={selectedHotel}
          touchableWrapperProps={{testID: 'picker-select'}}
        />
      </View>

      <View style={{ flex: 1, alignItems: 'flex-end', marginTop: 5, marginBottom: 40}}>
        <CustomButton icon={""} text={"Submit"} func={() => sendReservation(navigation)} testID={"btn-reserve"} />
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
  },
  dateLabelsContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 20,
  },
  dateLabel: {
    flex: 1,
    fontSize: 16,
    color: '#333',
  },
  dateValue: {
    flex: 2,
    fontSize: 16,
    color: '#666',
  },
  button: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    paddingVertical: 12,
    borderRadius: 5,
    marginTop: 20,
    borderWidth: 1,
    borderColor: '#F59B00',
  },
  buttonText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#F59B00',
    marginRight: 10,
  },
  disabledButton: {
    opacity: 0.5,
    borderColor: '#888',
  },
});

export default NewReserveScreen;
