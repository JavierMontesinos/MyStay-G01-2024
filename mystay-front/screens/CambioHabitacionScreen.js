import React, { useEffect, useState } from 'react';

import { View, Text, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { useIsFocused } from '@react-navigation/native';

import { SubTitleText, TitleText } from '../components/CustomText'
import CustomButton from '../components/CustomButton';
import { get, put, validJWT } from '../utils/Requests';
import AuthContext from '../utils/AuthProvider';

import RNPickerSelect from 'react-native-picker-select';

const CambioHabitacionScreen = ({ navigation }) => {
	const [cliente, setCliente] = useState();
	const [current, setCurrent] = useState({});
	const [habitaciones, setHabitaciones] = useState([]);
	const [selected, setSelected] = useState({});

	const isFocused = useIsFocused();
	const { signOut } = React.useContext(AuthContext);

	const sendSolicitud = async (navigation) => {
		try {

			let clienteTemp = {...cliente, habitacion: selected.id};
			await put('cliente', clienteTemp);

			alert('Cambiado correctamente');

			navigation.navigate("Profile")
		} catch (error) {
			console.log(error)
			if (validJWT(error.response?.data, signOut)) {
				console.log(error)
				if (error.response?.data) {
					alert(error.response?.data)
				} else {
					alert(error)
				}
			}
		}
	};

	const getInfo = async () => {
		try {
			const cliente = await get("cliente");
			setCliente(cliente);

			setCurrent({ numero: cliente.habitacion.numero, id: cliente.habitacion.id, precio: cliente.habitacion.precio });

			const habitaciones = await get(`hotel/${cliente.hotel.id}/habitaciones`);

			setHabitaciones(habitaciones.map(habitacion => ({
				label: habitacion.numero.toString(),
				value: {
					id: habitacion.id,
					numero: habitacion.numero,
					precio: habitacion.precio
				}
			})).filter(habitacion => habitacion.value.id != cliente.habitacion.id)
			);

		} catch (error) {
			if (validJWT(error.response?.data, signOut)) {
				console.log(error)
				if (error.response?.data) {
					alert(error.response?.data)
				} else {
					alert(error)
				}
			}
		}
	}

	useEffect(() => {
		if (isFocused) {
			setSelected({});
			getInfo();

		}
	}, [isFocused])

	return (
		<ScrollView style={styles.container}>
			<TitleText text={"Cambio de habitación"} style={{ textAlign: "center" }} />

			<View style={styles.infoContainer}>
				<SubTitleText text={"Habitación actual"} />
				<Text style={styles.label}>Número:</Text>
				<Text style={styles.info}>{current.numero}</Text>
				<Text style={styles.label}>Precio:</Text>
				<Text style={styles.info}>{current.precio}</Text>
			</View>

			<View style={styles.infoContainer}>
				{selected?.id ?

					<>
						<SubTitleText text={"Nueva habitación"} />
						<Text style={styles.label}>Número:</Text>
						<Text style={styles.info}>{selected.numero}</Text>
						<Text style={styles.label}>Precio:</Text>
						<Text style={styles.info}>{selected.precio}</Text>
					</>
					:
					<>
						<SubTitleText text={"Escoge una habitación nueva"} />
					</>
				}
			</View>

			<RNPickerSelect
				onValueChange={(value) => setSelected(value)}
				items={habitaciones}
				placeholder={{ label: 'Elige la nueva habitacion', value: null }}
				value={selected}
			/>

			<View style={{ flex: 1, alignItems: 'flex-end', marginTop: 5, marginBottom: 40 }}>
				<CustomButton icon={""} text={"Solicitar"} func={() => sendSolicitud(navigation)} testID={"btn-solicitud"} />
			</View>

		</ScrollView>
	);
};

const styles = StyleSheet.create({
	container: {
		flex: 1,
		padding: 20,
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
	infoContainer: {
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

export default CambioHabitacionScreen;
