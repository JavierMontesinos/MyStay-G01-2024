import React from 'react';
import { Text, StyleSheet, TouchableOpacity } from 'react-native';
import { MaterialCommunityIcons } from '@expo/vector-icons';

const CustomButton = ({ text, icon, func }) => {
    return (
        <TouchableOpacity style={styles.button} onPress={func} >
            <MaterialCommunityIcons name={ icon } size={20} color="white" style={styles.icon} />
            <Text style={styles.text}>{ text }</Text>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    button: {
        backgroundColor: '#1d2b42',
        flexDirection: 'row',
        alignItems: 'center',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        width: 260,
        paddingBottom: 20,
        paddingTop: 20,
    },
    icon: {
        marginRight: 10,
    },
    text: {
        color: 'white',
        fontSize: 16,
    },
})

export default CustomButton;