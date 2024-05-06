import 'react-native-gesture-handler';
import React, { useState } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import * as SecureStore from 'expo-secure-store';
import LoginScreen from './screens/LoginScreen';
import AuthContext from './utils/AuthProvider';
import DrawerNav from './components/DrawerNav';
import axios from 'axios';
import AdminScreen from './screens/AdminScreen';
import ClientesScreen from './screens/Admin/ClientesScreen';
import EmpleadosScreen from './screens/Admin/EmpleadosScreen';

const Stack = createNativeStackNavigator();

const App = () => {
  let userToken = null;

  const [userRole, setUserRole] = useState('');

  const [state, dispatch] = React.useReducer(
    (prevState, action) => {
      switch (action.type) {
        case 'RESTORE_TOKEN':
          return {
            ...prevState,
            userToken: action.token,
            isLoading: false,
          };
        case 'SIGN_IN':
          return {
            ...prevState,
            isSignout: false,
            userToken: action.token,
          };
        case 'SIGN_OUT':
          return {
            ...prevState,
            isSignout: true,
            userToken: null,
          };
        default:
          return prevState;
      }
    },
    {
      isLoading: true,
      isSignout: false,
      userToken: null,
    }
  );

  React.useEffect(() => {
    const bootstrapAsync = async () => {
      try {
        userToken = await SecureStore.getItemAsync('userToken');
        setUserRole(await SecureStore.getItemAsync('userRole'));
      } catch (e) {
        console.error("Can't restore token: ", e);
      }

      dispatch({ type: 'RESTORE_TOKEN', token: userToken });
    };

    bootstrapAsync();
  }, []);

  const authContext = React.useMemo(
    () => ({
      signIn: async (postData) => {
        try {
          const response = await axios.post('http://localhost:8443/login', postData, {timeout: 3000});
          setUserRole(response.data.role);
          userToken = response.data.token;

          await SecureStore.setItemAsync('userToken', userToken);
          await SecureStore.setItemAsync('userRole', response.data.role);
          dispatch({ type: 'SIGN_IN', token: userToken });
        } catch (error) {
          if (axios.isAxiosError(error) && error.code === 'ECONNABORTED') {
            alert('No estas conectado a la wifi del hotel');
          } else if (axios.isAxiosError(error) && !error.response) {
            alert('No estas conectado a la wifi del hotel');
          } else {
            alert(error.response.data);
            console.log('Error:', error.message);
          }
        }
      },
      signOut: async () => {
        await SecureStore.deleteItemAsync('userToken');
        await SecureStore.deleteItemAsync('userRole');
        userToken = null;
        setUserRole("");
        dispatch({ type: 'SIGN_OUT' });
      }
    }),
    []
  );

  return (
    <AuthContext.Provider value={authContext}>
      <NavigationContainer>
      <Stack.Navigator 
            screenOptions={({ route }) => ({
              headerShown: false,
            })}
          >
          {state.userToken == null ? (
          
            <Stack.Screen name="Login" component={LoginScreen} />
          ) : (
            <>
              { userRole == "admin" ?
                <>
                  <Stack.Screen name="Home" component={AdminScreen} />
                  <Stack.Screen name="Clientes" component={ClientesScreen} />
                  <Stack.Screen name="Empleados" component={EmpleadosScreen} />
                </>
                :
                userRole == "empleado" ?
                  <></>
                  :
                  <Stack.Screen name="Dnav" component={DrawerNav} />
              }

            </>
          )}
        </Stack.Navigator>  
      </NavigationContainer>
    </AuthContext.Provider>
  );
};

export default App;